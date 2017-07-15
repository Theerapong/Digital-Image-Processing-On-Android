package com.camera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;

public class ToBW_Auto {

	private static short[][] arrayR ;
    private static short[][] arrayG ;
    private static short[][] arrayB ;
    private static int width;
    private static int height;
    private static short[][] arrayGray;
    
    static double []meanGray1;
    static double []meanGray2;
    
    private static int threshold;
    
    
    
	public static Bitmap start(Bitmap bitmap) {
		
		String photoPath = Environment.getExternalStorageDirectory()+"/ATest/Sunset.jpg";
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		bitmap = BitmapFactory.decodeFile(photoPath, options);
		

		Log.i("show","ToBW_Auto -> start 333333 b" );
				
		width = bitmap.getWidth();
        height = bitmap.getHeight();

        arrayGray = new short[width][height];
        
        arrayR = new short[width][height];
        arrayG = new short[width][height];
        arrayB = new short[width][height];
        

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
        //mean(a_gray,1) อันใหม่
        for(int i=0;i<width;i++){
        	double totalHeight=0;
        	for(int j=0;j<height;j++){
        		totalHeight+=arrayGray[i][j];
        	}
        	meanGray1[i]=totalHeight/height;    //mean(a_gray) 
        	
        	meanGray1[i]=meanGray1[i]/height;   //mean(a_gray) ./ size(a_gray,1) 
//        	Log.i("show","   meanGray1["+i+"] คือ " + meanGray1[i]);
        }
        
        //sum(( mean(a_gray) ./ size(a_gray,1) ))
        double sum1=0;
        for(int i=0;i<width;i++){
        	sum1+=meanGray1[i];
        }
        ///( sum(( mean(a_gray) ./ size(a_gray,1) )) / size(a_gray,2) ) 
        sum1=sum1/width;
        
        
        
        
      //mean(a_gray,2) อันใหม่
        for(int i=0;i<height;i++){
        	double totalWidth=0;
        	for(int j=0;j<width;j++){
        		totalWidth+=arrayGray[j][i];
        	}
        	meanGray2[i]=totalWidth/width/width;   
        }
        //sum(( mean(a_gray) ./ size(a_gray,1) ))
        double sum2=0;
        for(int i=0;i<height;i++){
        	sum2+=meanGray2[i];
        }
        ///( sum(( mean(a_gray) ./ size(a_gray,1) )) / size(a_gray,2) ) 
        sum2=sum2/height;
        
        
        
        double sum=sum1+sum2;
        	Log.i("show","sum1 " + sum);
//        sum=sum*255; 
//        	Log.i("show","sum2 " + sum);
//        threshold=(int) sum;
        Log.i("show","ToBW_Auto ค่า auto threshold  จากสมการคือ  " + threshold);       
        //end threshold *************************
        
        

        //new double
        Double doub = sum;
        Integer inte = doub.intValue(); 
        
        double newDoub=sum-inte;
        Log.i("show"," bbbbb " + newDoub);
        
        sum=sum*255;
    	Log.i("show","sum3 " + sum);
        
        
        
        
        //To Gray
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				//ToBW
				if(arrayGray[x][y]<threshold){
					bitmap.setPixel(x, y, Color.rgb( 0, 0, 0));
				}else{
					bitmap.setPixel(x, y, Color.rgb( 255, 255, 255) );
				}
			
			}
		}

        
		return bitmap;
		
	}

}
