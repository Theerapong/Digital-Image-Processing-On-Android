package com.camera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;

public class ToThinning {

    private static short[][] arrayGray;
    private static byte[][] arrayBinary;
    private static int width;
    private static int height;
    
	public static Bitmap start(Bitmap bitmap) {
		String photoPath = Environment.getExternalStorageDirectory()+"/ATest/Sunset.jpg";
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		bitmap = BitmapFactory.decodeFile(photoPath,options);
//		bitmap = BitmapFactory.decodeFile(photoPath);
		
        if(bitmap==null)Log.i("show","bitmap = null !!!");
		
		width = bitmap.getWidth();
        height = bitmap.getHeight();
        Log.i("show","ขนาด -->> "+width +" -> "+ height);

        arrayGray = new short[width][height];
        arrayBinary = new byte[width][height];
       
        
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int pixel = bitmap.getPixel(x, y);
				
				short red = (short) Color.red(pixel);
				short green = (short) Color.green(pixel);
				short blue = (short) Color.blue(pixel);

				arrayGray[x][y]= red = green = blue = (short) (red * 0.299 + green * 0.587 + blue * 0.114);
				
				if(arrayGray[x][y]<150){
					bitmap.setPixel(x, y, Color.rgb( 0, 0, 0));
					arrayBinary[x][y]=1;
					
					if((x%2==0)&&(y%2==0)){
//						bitmap.setPixel(x, y, Color.rgb( 255, 255, 255));
//						arrayBinary[x][y]=0;
					}
				}else{

				}
				

			}//end for
		}//end for
		
		
//		textFile();
		
		
		Bitmap thin = thinningAlgorithm(bitmap);

		
		return thin;
	}
	

	


	private static Bitmap thinningAlgorithm(Bitmap bitmap) {
		

		for (int y = 1; y < height-1; y++) {
			for (int x = 1; x < width-1; x++) {
				
				//if neighborhood is according to 14 rules
				if( rules1to8(arrayBinary[x-1][y-1], arrayBinary[x][y-1] ,arrayBinary[x+1][y-1], 
					      arrayBinary[x-1][y]  , arrayBinary[x][y]	,arrayBinary[x+1][y]  ,
				          arrayBinary[x-1][y+1], arrayBinary[x][y+1] ,arrayBinary[x+1][y+1]	 )
				   ){
					
					//then mark pixel is 'E' (edge)

//					bitmap.setPixel(x, y, Color.rgb( 0, 0, 0) );	
//					bitmap.setPixel(x, y, Color.rgb( 100, 100, 100) );

					
					
					bitmap.setPixel(x, y, Color.rgb( 255, 255, 255) );
					arrayBinary[x][y] = 0;
				}
				else{
//					bitmap.setPixel(x, y, Color.rgb( 255, 255, 255) );
//					bitmap.setPixel(x, y, Color.rgb( 0, 0, 0) );
//					rules9to14(arrayBinary[x-1][y-1], arrayBinary[x][y-1] ,arrayBinary[x+1][y-1], 
//					      arrayBinary[x-1][y]  , arrayBinary[x][y]	,arrayBinary[x+1][y]  ,
//				          arrayBinary[x-1][y+1], arrayBinary[x][y+1] ,arrayBinary[x+1][y+1],	
//				          x,y );
				}
				
				
//					arrayBinary[x][y] = 8;
				
				
				
				
			}//end for
		}//end for 
		
		
		return bitmap;
	}//end method

	



	private static boolean rules1to8(byte a, byte b, byte c, byte d, byte e, byte f, byte g, byte h, byte i) {
		
		if ( (a==0) && (b==0) && (d==0) && (e==1) && (f==1) && (h==1) ) {// rule 1
			return true;
		}
		else if ( (b==0) && (c==0) && (d==1) && (e==1) && (f==0) && (h==1) ) {// rule 2
			return true;
		}
		else if ((b==1) && (d==1) && (e==1) && (f==0) && (h==0) && (i==0)) {// rule 3
			return true;
		}
		else if ((b==1) && (d==0) && (e==1) && (f==1) && (g==0) && (h==0)) {// rule 4
			return true;
		}
		else if ((a==0) && (b==0) && (c==0) && (e==1) && (g==1) && (h==1)) {// rule 5
			return true;
		}
		else if ((a==1) && (c==0) && (d==1) && (e==1) && (f==0) && (i==0)) {// rule 6
			return true;
		}
		else if ((b==1) && (c==1) && (e==1) && (g==0) && (h==0) && (i==0)) {// rule 7
			return true;
		}
		else if ((a==0) && (d==0) && (e==1) && (f==1) && (g==0) && (i==1)) {// rule 8
			return true;
		}
		
		
		
		
		
		
		
		
		else if ( (a==1)&&(b==1)&&(e==1)&&(f==0)&&(g==0)&&(h==0)&&(i==0) ){// rule 9
			return true;
		}
		else if ( (a==1)&&(b==1)&&(e==1)&&(g==0)&&(h==0)&&(i==0) ){// rule 10
			return true;
		}
		else if ( (c==0)&&(d==1)&&(e==1)&&(f==0)&&(g==1)&&(i==0) ){// rule 11
			return true;
		}
		else if ( (a==1)&&(b==1)&&(c==0)&&(e==1)&&(f==0)&&(g==0)&&(h==0)&&(i==0) ){// rule 12
			return true;
		}
		else if ( (a==0)&&(c==1)&&(d==0)&&(e==1)&&(g==0)&&(h==0)&&(i==0) ){// rule 13
			return true;
		}
		else if ( (a==0)&&(c==1)&&(d==0)&&(e==1)&&(f==1)&&(g==0)&&(h==0)&&(i==0) ){// rule 14
			return true;
		}
		
							
						
					
		
		return false;
	}//end method
	
	
	
	
	private static void textFile() {
		File dir = new File(android.os.Environment.getExternalStorageDirectory(),"A_");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String filename = "BBB.txt";
		
		try {
			File f = new File(dir + File.separator + filename);
			FileOutputStream fOut = new FileOutputStream(f);
			OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
			
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					myOutWriter.append( Short.toString(arrayBinary[x][y]) +"," );
				}
				myOutWriter.append( "\n" );
			}
			
			myOutWriter.close();
			fOut.close();
		} catch (FileNotFoundException e) { 
			e.printStackTrace();
		} catch (IOException e) { 
			e.printStackTrace();
		}
		
		
	}
}//end class