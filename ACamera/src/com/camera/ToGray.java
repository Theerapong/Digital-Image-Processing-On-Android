package com.camera;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.util.Log;
import java.util.Arrays;

public class ToGray {

	private static short[][] arrayR ;
    private static short[][] arrayG ;
    private static short[][] arrayB ;
    private static short[][] arrayGray;
    

	public static Bitmap start(Bitmap bitmap) {

		Log.i("show","ToGray -> start" );
		
		
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
//        Log.i("show","ขนาด -->> "+width +" -> "+ height);

        arrayR = new short[width][height];
        arrayG = new short[width][height];
        arrayB = new short[width][height];
        arrayGray = new short[width][height];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int pixel = bitmap.getPixel(x, y);
				
				short red = (short) Color.red(pixel);
				short green = (short) Color.green(pixel);
				short blue = (short) Color.blue(pixel);
				
				arrayR[x][y] = red;
				arrayG[x][y] = green;
				arrayB[x][y] = blue;
				
				
				arrayGray[x][y]= red = green = blue = (short) (red * 0.299 + green * 0.587 + blue * 0.114);
				
//				int inc=20;
//				if((red+=inc) <255)  {
//					red+=inc;
//				}
//				if((green+=inc)<255){
//					green+=inc;
//				}
//				if((blue+=inc)<255){
//					blue+=inc;
//				}
				
				bitmap.setPixel(x, y, Color.rgb( red, green, blue));
				
			}
		}

        
		return bitmap;
	}
}
