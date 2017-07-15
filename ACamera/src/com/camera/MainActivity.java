package com.camera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.necatievren.camera.R;

public class MainActivity extends Activity implements SurfaceHolder.Callback {

	private Camera mCamera;
	private Button button;
	private SurfaceView surfaceView;
	private PackageManager pm;
	private Boolean hasCamera = false;
	private Bitmap bitmap;
	private TextView txtView;
	private static Bitmap scaledBitmap;
	private static String size = "0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Bitmap image = BitmapFactory.decodeResource(getResources(),
				R.drawable.bn2);

		FrameLayout game = new FrameLayout(this);
		LinearLayout gameWidgets = new LinearLayout(this);

		Button endGameButton = new Button(this);
		TextView myText = new TextView(this);

		endGameButton.setWidth(300);
		endGameButton.setText("Start Game");
		myText.setText("rIZ..i");

		gameWidgets.addView(myText);
		gameWidgets.addView(endGameButton);

		game.addView(gameWidgets);

		setContentView(game);

		setContentView(R.layout.activity_main);
		button = (Button) findViewById(com.necatievren.camera.R.id.fCekBtn);
		surfaceView = (SurfaceView) findViewById(com.necatievren.camera.R.id.surfaceView);
		txtView = (TextView) findViewById(R.id.textView1);

		button.bringToFront();
		txtView.bringToFront();

		pm = this.getPackageManager();

		if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			hasCamera = true;
			SurfaceHolder holder = surfaceView.getHolder();
			holder.addCallback(this);
			holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
			holder.setFixedSize(600, 400);
		}

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (hasCamera) {
					takePicture();
				}
			}
		});

	}// end method

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {

		case R.id.action_settings:
			return true;

		case R.id.item1:
			Toast.makeText(this, "Selected 2048x1563", Toast.LENGTH_SHORT)
					.show();
			size = "2048";
			return true;

		case R.id.item2:
			Toast.makeText(this, "Selected 1600x1200", Toast.LENGTH_SHORT)
					.show();
			size = "1600";
			return true;

		case R.id.item3:
			Toast.makeText(this, "Selected  1280x960", Toast.LENGTH_SHORT)
					.show();
			size = "1280";
			return true;

		case R.id.item4:
			Toast.makeText(this, "Selected  640x480", Toast.LENGTH_SHORT)
					.show();
			size = "640";
			return true;
		}
		return false;
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		tryDrawing(arg0);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mCamera = Camera.open();
		mCamera.setDisplayOrientation(90);
		try {
			mCamera.setPreviewDisplay(holder);
			mCamera.startPreview();

		} catch (IOException e) {
			e.printStackTrace();
		}
		tryDrawing(holder);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		mCamera.stopPreview();
		mCamera.release();
	}

	private void takePicture() {
		AudioManager mgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		mgr.setStreamMute(AudioManager.STREAM_SYSTEM, false);
		mCamera.takePicture(_shutterCallBack, _rawCallBack, _jpgCallBack);
	}

	ShutterCallback _shutterCallBack = new ShutterCallback() {
		@Override
		public void onShutter() {

		}
	};

	PictureCallback _rawCallBack = new PictureCallback() {
		@Override
		public void onPictureTaken(byte[] arg0, Camera arg1) {

		}
	};

	PictureCallback _jpgCallBack = new PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {

			try {

				Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
						data.length);

				// Bitmap bitmap2 = BitmapFactory.decodeByteArray(data, offset,
				// length)

				Matrix matrix = new Matrix();
				matrix.postRotate(90);
				// Bitmap scaledBitmap =
				// Bitmap.createScaledBitmap(bitmap,bitmap.getWidth()
				// ,bitmap.getHeight() ,true);

				if (size == "2048") {
					scaledBitmap = Bitmap.createScaledBitmap(bitmap, 2048,
							1536, true);
				} else if (size == "1600") {
					scaledBitmap = Bitmap.createScaledBitmap(bitmap, 1600,
							1200, true);
				} else if (size == "1280") {
					scaledBitmap = Bitmap.createScaledBitmap(bitmap, 1280, 960,
							true);
				} else {
					scaledBitmap = Bitmap.createScaledBitmap(bitmap, 640, 480,
							true);
				}

				// scaledBitmap = Bitmap.createScaledBitmap(bitmap,1280 , 960
				// ,true);

				Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
						scaledBitmap.getWidth() / 3, scaledBitmap.getHeight(),
						matrix, true);

				File folder = new File(
						Environment.getExternalStorageDirectory() + "/ACamera");
				boolean success = true;
				if (!folder.exists()) {
					success = folder.mkdir();
				}
				if (success) {
				} else {
				}

				Toast.makeText(getBaseContext(), "Please wait",
						Toast.LENGTH_SHORT).show();

				FileOutputStream fileOutStream = null;
				// fileOutStream = new
				// FileOutputStream("/sdcard/ACamera/1_Bitmap.png");
				// rotatedBitmap.compress(Bitmap.CompressFormat.PNG, 100,
				// fileOutStream);

				// Bitmap grayBitmap = ToGray.start(rotatedBitmap);// ---->>>>
				// fileOutStream = new
				// FileOutputStream("/sdcard/ACamera/2_Gray.png");
				// grayBitmap.compress(Bitmap.CompressFormat.PNG , 100,
				// fileOutStream);

				// Bitmap bwBitmap = ToBW.start(rotatedBitmap);// ---->>>>
				// fileOutStream = new
				// FileOutputStream("/sdcard/ACamera/3_BW.png");
				// bwBitmap.compress(Bitmap.CompressFormat.PNG , 100,
				// fileOutStream);

				Bitmap bwBitmap_fix = ToBW_Fixed.start(rotatedBitmap);// ---->>>>
				fileOutStream = new FileOutputStream(
						"/sdcard/ACamera/3_BW_Fixed.png");
				bwBitmap_fix.compress(Bitmap.CompressFormat.PNG, 100,
						fileOutStream);

				// Bitmap bwBitmap_auto = ToBW_Auto.start(rotatedBitmap);//
				// ---->>>>
				// fileOutStream = new
				// FileOutputStream("/sdcard/ACamera/3_BW_Auto.png");
				// bwBitmap_auto.compress(Bitmap.CompressFormat.PNG , 100,
				// fileOutStream);

				// Bitmap thinningBitmap = ToThinning.start(rotatedBitmap);//
				// ---->>>>
				// fileOutStream = new
				// FileOutputStream("/sdcard/ACamera/4_Thinning.png");
				// thinningBitmap.compress(Bitmap.CompressFormat.PNG , 100,
				// fileOutStream);

				Bitmap thinningBitmap = ToThinning3.start(rotatedBitmap);// ---->>>>
				fileOutStream = new FileOutputStream(
						"/sdcard/ACamera/4_Thinning3.png");
				thinningBitmap.compress(Bitmap.CompressFormat.PNG, 100,
						fileOutStream);

				// Bitmap thinningBitmapFix =
				// ToThinning3Fix128.start(rotatedBitmap);// ---->>>>
				// fileOutStream = new
				// FileOutputStream("/sdcard/ACamera/5_Thinning3Fix128.png");
				// thinningBitmapFix.compress(Bitmap.CompressFormat.PNG , 100,
				// fileOutStream);

				Intent i = new Intent(MainActivity.this, ShowActivity.class);
				MainActivity.this.startActivity(i);

			} catch (Exception e) {
				Log.i("show", " " + e);
			}

			Log.i("show", "...");
		}
	};// end Take Picture

	private void tryDrawing(SurfaceHolder holder) {

		Canvas canvas = holder.lockCanvas();
		if (canvas == null) {
			Log.i("show", "Cannot draw onto the canvas as it's null");
		} else {
			drawMyStuff(canvas);
			holder.unlockCanvasAndPost(canvas);
		}
	}

	private void drawMyStuff(final Canvas canvas) {
		// Random random = new Random();
		Log.i("show", "Drawing . . . ");
		canvas.drawRGB(255, 128, 128);
	}

}
