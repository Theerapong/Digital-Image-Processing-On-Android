package com.camera;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;

import com.necatievren.camera.R;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;


public class ShowActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show);
		
	
//		File imgFile3 = new File(Environment.getExternalStorageDirectory(),"/ACamera/4_Thinning.png");
//	    if(imgFile3.exists()){
//	        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile3.getAbsolutePath());
//	        ImageView myImage = (ImageView) findViewById(R.id.imageView3);
//	        myImage.setImageBitmap(myBitmap);
//	    }
	    
		File imgFile3 = new File(Environment.getExternalStorageDirectory(),"/ACamera/4_Thinning3.png");
	    if(imgFile3.exists()){
	        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile3.getAbsolutePath());
	        ImageView myImage = (ImageView) findViewById(R.id.imageView3);
	        myImage.setImageBitmap(myBitmap);

	    }
	    

//		File imgFile3 = new File(Environment.getExternalStorageDirectory(),"/ACamera/1_Bitmap.png");
//	    if(imgFile3.exists()){
//	        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile3.getAbsolutePath());
//	        ImageView myImage = (ImageView) findViewById(R.id.imageView3);
//	        myImage.setImageBitmap(myBitmap);
//
//	    }
	    
	    
	    File imgFile1 = new File(Environment.getExternalStorageDirectory(),"/ACamera/3_BW_Fixed.png");
	    if(imgFile1.exists()){
	        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile1.getAbsolutePath());
	        ImageView myImage = (ImageView) findViewById(R.id.imageView2);
	        myImage.setImageBitmap(myBitmap);

	    }
	    
		
		
//	    File imgFile2 = new File(Environment.getExternalStorageDirectory(),"/ACamera/3_BW_Auto.png");
//	    if(imgFile2.exists()){
//	        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile2.getAbsolutePath());
//	        ImageView myImage = (ImageView) findViewById(R.id.imageView2);
//	        myImage.setImageBitmap(myBitmap);
//
//	    }
	    
	    
//	    File imgFile2 = new File(Environment.getExternalStorageDirectory(),"/ACamera/5_Thinning3Fix128.png");
//	    if(imgFile2.exists()){
//	        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile2.getAbsolutePath());
//	        ImageView myImage = (ImageView) findViewById(R.id.imageView2);
//	        myImage.setImageBitmap(myBitmap);
//
//	    }



	    
	}//end method

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return true;
	}

}
