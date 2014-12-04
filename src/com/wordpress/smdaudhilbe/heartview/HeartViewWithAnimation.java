package com.wordpress.smdaudhilbe.heartview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

public class HeartViewWithAnimation extends FrameLayout {

	//	for backward color
	public int howMuchPixelsToChangeColor = 0;
	private List<RectF> listOfRects;
	public double baseHeartEqualParts;
	public int baseOfBigHeart;
	
	//	for heart shape
	private Path path;
	private Paint paint,backViewPaint;	
	private int x1,y1,x2,y2,x3,y3,x4,y4;
	public RectF rect;
	
	//	backward color fill
	private BackEndView backView;	

	public HeartViewWithAnimation(Context context, AttributeSet attrs) {
		super(context, attrs);
						
		listOfRects = new ArrayList<RectF>();				
		rect = new RectF();
		
		path = new Path();
		paint = new Paint();		

		//	holo light theme
		paint.setColor(Color.parseColor("#fff3f3f3"));
		
		backViewPaint = new Paint();
		backViewPaint.setColor(Color.parseColor("#21BBA6"));
		
		//	back view
		backView = new BackEndView(context, attrs);
		this.addView(backView);
		
		//	front view
		FrontEndHeartView frontView = new FrontEndHeartView(context, attrs);
		this.addView(frontView);
	}
	
	//	rects like color to be filled at background
	public class BackEndView extends View{

		public BackEndView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			
			for (int i = 0; i < listOfRects.size(); i++) {
				RectF rectIs = listOfRects.get(i);				
				canvas.drawRect(rectIs, backViewPaint);
			}
		}
	}
	
	//	front end of heart view
	public class FrontEndHeartView extends View{
		
		public FrontEndHeartView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			
			drawingRectForWholeScreen();
			drawBigHeartView();
			drawSmallHeartView();			
			canvas.drawPath(path, paint);
		}
		
		//	3
		private void drawSmallHeartView() {
			
			rect = null;
			rect = new RectF();
			
			x1 = getWidth() / 2;
			y1 = getHeight() / 2 + 250  - 30;
					
			x2 = getWidth() / 2 - 250  + 30;
			y2 = getHeight() / 2;
				
			x3 = getWidth() / 2 + 250 - 30;
			y3 = getHeight() / 2;
					
			x4 = getWidth() / 2;
			y4 = getHeight() / 2 - 250 + 30;
				
			//	square
			path.moveTo(x1, y1);
			path.lineTo(x2, y2);
			path.lineTo(x4, y4);
			path.lineTo(x3, y3);
			path.close();
				
			//	mid point right side
			int midX = (x3 + x4) / 2;
			int midY = (y3 + y4) / 2;
					
			//	distance formula
			double distance = Math.sqrt(Math.pow(x3 - midX, 2) + Math.pow(y3 - midY, 2));
					
			//	rect around certain point
			rect.set(midX - (float)distance,midY - (float)distance,midX + (float)distance,midY + (float)distance);
			path.addArc(rect, 225, 180);
				
			//	mid point left side
			midX = (x2 + x4) / 2;
			midY = (y2 + y4) / 2;
				
			distance = Math.sqrt(Math.pow(x2 - midX, 2) + Math.pow(y2 - midY, 2));
				
			//	rect around certain point
			rect.set(midX - (float)distance,midY - (float)distance,midX + (float)distance,midY + (float)distance);
			path.addArc(rect, 135, 180);		
		}

		//	2
		private void drawBigHeartView() {
			
			x1 = getWidth() / 2;
			y1 = getHeight() / 2 + 250;
					
			x2 = getWidth() / 2 - 250;
			y2 = getHeight() / 2;
				
			x3 = getWidth() / 2 + 250;
			y3 = getHeight() / 2;
					
			x4 = getWidth() / 2;
			y4 = getHeight() / 2 - 250;
				
			//	square
			path.moveTo(x1, y1);
			path.lineTo(x2, y2);
			path.lineTo(x4, y4);
			path.lineTo(x3, y3);
			path.close();
				
			//	mid point right side
			int midX = (x3 + x4) / 2;
			int midY = (y3 + y4) / 2;
					
			//	distance formula
			double distanceNRadiusOfSemiCircle = Math.sqrt(Math.pow(x3 - midX, 2) + Math.pow(y3 - midY, 2));
					
			//	rect around certain point
			rect.set(midX - (float)distanceNRadiusOfSemiCircle,midY - (float)distanceNRadiusOfSemiCircle,midX + (float)distanceNRadiusOfSemiCircle,midY + (float)distanceNRadiusOfSemiCircle);
			path.addArc(rect, 225, 180);
				
			//	mid point left side
			midX = (x2 + x4) / 2;
			midY = (y2 + y4) / 2;
				
			distanceNRadiusOfSemiCircle = Math.sqrt(Math.pow(x2 - midX, 2) + Math.pow(y2 - midY, 2));
				
			//	rect around certain point
			rect.set(midX - (float)distanceNRadiusOfSemiCircle,midY - (float)distanceNRadiusOfSemiCircle,midX + (float)distanceNRadiusOfSemiCircle,midY + (float)distanceNRadiusOfSemiCircle);
			path.addArc(rect, 135, 180);
				
			//	to make hole
			path.setFillType(Path.FillType.EVEN_ODD);
			
			//	calculating peak height of background heart
			double heartPeakPointY = midY - distanceNRadiusOfSemiCircle;			
			Log.d("heartPeakPointY : ",heartPeakPointY+"");		
			
			double totalHeightOfHeart = (y1 - heartPeakPointY) + 49;		
			Log.d("totalHeightOfHeart : ",totalHeightOfHeart+"");
			
			baseHeartEqualParts = totalHeightOfHeart / 100;
			Log.d("equalParts : ",baseHeartEqualParts+"");
			
			baseOfBigHeart = y1;		
		}

		//	1
		private void drawingRectForWholeScreen() {
			path.moveTo(0, 0);
			path.lineTo(getWidth(), 0);
			path.lineTo(getWidth(), getHeight());
			path.lineTo(0, getHeight());		
			path.close();
		}
	}
	
	public void howMuchPixelsToChangeColor(int pixelsCount) {
		this.howMuchPixelsToChangeColor = pixelsCount;
		
		listOfRects.clear();
		
		for (int i = 1; i <= howMuchPixelsToChangeColor ; i++) {
			listOfRects.add(new RectF(0,baseOfBigHeart - (float)(i * baseHeartEqualParts),getWidth(),baseOfBigHeart));
		}		
		Log.d("listOfRects.size is : ",listOfRects.size()+"");
		
		//	invalidating backView
		backView.invalidate();
	}
}