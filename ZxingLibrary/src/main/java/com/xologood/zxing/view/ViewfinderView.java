/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xologood.zxing.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.google.zxing.ResultPoint;
import com.xologood.zxing.R;
import com.xologood.zxing.camera.CameraManager;

import java.util.ArrayList;
import java.util.List;

/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder
 * rectangle and partial transparency outside it, as well as the laser scanner
 * animation and result points.
 * 
 * 
 * @author gaotaiwen@gmail.com ()
 */
public final class ViewfinderView extends View {

	private static final int[] SCANNER_ALPHA = { 0, 64, 128, 192, 255, 192, 128, 64 };
	
	/** 
     * 刷新界面的时间 
     */
	private static final long ANIMATION_DELAY = 30L;
	
	private static final int CURRENT_POINT_OPACITY = 0xFF;
	private static final int MAX_RESULT_POINTS = 20;
	private static final int POINT_SIZE = 6;

	private CameraManager cameraManager;
	
	/** 
     * 扫描框外背景画笔对象
     */  
	private final Paint Backpaint;
	
	/** 
     * 扫描框内四个边角画笔对象
     */ 
	private final Paint Cornerpaint;
	
	/** 
     * 扫描框外下文字画笔对象
     */ 
	private final Paint Textpaint;
	
	/** 
     * 手机的屏幕密度 
     */  
    private static float Density;  
    
    /** 
     * 字体大小 
     */  
    private static final int TEXT_SIZE = 14;  
    
    /** 
     * 字体距离扫描框下面的距离 
     */  
    private static final int TEXT_PADDING_TOP = 30;  
    
    /** 
     * 四个绿色边角对应的长度 
     */  
    private int ScreenRate_wight;  
    
    /** 
     * 四个绿色边角对应的宽度 
     */  
    private static final int ScreenRate_height = 10;
	
	private Bitmap resultBitmap;
	
	/** 
     *
     */  
	private final int maskColor;
	
	/** 
     * 扫描框外背景颜色
     */  
	private final int resultColor;
	
	/** 
     * 扫描框内运动条颜色
     */  
	private final int laserColor;
	
	/** 
     * 扫描框内分散点颜色
     */
	private final int resultPointColor;
	private int scannerAlpha;
	private List<ResultPoint> possibleResultPoints;
	private List<ResultPoint> lastPossibleResultPoints;
	int loopTop, loopBottom;
	boolean isFirst;
	boolean isRun = true;

	public boolean isRun() {
		return isRun;
	}

	public void setRun(boolean isRun) {
		this.isRun = isRun;
	}

	// This constructor is used when the class is built from an XML resource.
	public ViewfinderView(Context context, AttributeSet attrs) {
		super(context, attrs);

		// Initialize these once for performance rather than calling them every
		// time in onDraw().
		Density = context.getResources().getDisplayMetrics().density;  //1.5
		ScreenRate_wight = (int)(12 * Density);
		
		Backpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		Cornerpaint = new Paint(Paint.DITHER_FLAG);
		Textpaint = new Paint();
		
		Resources resources = getResources();
		maskColor = resources.getColor(R.color.viewfinder_mask);
		resultColor = resources.getColor(R.color.result_view);
		laserColor = resources.getColor(R.color.viewfinder_laser);
		resultPointColor = resources.getColor(R.color.possible_result_points);
		
		scannerAlpha = 0;
		possibleResultPoints = new ArrayList<ResultPoint>(5);
		lastPossibleResultPoints = null;
	}

	public void setCameraManager(CameraManager cameraManager) {
		this.cameraManager = cameraManager;
	}

	@Override
	public void onDraw(Canvas canvas) {
		//中间的扫描框，你要修改扫描框的大小，去CameraManager里面修改
		if (cameraManager == null) {
			return; 
		}
		Rect frame = cameraManager.getFramingRect();
		if (frame == null) {
			return;
		}
		
		//初始化中间线滑动的最上边和最下边  
		if (!isFirst) {
			loopTop = frame.top;
			isFirst = true;
			loopBottom = frame.bottom;
		}
		
		//获取屏幕的宽和高
		int width = canvas.getWidth();//540
		int height = canvas.getHeight();//960

		Backpaint.setColor(resultBitmap != null ? resultColor : maskColor);
		//画出扫描框外面的阴影部分，共四个部分，扫描框的上面到屏幕上面，扫描框的下面到屏幕下面  
        //扫描框的左边面到屏幕左边，扫描框的右边到屏幕右边
		canvas.drawRect(0, 0, width, frame.top, Backpaint);
		canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, Backpaint);
		canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, Backpaint);
		canvas.drawRect(0, frame.bottom + 1, width, height, Backpaint);
		
		//这里画取景框四个角落的绿色夹角
		Cornerpaint.setColor(Color.GREEN);
		Cornerpaint.setAntiAlias(true); 	
		Cornerpaint.setStrokeWidth(5);
		canvas.drawLine(frame.left - 2.5f, frame.top, frame.left + ScreenRate_wight, frame.top, Cornerpaint);
		canvas.drawLine(frame.left, frame.top, frame.left, frame.top + ScreenRate_wight, Cornerpaint);
		
		canvas.drawLine(frame.right - ScreenRate_wight, frame.top, frame.right + 2.5f, frame.top, Cornerpaint);
		canvas.drawLine(frame.right, frame.top, frame.right, frame.top + ScreenRate_wight, Cornerpaint);
		
		canvas.drawLine(frame.left - 2.5f, frame.bottom, frame.left + ScreenRate_wight, frame.bottom, Cornerpaint);
		canvas.drawLine(frame.left, frame.bottom - ScreenRate_wight, frame.left, frame.bottom, Cornerpaint);
		
		canvas.drawLine(frame.right - ScreenRate_wight, frame.bottom, frame.right + 2.5f, frame.bottom, Cornerpaint);
		canvas.drawLine(frame.right, frame.bottom - ScreenRate_wight, frame.right, frame.bottom, Cornerpaint);
		
		if (resultBitmap != null) {
			// Draw the opaque result bitmap over the scanning rectangle
			Backpaint.setAlpha(CURRENT_POINT_OPACITY);
			canvas.drawBitmap(resultBitmap, null, frame, Backpaint);
		} else {
			
			//画扫描框下面的字  
			Textpaint.setColor(Color.WHITE);  
			Textpaint.setTextSize(TEXT_SIZE * Density);  
			Textpaint.setAntiAlias(true);
			Textpaint.setFilterBitmap(true);
			//Textpaint.setAlpha(0x40);  
			Textpaint.setTypeface(Typeface.create("System", Typeface.BOLD));
			
			//获取文字长度，（总长-文字长度）/2 = 开始画的位置 为居中
			float tXone = (width - getFontlength(Textpaint, getResources().getString(R.string.msg_tishi_nr)))/2;
			float tXtwo = (width - getFontlength(Textpaint, getResources().getString(R.string.msg_tishi_sgd)))/2;
			
			canvas.drawText(getResources().getString(R.string.msg_tishi_nr), tXone,
		        		(float) (frame.bottom + (float)TEXT_PADDING_TOP * Density), Textpaint);
			canvas.drawText(getResources().getString(R.string.msg_tishi_sgd), tXtwo,
	        		(float) (frame.bottom + (float)60 * Density), Textpaint);
			
//			// Draw a red "laser scanner" line through the middle to show
//			// decoding is active
//			Backpaint.setColor(laserColor);
//			Backpaint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
//			scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
//			//int middle = frame.height() / 2 + frame.top;
//			
//			// 绘制中间的红线
//			loopTop++;
//			if (loopTop >= frame.bottom) {
//				loopTop = frame.top;
//			}
//			
//			canvas.drawRect(frame.left + ScreenRate_wight, loopTop - 1, 
//					frame.right - ScreenRate_wight, loopTop + 2, Backpaint);
//
//			Rect previewFrame = cameraManager.getFramingRectInPreview();
//			float scaleX = frame.width() / (float) previewFrame.width();
//			float scaleY = frame.height() / (float) previewFrame.height();
//
//			List<ResultPoint> currentPossible = possibleResultPoints;
//			List<ResultPoint> currentLast = lastPossibleResultPoints;
//			int frameLeft = frame.left;
//			int frameTop = frame.top;
//			if (currentPossible.isEmpty()) {
//				lastPossibleResultPoints = null;
//			} else {
//				possibleResultPoints = new ArrayList<ResultPoint>(5);
//				lastPossibleResultPoints = currentPossible;
//				Backpaint.setAlpha(CURRENT_POINT_OPACITY);
//				Backpaint.setColor(resultPointColor);
//				synchronized (currentPossible) {
//					for (ResultPoint point : currentPossible) {
//						canvas.drawCircle(frameLeft
//								+ (int) (point.getX() * scaleX), frameTop
//								+ (int) (point.getY() * scaleY), POINT_SIZE,
//								Backpaint);
//					}
//				}
//			}
//			
//			// 绘制一闪一闪的黄点
//			if (currentLast != null) {
//				Backpaint.setAlpha(CURRENT_POINT_OPACITY / 2);
//				Backpaint.setColor(resultPointColor);
//				synchronized (currentLast) {
//					float radius = POINT_SIZE / 2.0f;
//					for (ResultPoint point : currentLast) {
//						canvas.drawCircle(frameLeft
//								+ (int) (point.getX() * scaleX), frameTop
//								+ (int) (point.getY() * scaleY), radius, Backpaint);
//					}
//				}
//			}
//
//			// Request another update at the animation interval, but only
//			// repaint the laser line,not the entire viewfinder mask.
//			// 仅仅刷新中间的红线，让其一闪一闪的动画,不是整个矩形刷新
//
//			loopTop++;
//			// if(isRun){
//			postInvalidateDelayed(ANIMATION_DELAY, frame.left - POINT_SIZE,
//					frame.top - POINT_SIZE, frame.right + POINT_SIZE,
//					frame.bottom + POINT_SIZE);
//			// }
		}

	}

	public void refreshUI() {

	}

	public void drawViewfinder() {
		Bitmap resultBitmap = this.resultBitmap;
		this.resultBitmap = null;
		if (resultBitmap != null) {
			resultBitmap.recycle();
		}
		invalidate();
	}

	public void addPossibleResultPoint(ResultPoint point) {
		List<ResultPoint> points = possibleResultPoints;
		synchronized (points) {
			points.add(point);
			int size = points.size();
			if (size > MAX_RESULT_POINTS) {
				// trim it
				points.subList(0, size - MAX_RESULT_POINTS / 2).clear();
			}
		}
	}
	
	/**  
     * @return 返回指定笔和指定字符串的长度  
     */  
    public static float getFontlength(Paint paint, String str) {  
        return paint.measureText(str);  
    } 

}