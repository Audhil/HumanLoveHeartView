package com.wordpress.smdaudhilbe.heartview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class SimpleHeartViewWithAnimation extends View {
	
	private Path path,outerPathLeft,outerPathRight,outerSemiCirclePathLeft,outerSemiCirclePathRight;
	private Paint paint,outerPaint;	
	private int x1,y1,x2,y2,x3,y3,x4,y4;
	public RectF rectLeft,rectRight;
	private List<PointF> totalOuterLeftSquarPoints,totalOuterRightSquarPoints;
	
//	method 1
//	private int inte = 0;	
	private int percentageToDraw = 0;
	
	private boolean drawn = false;

	public SimpleHeartViewWithAnimation(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		rectLeft = new RectF();
		rectRight = new RectF();
		
		path = new Path();
		paint = new Paint();
		
		paint.setColor(Color.LTGRAY);
		paint.setStrokeWidth(15);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStyle(Paint.Style.STROKE);
		
		outerPathLeft = new Path();
		outerPathRight = new Path();
		
		outerPaint = new Paint();
		outerPaint.setColor(Color.parseColor("#21BBA6"));
		outerPaint.setStrokeWidth(20);
		outerPaint.setStrokeCap(Paint.Cap.ROUND);
		outerPaint.setStyle(Paint.Style.STROKE);
		
		totalOuterLeftSquarPoints = new ArrayList<PointF>();
		totalOuterRightSquarPoints = new ArrayList<PointF>();
				
		outerSemiCirclePathLeft = new Path();
		outerSemiCirclePathRight = new Path();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		//	base heart view
		if(!drawn){
			drawn = true;
			drawHeartView();
		}
		
		canvas.drawPath(path, paint);
		
		drawOuterStroke();
		
		//	bottom square - method 1
//		drawOuterBorderHeartView();
		
		canvas.drawPath(outerPathLeft, outerPaint);
		canvas.drawPath(outerPathRight, outerPaint);
	}
	
	//	drawing outer stroke
	private void drawOuterStroke() {
		
		//	only drawing bottom square
		if(percentageToDraw <= 50){
			
			//	clearing old lists
			totalOuterLeftSquarPoints.clear();
			totalOuterRightSquarPoints.clear();
			
			for (int i = 1; i <= percentageToDraw; i++) {
				totalOuterLeftSquarPoints.add(new PointF(x1 + ((i * (x2 - x1)) / 50), y1 + ((i * (y2 - y1)) / 50)));
				totalOuterRightSquarPoints.add(new PointF(x1 + ((i * (x3 - x1)) / 50), y1 + ((i * (y3 - y1)) / 50)));
			}
			
			//	for fast drawing
			outerPathLeft.rewind();
			outerPathRight.rewind();
			
			for (int i = 0; i < totalOuterLeftSquarPoints.size() && i < totalOuterRightSquarPoints.size(); i++) {
				
				//	originating point
				outerPathLeft.moveTo(totalOuterLeftSquarPoints.get(0).x, totalOuterLeftSquarPoints.get(0).y);
				outerPathRight.moveTo(totalOuterRightSquarPoints.get(0).x, totalOuterRightSquarPoints.get(0).y);
				
				//	line to other points
				outerPathLeft.lineTo(totalOuterLeftSquarPoints.get(i).x, totalOuterLeftSquarPoints.get(i).y);
				outerPathRight.lineTo(totalOuterRightSquarPoints.get(i).x, totalOuterRightSquarPoints.get(i).y);
			}			
		}
		
		//	bottom square with semi circles
		else{
			
			//	makes app fast
			outerSemiCirclePathLeft.rewind();
			outerSemiCirclePathRight.rewind();
			
			outerSemiCirclePathLeft.addArc(rectLeft, 135,(percentageToDraw - 50) * 3.6f);
			
			//	to be drawn at CCW
			outerSemiCirclePathRight.addArc(rectRight, 45,(50 - percentageToDraw) * 3.6f);
			
			outerPathRight.addPath(outerSemiCirclePathRight);
			outerPathLeft.addPath(outerSemiCirclePathLeft);
		}
	}
	
	//	drawing front heart - border - method 1
//	private void drawOuterBorderHeartView() {
//		
//		//	outer points
//		if(percentageToDraw <= 50){
//			
//			for (int i = 1; i <= 50; i++) {
//				totalOuterLeftSquarPoints.add(new PointF(x1 + ((i * (x2 - x1)) / 50), y1 + ((i * (y2 - y1)) / 50)));
//				totalOuterRightSquarPoints.add(new PointF(x1 + ((i * (x3 - x1)) / 50), y1 + ((i * (y3 - y1)) / 50)));
//			}		
//			
//			//	origin at first point
//			outerPathLeft.moveTo(totalOuterLeftSquarPoints.get(0).x, totalOuterLeftSquarPoints.get(0).y);
//			outerPathRight.moveTo(totalOuterRightSquarPoints.get(0).x, totalOuterRightSquarPoints.get(0).y);
//			
//			if(inte < totalOuterLeftSquarPoints.size() && inte < totalOuterRightSquarPoints.size()){
//				outerPathLeft.lineTo(totalOuterLeftSquarPoints.get(inte).x, totalOuterLeftSquarPoints.get(inte).y);
//				outerPathRight.lineTo(totalOuterRightSquarPoints.get(inte).x, totalOuterRightSquarPoints.get(inte).y);
//				
//				inte++;
//				
//				try {
//					Thread.sleep(20);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				
//				invalidate();
//			}
//		}		
//	}	

	//	drawing heart - back
	private void drawHeartView() {
		
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
//		path.lineTo(x4, y4);
//		path.lineTo(x3, y3);
		
		path.moveTo(x1, y1);
		path.lineTo(x3, y3);
		path.close();
			
		//	mid point right side
		int midX = (x3 + x4) / 2;
		int midY = (y3 + y4) / 2;
				
		//	distance formula
		double distanceNRadiusOfSemiCircle = Math.sqrt(Math.pow(x3 - midX, 2) + Math.pow(y3 - midY, 2));
				
		//	rect around certain point
		rectRight.set(midX - (float)distanceNRadiusOfSemiCircle,midY - (float)distanceNRadiusOfSemiCircle,midX + (float)distanceNRadiusOfSemiCircle,midY + (float)distanceNRadiusOfSemiCircle);
		path.addArc(rectRight, 225, 180);
			
		//	mid point left side
		midX = (x2 + x4) / 2;
		midY = (y2 + y4) / 2;
			
		distanceNRadiusOfSemiCircle = Math.sqrt(Math.pow(x2 - midX, 2) + Math.pow(y2 - midY, 2));
			
		//	rect around certain point
		rectLeft.set(midX - (float)distanceNRadiusOfSemiCircle,midY - (float)distanceNRadiusOfSemiCircle,midX + (float)distanceNRadiusOfSemiCircle,midY + (float)distanceNRadiusOfSemiCircle);
		path.addArc(rectLeft, 135, 180);
	}

	//	setting total percentage - method 1
	public void setOuterHeartPercentToDraw(int percentageToDraw) {
		this.percentageToDraw = percentageToDraw;
	}
}