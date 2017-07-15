package com.camera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

public class ToThinning2 {

    private static short[][] arrayGray;
    private static byte[][] arrayBinary;
    private static int width;
    private static int height;
    private static int threshold=1;
    
    static double []meanGray1;
    static double []meanGray2;
    static final byte E=100;
    
	public static Bitmap start(Bitmap bitmap) {
		
		
		width = bitmap.getWidth();
        height = bitmap.getHeight();
        Log.i("show","ขนาด -->> "+width +" -> "+ height);

        arrayGray = new short[width][height];
        arrayBinary = new byte[width][height];
        
        meanGray1=new double[width];
        meanGray2=new double[height];
        


        //mean(Gray)
        int mean=0;
        for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int pixel = bitmap.getPixel(x, y);
				short red = (short) Color.red(pixel);
				short green = (short) Color.green(pixel);
				short blue = (short) Color.blue(pixel);
				
				
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
				
				arrayGray[x][y]= red = green = blue = (short) (red * 0.299 + green * 0.587 + blue * 0.114);
				mean+=arrayGray[x][y];
				
				
				
			}
        }
        mean=mean/(width*height);
        Log.i("show","ค่า mean คือ " + mean);
        //mean(Gray)
        
        
        
        //start threshold
        
        //mean(a_gray,1) อันใหม่
        for(int i=0;i<width;i++){
        	double totalHeight=0;
        	for(int j=0;j<height;j++){
        		totalHeight+=arrayGray[i][j];
        	}
        	meanGray1[i]=totalHeight/height/height;    //mean(a_gray) ./ size(a_gray,1) 
//        	Log.i("show","   meanGray1[i] คือ " + meanGray1[i]);
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
        
        sum=sum*255/1.2638;
        threshold=(int) sum;
        
        Log.i("show","รวม  threshold  คือ " + sum);
        
        
        //end threshold
        
        
        
       //mean(Gray)./size(Gray,1)
//        int[] sizeGray1 = new int[height];
//        int meanSizeGray1=0;
//        for(int i=0;i<height;i++){
//        	sizeGray1[i]=0;
//        }
//        for (int y = 0; y < height; y++) {
//			for (int x = 0; x < width; x++) {
//				sizeGray1[y]+=arrayGray[x][y];
//			}
//			meanSizeGray1+=(sizeGray1[y]/width);
//        }
//        meanSizeGray1=meanSizeGray1/height;
//        Log.i("show","รวม meanSizeGray1 คือ " + meanSizeGray1);
        //mean(Gray)./size(Gray,1)
             
        

        //to B&W
        for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				
				if(arrayGray[x][y]<threshold){
					bitmap.setPixel(x, y, Color.rgb( 0, 0, 0));
					arrayBinary[x][y]=1;
					
					if((x%2==0)&&(y%2==0)){
						//bitmap.setPixel(x, y, Color.rgb( 255, 255, 255));
						//arrayBinary[x][y]=0;
					}
				}else{
				}
				

			}//end for
		}//end for
        
		
		
//		textFile();
		

//		Bitmap thin = thinningAlgorithm(bitmap);

		for(int i=0 ;i<10; i++){
			bitmap = thinningAlgorithm(bitmap);
		}
		
		return bitmap;
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

					
//					bitmap.setPixel(x, y, Color.rgb( 100, 100, 100) );//ปรับจุดกลางเป็น E
					bitmap.setPixel(x, y, Color.rgb( 255, 255, 255) );//ปรับจุดกลางเป็นสีขาว
					arrayBinary[x][y] = 0 ;

				}
				
//				if((x%2==0)||(y%2==0)){

					if(x%2==0){
//					bitmap.setPixel(x, y, Color.rgb( 255, 255, 255) );
//					bitmap.setPixel(x, y, Color.rgb( 0, 0, 0) );
					if( rules9to14(arrayBinary[x-1][y-1], arrayBinary[x][y-1] ,arrayBinary[x+1][y-1], 
					      arrayBinary[x-1][y]  , arrayBinary[x][y]	,arrayBinary[x+1][y]  ,
				          arrayBinary[x-1][y+1], arrayBinary[x][y+1] ,arrayBinary[x+1][y+1] ) )
					{
						
						arrayBinary[x][y] = 0 ;
						bitmap.setPixel(x, y, Color.rgb( 255, 255, 255) );//ปรับจุดกลางเป็นสีขาว
					}
					
					
				}
			
			
			
				
				
				
//					arrayBinary[x][y] = 8;
				
				
				
				
			}//end for
		}//end for 
		
		
		
		
		
		return bitmap;
	}//end method

	



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