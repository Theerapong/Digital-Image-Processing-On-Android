package com.camera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;

public class ToThinning3Fix128 {

    private static short[][] arrayGray;
    private static byte[][] arrayBinary;
    private static int width;
    private static int height;
    private static int threshold=128;
    
    static double []meanGray1;
    static double []meanGray2;
    static final byte E=100;
    
    private static Bitmap newBitmap;
    
	public static Bitmap start(Bitmap bitmap) {
		

		String photoPath = Environment.getExternalStorageDirectory()+"/ATest/S.png";
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		bitmap = BitmapFactory.decodeFile(photoPath, options);
		
		
		Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
		newBitmap = Bitmap.createBitmap(bitmap.getWidth() , bitmap.getHeight(), conf); // this creates a MUTABLE bitmap
		Canvas canvas = new Canvas(newBitmap);
		
		
		Log.i("show","ToThinning3Fix128 -> start" );
		
		width = bitmap.getWidth();
        height = bitmap.getHeight();

        arrayGray = new short[width][height];
        arrayBinary = new byte[width][height];
        
        meanGray1=new double[width];
        meanGray2=new double[height];
       
        for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int pixel = bitmap.getPixel(x, y);
				short red = (short) Color.red(pixel);
				short green = (short) Color.green(pixel);
				short blue = (short) Color.blue(pixel);
				
				arrayGray[x][y]= red = green = blue = (short) (red * 0.299 + green * 0.587 + blue * 0.114);
				
			}
        }
        
        //start threshold *************************
        
        //ไม่ใช้ Auto threshold
        
        Log.i("show","ค่า threshold ที่ fixed คือ " + threshold);
        
        
        //end threshold *************************
        
        
        
             
        

        //to B&W
        for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				
				if(arrayGray[x][y]<threshold){
					newBitmap.setPixel(x, y, Color.rgb( 0, 0, 0));
					arrayBinary[x][y]=1;
					
					//if((x%2==0)&&(y%2==0)){
						//bitmap.setPixel(x, y, Color.rgb( 255, 255, 255));
						//arrayBinary[x][y]=0;
					//}
				}
				else{
				}
				

			}//end for x
		}//end for y
        
		
		
//		textFile();
		

//		Bitmap thin = thinningAlgorithm(bitmap);

		for(int i=0 ;i<10; i++){
			newBitmap = thinningAlgorithm(newBitmap);
		}
		
		return newBitmap;
	}
	

	


	private static Bitmap thinningAlgorithm(Bitmap newBitmap) {
		
 
		for (int y = 1; y < height-1; y++) {
			for (int x = 1; x < width-1; x++) {
				

				if( rules1to14(arrayBinary[x-1][y-1], arrayBinary[x][y-1] ,arrayBinary[x+1][y-1], 
					      arrayBinary[x-1][y]  , arrayBinary[x][y]	,arrayBinary[x+1][y]  ,
				          arrayBinary[x-1][y+1], arrayBinary[x][y+1] ,arrayBinary[x+1][y+1]	 )
				   ){
					
					newBitmap.setPixel(x, y, Color.rgb( 255, 255, 255) );//ปรับจุดกลางเป็นสีขาว //อันเก่า
					arrayBinary[x][y] = 0;												//อันเก่า
//					arrayBinary[x][y] = E ; //อันใหม่

				}

				
				if(x%2==0){      //อันใหม่ หมดเลย 
//					for (int b = 1; b < height-1; b++) {
//						for (int a = 1; a < width-1; a++) {
//							if(arrayBinary[a][b]==E){
//								arrayBinary[a][b] = 0 ;
//								bitmap.setPixel(a, b, Color.rgb( 255, 255, 255) );//ปรับจุดกลางเป็นสีขาว
//							}
//							
//						}
//					}
				}
				
				
			}//end for x
			
			
		}//end for y
		
			
			
			
				
				
				
//					arrayBinary[x][y] = 8;
				
				
				
				
		
		
		
		
		return newBitmap;
	}//end method

	


	
	private static boolean rules1to14(byte a, byte b, byte c, byte d, byte e,
			byte f, byte g, byte h, byte i) {
		
		if(e==1){


			if ( (a==0) && (b==0) && (d==0) &&  (f==1) && (h==1) ) {	 // rule 1
				return true;
			}
			else if ((b==0) && (c==0) && (d==1) && (f==0) && (h==1)) {// rule 2
				return true;
			}
			else if ((b==1) && (d==1)  && (f==0) && (h==0) && (i==0)) {// rule 3
				return true;
			}
			else if ((b==1) && (d==0)  && (f==1) && (g==0) && (h==0)) {// rule 4
				return true;
			}
			else if ((a==0) && (b==0) && (c==0)  && (g==1) && (h==1)) {// rule 5
				return true;
			}
			else if ((a==1) && (c==0) && (d==1) && (f==0) && (i==0)) {// rule 6
				return true;
			}
			else if ((b==1) && (c==1)  && (g==0) && (h==0) && (i==0)) {// rule 7
				return true;
			}
			else if ((a==0) && (d==0)  && (f==1) && (g==0) && (i==1)) {// rule 8
				return true;
			}
			
			
			if ( (a==1)&&(b==1)&&(c==E)&&(d==E)&&(f==0)&&(g==0)&&(h==0)&&(i==0) ){// rule 9
				return true;
			}
			else if ( (a==1)&&(b==1)&&(c==E)&&(d==E)&&(f==E)&&(g==0)&&(h==0)&&(i==0) ){// rule 10
				return true;
			}
			else if ( (a==E)&&(b==E)&&(c==0)&&(d==1)&&(f==0)&&(g==1)&&(h==E)&&(i==0) ){// rule 11
				return true;
			}
			else if ( (a==1)&&(b==1)&&(c==0)&&(d==E)&&(f==0)&&(g==0)&&(h==0)&&(i==0) ){// rule 12
				return true;
			}
			else if ( (a==0)&&(b==E)&&(c==1)&&(d==0)&&(f==E)&&(g==0)&&(h==0)&&(i==0) ){// rule 13
				return true;
			}
			else if ( (a==0)&&(b==E)&&(c==1)&&(d==0)&&(f==1)&&(g==0)&&(h==0)&&(i==0) ){// rule 14
				return true;
			}
		}
		 return false;
	}
	

	private static boolean rules9to14(byte a, byte b, byte c, byte d, byte e,
			byte f, byte g, byte h, byte i) {
		
		if(e==1){
			

			

			if ( (a==1)&&(b==1)&&(c==E)&&(d==E)&&(f==0)&&(g==0)&&(h==0)&&(i==0) ){// rule 9
				return true;
			}
			else if ( (a==1)&&(b==1)&&(c==E)&&(d==E)&&(f==E)&&(g==0)&&(h==0)&&(i==0) ){// rule 10
				return true;
			}
			else if ( (a==E)&&(b==E)&&(c==0)&&(d==1)&&(f==0)&&(g==1)&&(h==E)&&(i==0) ){// rule 11
				return true;
			}
			else if ( (a==1)&&(b==1)&&(c==0)&&(d==E)&&(f==0)&&(g==0)&&(h==0)&&(i==0) ){// rule 12
				return true;
			}
			else if ( (a==0)&&(b==E)&&(c==1)&&(d==0)&&(f==E)&&(g==0)&&(h==0)&&(i==0) ){// rule 13
				return true;
			}
			else if ( (a==0)&&(b==E)&&(c==1)&&(d==0)&&(f==1)&&(g==0)&&(h==0)&&(i==0) ){// rule 14
				return true;
			}
		}
		 return false;
	}





	private static boolean rules1to8(byte a, byte b, byte c, byte d, byte e, byte f, byte g, byte h, byte i) {
		
		if ( (a==0) && (b==0) && (d==0) && (e==1) && (f==1) && (h==1) ) {	 // rule 1
			return true;
		}
		else if ((b==0) && (c==0) && (d==1) && (e==1) && (f==0) && (h==1)) {// rule 2
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
		
		
		
		
		
		
		
		
//		else if ( (a==1)&&(b==1)&&(e==1)&&(f==0)&&(g==0)&&(h==0)&&(i==0) ){// rule 9
//			return true;
//		}
//		else if ( (a==1)&&(b==1)&&(e==1)&&(g==0)&&(h==0)&&(i==0) ){// rule 10
//			return true;
//		}
//		else if ( (c==0)&&(d==1)&&(e==1)&&(f==0)&&(g==1)&&(i==0) ){// rule 11
//			return true;
//		}
//		else if ( (a==1)&&(b==1)&&(c==0)&&(e==1)&&(f==0)&&(g==0)&&(h==0)&&(i==0) ){// rule 12
//			return true;
//		}
//		else if ( (a==0)&&(c==1)&&(d==0)&&(e==1)&&(g==0)&&(h==0)&&(i==0) ){// rule 13
//			return true;
//		}
//		else if ( (a==0)&&(c==1)&&(d==0)&&(e==1)&&(f==1)&&(g==0)&&(h==0)&&(i==0) ){// rule 14
//			return true;
//		}
		
							
						
					
		
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