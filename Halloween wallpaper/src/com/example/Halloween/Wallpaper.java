package com.example.Halloween;

import com.example.Halloween.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.service.wallpaper.WallpaperService;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;

public class Wallpaper extends WallpaperService {

    private static final String TAG = "Wallpaper";
	public static Bitmap backgroundImage;
	public static Bitmap image1;
	public static Bitmap image2;
    public static int XPIXELS;
    public static Bitmap backgroundImage2;
    
    public static int[] touchX;
    public static int[] touchY;
    public static int count;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");

    }

    @Override
    public Engine onCreateEngine() {
        return new WallpaperEngine();
    }

    class WallpaperEngine extends Engine {

        private static final String TAG = "WallpaperEngine";

        private AnimationThread animationThread;
        private Scene scene;

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);

            Log.d(TAG, "onCreate");

            // create the scene
            scene = new Scene();
            
            // start animation thread; thread starts paused
            // will run onVisibilityChanged
            animationThread = new AnimationThread(surfaceHolder, scene);
            animationThread.start();

            image1 = BitmapFactory.decodeResource(getResources(),R.drawable.lori);
            backgroundImage = BitmapFactory.decodeResource(getResources(),R.drawable.background3);
            image2 = BitmapFactory.decodeResource(getResources(),R.drawable.duch);
            XPIXELS=100;
            
            //Scale
            {
            Context mContext = getBaseContext();
            DisplayMetrics displayMetrics = new DisplayMetrics(); 
            displayMetrics = mContext.getResources().getDisplayMetrics();
            int mScreenWidth = displayMetrics.widthPixels;
            int mSreenHeight = displayMetrics.heightPixels;
            
            float xScale = (float) mScreenWidth/ backgroundImage.getWidth();
            float yScale = (float) mSreenHeight/ backgroundImage.getHeight();
            float scale = Math.max(xScale, yScale); //selects the larger size to grow the images by

        //    scale = (float) (scale*1.1); //this allows for ensuring the image covers the whole screen.    
        //   float   scaledWidth = (float) (backgroundImage.getWidth() * (0.82));
            
         float   scaledWidth = scale * backgroundImage.getWidth();
         float   scaledHeight = scale * backgroundImage.getHeight();

         backgroundImage2 = Bitmap.createScaledBitmap(backgroundImage, (int)scaledWidth, (int)scaledHeight, true);     
            }
        }

        @Override
        public void onDestroy() {
            Log.d(TAG, "onDestroy");

            animationThread.stopThread();
            joinThread(animationThread);
            animationThread = null;

            super.onDestroy();
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            Log.d(TAG, "onVisibilityChanged: " + (visible ? "visible" : "invisible"));
            if (visible) {
                animationThread.resumeThread();
            } else {
                animationThread.pauseThread();
            }
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            Log.d(TAG, "onSurfaceChanged: width: " + width + ", height: " + height);

            scene.updateSize(width, height);

        }
        
        @Override
        public void onOffsetsChanged(float xOffset, float yOffset, float xStep, float yStep, int xPixels, int yPixels) 
        {
        	XPIXELS=xPixels;

        }

        private void joinThread(Thread thread) {
            boolean retry = true;
            while (retry) {
                try {
                    thread.join();
                    retry = false;
                } catch (InterruptedException e) {
                }
            }
        }

    }

}
