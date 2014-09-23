package com.example.Halloween;


import android.graphics.Canvas;
import android.graphics.Paint;

public class Scene {

    // animation specific variables
    private float outerCircleRadius;
    private float circleRadius;

    private int centerX;
    private int centerY;

    private static int circleX;
    private static int circleY;

    private float angle;
    private float angle2;
    
    private static int X;
    private static int Y;
    private static int XX;
    private static int YY;
    private static int count;
    private static int r;
    
    private static int alpha;
    private static int alpha_m;
    private static int alpha_sign;
    
    public Scene() {
    	XX=250;
    	YY=250;
    	count=0;
    	r=0;
    	
    	alpha_m=0;
    	alpha=255;
    	alpha_sign=0;
    }

    public synchronized void updateSize(int width, int height) {

        centerX = width / 2;
        centerY = height / 2;

        int size = (width < height) ? width : height;

        outerCircleRadius = size / 3;
        circleRadius = outerCircleRadius * 0.2f;

        update();      
    }

    public synchronized void update() {

        angle += 1.0f;
        if (angle > 360f) {
            angle -= 360f;
        }
        
        angle2 += 0.8f;
        if (angle2 > 360f) {
            angle2 -= 360f;
        }


        circleX = (int) (centerX - outerCircleRadius * Math.cos(Math.toRadians(angle)));
        circleY = (int) (centerY - outerCircleRadius * Math.sin(Math.toRadians(angle)));
        
        X = (int) (centerX - outerCircleRadius * Math.sin(Math.toRadians(angle2)));
        Y = (int) (centerY - outerCircleRadius * Math.tan(Math.toRadians(angle2)));
        
     //   XX = (int) (centerX - outerCircleRadius * Math.tan(Math.toRadians(angle2-0.2)));
     //   YY = (int) (centerY - outerCircleRadius * Math.cos(Math.toRadians(angle2-0.2)));
        
        XX = (int) ((400) - (outerCircleRadius) * Math.sin(3*Math.toRadians(angle2-0.6)));
        YY = (int) ((400) - (outerCircleRadius) * Math.cos(3*Math.toRadians(angle2-0.6)));
       
        if(alpha==0)
        {
        	alpha_sign=-1;
        	alpha_m++;
        	
        	if (alpha_m==80)
        	{
        		alpha_m=0;
        		alpha_sign=1;		
        	}
        }
    
        if(alpha_sign==0)
        {
        alpha-=1;
        
        if(alpha<0)
        	alpha_sign=1;
        }
        
        if(alpha_sign==1)	
        {
        	 alpha+=1;
        	 
             if(alpha>255)
             	alpha_sign=0;
        }
        
    
        /*
        if(count>=30)
        	count=0;
        
        if (count==0)
        {
        Random rnd=new Random();
        r = rnd.nextInt(4);
        }
        
        if(r==0)
        {
        	XX+=4;
        	YY+=5;
        }
        
        if(r==1)
        {
        	XX-=4;
        	YY+=5; 
        }
        
        if(r==2)
        {
        	XX+=4;
        	YY-=5; 
        }
        
        if(r==3)
        {
        	XX-=4;
        	YY-=5;
        }
        
        if(XX > 600|| YY>600 )
        	XX=250;
            YY=250;
        count++;
        */
       
    }

    public synchronized static void draw(Canvas canvas) {

        // draw objects
     //   canvas.drawCircle(centerX, centerY, outerCircleRadius, outerCirclePaint);
      //  canvas.drawCircle(centerX, centerY, 5f, circlePaint);
     //   canvas.drawCircle(circleX, circleY, circleRadius, circlePaint);

    	Paint paint = new Paint();
    	paint.setAlpha(alpha);
    	
        {
                 canvas.drawBitmap(Wallpaper.backgroundImage2, Wallpaper.XPIXELS, 0, null);
                 canvas.drawBitmap(Wallpaper.image1,circleX ,circleY, paint);
                 canvas.drawBitmap(Wallpaper.image2,X ,Y, paint);
                 canvas.drawBitmap(Wallpaper.image2,XX ,YY, null);  
                 canvas.drawBitmap(Wallpaper.image2,X ,XX, null);  
        }

        
    }

}
