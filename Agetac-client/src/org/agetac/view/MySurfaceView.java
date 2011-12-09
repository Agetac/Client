package org.agetac.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements Callback {
	
	private SurfaceHolder holder;
	private Context context;

	public MySurfaceView(Context c) {
		super(c);
		holder = getHolder();
		context = getContext();
	}
	
	public MySurfaceView(Context c, AttributeSet attrs) {
		super(c, attrs);
		holder = getHolder();
		context = getContext();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		String text = "ceci est un test";
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		canvas.drawText(text, 0, text.length()-1, 400, 250, paint);
	}
}
