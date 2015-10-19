/**
 * Copyright (C) 2015 - Mazarin Pvt Ltd
 * @author Tharaka Nirmana
 */
package com.mazarin_movieApp;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

/**
 * Shows a loading screen for 4 seconds Checks which state the app is in and
 * directs to the right screen
 */
public class SplashScreen extends BaseActivity {

	private final int SPLASH_DISPLAY_LENGHT = 4000;
	
	private ImageView imagView;
	private Timer timer;
	private int index;
	private MyHandler handler;
	Bitmap bmp;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.splashscreen);
		getActionBar().hide();
		/*
		 * New Handler to start the MainActivity.. and close this SplashScreen
		 * after 4 seconds.
		 */
		
		handler = new MyHandler();
		imagView = (ImageView) findViewById(R.id.imgAnimation);

		index = 0;
		timer = new Timer();
		timer.schedule(new TickClass(), 500, 175);
		
		
		new Handler().postDelayed(new Runnable() {
			public void run() {
				
				Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
				SplashScreen.this.startActivity(mainIntent);
				SplashScreen.this.finish();

			}
		}, SPLASH_DISPLAY_LENGHT);
	}
	
	private class TickClass extends TimerTask {
		@Override
		public void run() {
			handler.sendEmptyMessage(index);
			index++;
		}
	}

	@SuppressLint("HandlerLeak")
	private class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			int[] images = new int[20];
			images[0] = R.drawable.animation_dots1;
			images[1] = R.drawable.animation_dots2;
			images[2] = R.drawable.animation_dots3;
			images[3] = R.drawable.animation_dots4;
			images[4] = R.drawable.animation_dots5;
			images[5] = R.drawable.animation_dots1;
			images[6] = R.drawable.animation_dots2;
			images[7] = R.drawable.animation_dots3;
			images[8] = R.drawable.animation_dots4;
			images[9] = R.drawable.animation_dots5;
			images[10] = R.drawable.animation_dots1;
			images[11] = R.drawable.animation_dots2;
			images[12] = R.drawable.animation_dots3;
			images[13] = R.drawable.animation_dots4;
			images[14] = R.drawable.animation_dots5;
			images[15] = R.drawable.animation_dots1;
			images[16] = R.drawable.animation_dots2;
			images[17] = R.drawable.animation_dots3;
			images[18] = R.drawable.animation_dots4;
			images[19] = R.drawable.animation_dots5;

			try {
				bmp = BitmapFactory.decodeResource(getResources(),
						images[index]);
			} catch (ArrayIndexOutOfBoundsException e) {
			}
			if (index == 5) {
				index = 0;
			}
			imagView.setImageBitmap(bmp);
		}
	}
	

}
