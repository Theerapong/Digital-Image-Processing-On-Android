package com.camera;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

public class ToBW_Fixed {

	private static short[][] arrayR ;
    private static short[][] arrayG ;
    private static short[][] arrayB ;
    private static short[][] arrayGray;
    private static int threshold=160;
    
	public static Bitmap start(Bitmap bitmap) {

		Log.i("show","ToBW_Fixed -> start" );
				
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();


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
				
				
				int inc=10;
				if((red+=inc) <255)  {
					red+=inc;
				}
				if((green+=inc)<255){
					green+=inc;
				}
				if((blue+=inc)<255){
					blue+=inc;
				}
				
				
				arrayGray[x][y]= red = green = blue = (short) (red * 0.299 + green * 0.587 + blue * 0.114);
				
				if(arrayGray[x][y]<threshold){
					bitmap.setPixel(x, y, Color.rgb( 0, 0, 0));
				}else{
					bitmap.setPixel(x, y, Color.rgb( 255, 255, 255) );
				}
				
				
				
			}
		}
//		Log.i("show", "bw " + Integer.toString( arrayGray[0][0] ) );
//		Log.i("show", "bw " + Integer.toString( arrayGray[0][1] ) );
//		Log.i("show", "bw " + Integer.toString( Color.rgb( 255, 255, 255) ) );
        

        
		return bitmap;
		
	}

}
