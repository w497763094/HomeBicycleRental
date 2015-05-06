/*
 *  COPYRIGHT NOTICE  
 *  Copyright (C) 2015, ticktick <lujun.hust@gmail.com>
 *  https://github.com/Jhuster/ImageCropper
 *   
 *  @license under the Apache License, Version 2.0 
 *
 *  @file    CropImageActivity.java
 *  @brief   Image Cropper Activity
 *  
 *  @version 1.0     
 *  @author  ticktick
 *  @date    2015/01/09    
 */
package com.wudebin.bicyclerental.imagecropper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.wudebin.bicyclerental.R;

public class CropImageActivity extends Activity {

    private Bitmap mBitmap;
    private Uri mInputPath  = null;
    private Uri mOutputPath = null;
    private CropImageView mCropImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cropimage);
        mCropImageView = (CropImageView)findViewById(R.id.CropWindow);
            
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras == null) {
            setResult(RESULT_CANCELED);
            return;
        }               
        mInputPath  = intent.getData();
        mOutputPath = extras.getParcelable(MediaStore.EXTRA_OUTPUT);
        if( mInputPath == null || mOutputPath == null ) {
            setResult(RESULT_CANCELED);
            finish();
            return;
        }
        mBitmap = loadBitmapWithInSample(mInputPath);
        if( mBitmap == null ) {
            setResult(RESULT_CANCELED);
            finish();
            return;
        }               
        mCropImageView.initialize(mBitmap,getCropParam(intent));        
    }
    
    @Override
    protected void onDestroy() {            
        if( mBitmap != null ) {
            mBitmap.recycle();      
        }
        mCropImageView.destroy();               
        super.onDestroy();
    }
    public void onClickRotate(View v) {
        mCropImageView.rotate();
        mCropImageView.invalidate();
    }
    
    public void onClickReset(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void onClickCrop(View v) {
        mCropImageView.crop();
        new SaveImageTask().execute(mCropImageView.getCropBitmap());
    }
    
    private class SaveImageTask extends AsyncTask<Bitmap,Void,Boolean> {

        private MaterialDialog mDialog;

        private SaveImageTask() {
            mDialog = new MaterialDialog.Builder(CropImageActivity.this)
            .progress(true,0)
            .content("正在上传")
            .build();
    	}
    	
    	@Override
        protected void onPreExecute() {
            mDialog.show();
    	}
    	
    	@Override
        protected void onPostExecute(Boolean result) {
            if(mDialog.isShowing()){
                mDialog.dismiss();
            }
            setResult(RESULT_OK, new Intent().putExtra(MediaStore.EXTRA_OUTPUT,mOutputPath));
            finish();
    	}
    	
        @Override
        protected Boolean doInBackground(Bitmap... params) {
            OutputStream outputStream = null;
            try {
                outputStream = getContentResolver().openOutputStream(mOutputPath);
                if (outputStream != null) {
                	params[0].compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                }
            } 
            catch (IOException e) {
            
            }
            finally {
                closeSilently(outputStream);
            }
            
            return Boolean.TRUE;
       }
    	
    }
    protected Bitmap loadBitmapWithInSample( Uri uri ) {
            
        final int MAX_VIEW_SIZE = 1024;
            
        InputStream in = null;
        try {
            in = getContentResolver().openInputStream(uri);
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, o);
            in.close();
            
            int scale = 1;
            if (o.outHeight > MAX_VIEW_SIZE || o.outWidth > MAX_VIEW_SIZE ) {
                scale = (int) Math.pow(2, (int) Math.round(Math.log(MAX_VIEW_SIZE / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            in = getContentResolver().openInputStream(uri);
            Bitmap b = BitmapFactory.decodeStream(in, null, o2);
            in.close();

            return b;
        } 
        catch (FileNotFoundException e) {
                
        } 
        catch (IOException e) {
                
        }
        return null;
    }
        
    protected static void closeSilently(Closeable c) {
        if (c == null) return;
        try {
            c.close();
        } 
        catch (Throwable t) {
        }
    }
    
    public static CropParam getCropParam(Intent intent) {
        CropParam params = new CropParam();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if( extras.containsKey(CropIntent.ASPECT_X) && extras.containsKey(CropIntent.ASPECT_Y) ) {
                params.mAspectX = extras.getInt(CropIntent.ASPECT_X);
                params.mAspectY = extras.getInt(CropIntent.ASPECT_Y);
            }
            if( extras.containsKey(CropIntent.OUTPUT_X) && extras.containsKey(CropIntent.OUTPUT_Y) ) {
                params.mOutputX = extras.getInt(CropIntent.OUTPUT_X);
                params.mOutputY = extras.getInt(CropIntent.OUTPUT_Y);
            }
            if( extras.containsKey(CropIntent.MAX_OUTPUT_X) && extras.containsKey(CropIntent.MAX_OUTPUT_Y) ) {
                params.mMaxOutputX = extras.getInt(CropIntent.MAX_OUTPUT_X);
                params.mMaxOutputY = extras.getInt(CropIntent.MAX_OUTPUT_Y);
            }
        }               
        return params;
    }
}
