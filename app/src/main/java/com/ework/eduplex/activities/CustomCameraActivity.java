package com.ework.eduplex.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.PointF;
import android.hardware.Camera;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ework.eduplex.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by eWork on 8/4/2016.
 */
public class CustomCameraActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.rlTakePicture)
    RelativeLayout rlTakePicture;
    ImageView image;
    @Bind(R.id.rlDirectorSquare)
    RelativeLayout rlDirectorSquare;
    private SurfaceView preview = null;
    private SurfaceHolder previewHolder = null;
    private Camera camera = null;
    private boolean inPreview = false;
    Bitmap bmp, itembmp;
    static Bitmap mutableBitmap;
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
    File imageFileName = null;
    File imageFileFolder = null;
    private MediaScannerConnection msConn;
    Display d;
    int screenhgt, screenwdh;
    ProgressDialog dialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_camera);
        ButterKnife.bind(this);

        initComponents();

        image = (ImageView) findViewById(R.id.image);
        preview = (SurfaceView) findViewById(R.id.surface);

        previewHolder = preview.getHolder();
        previewHolder.addCallback(surfaceCallback);
        previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        previewHolder.setFixedSize(getWindow().getWindowManager()
                .getDefaultDisplay().getWidth(), getWindow().getWindowManager()
                .getDefaultDisplay().getHeight());
    }

    private void initComponents() {

        requestFullScreenLayout();
        requestTransparentStatusBar();

        //REQUEST SCREEN ALWAYS ON
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //GET DISPLAY SIZE
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int displayWidth = size.x;
        int displayHeight = size.y;

        //SET SIZE BASED ON KTP SIZE COMPARISON 1:1.5
        int height = displayHeight - 100;
        int width = height + (height / 2);

        rlDirectorSquare.getLayoutParams().height = height;
        rlDirectorSquare.getLayoutParams().width = width;
    }

    @OnClick(R.id.rlTakePicture)
    public void onClick() {
        camera.takePicture(null, null, photoCallback);
        inPreview = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            camera = Camera.open();
        } catch (Exception e) {}

    }

    @Override
    public void onPause() {
        if (inPreview) {
            camera.stopPreview();
        }

        camera.release();
        camera = null;
        inPreview = false;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onDestroy();
    }

    private Camera.Size getBestPreviewSize(int width, int height, Camera.Parameters parameters) {
        Camera.Size result = null;
        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size;
                } else {
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;
                    if (newArea > resultArea) {
                        result = size;
                    }
                }
            }
        }
        return (result);
    }

    private Camera.Size getBestPictureSize(int width, int height, Camera.Parameters parameters) {
        Camera.Size result = null;
        for (Camera.Size size : parameters.getSupportedPictureSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size;
                } else {
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;
                    if (newArea > resultArea) {
                        result = size;
                    }
                }
            }
        }
        return (result);
    }

    SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                camera.setPreviewDisplay(previewHolder);
            } catch (Throwable t) {
                Log.e("surfaceCallback",
                        "Exception in setPreviewDisplay()", t);
                Toast.makeText(CustomCameraActivity.this, t.getMessage(), Toast.LENGTH_LONG)
                        .show();
            }
        }

        public void surfaceChanged(SurfaceHolder holder,
                                   int format, int width,
                                   int height) {

            Camera.Parameters parameters = camera.getParameters();
            //SET THE PARAMS
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
//            parameters.setPictureFormat(PixelFormat.JPEG);
//            parameters.setJpegQuality(100);

            Camera.Size previewSize = getBestPreviewSize(width, height,
                    parameters);

            Camera.Size picSize = getBestPictureSize(width, height,
                    parameters);


            if (previewSize != null && picSize != null) {
                try {
                    parameters.setPreviewSize(previewSize.width, previewSize.height);
                    parameters.setPictureSize(picSize.width/3, picSize.height/3);
                    camera.setParameters(parameters);
                    camera.startPreview();
                    inPreview = true;
                } catch (Exception e) {
                    Camera.Parameters p = camera.getParameters();
                    //SET THE PARAMS
//                    p.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                    Camera.Size s = p.getSupportedPreviewSizes().get(0);
                    p.setPreviewSize(s.width, s.height);
                    camera.setParameters(p);
                    camera.startPreview();
                    inPreview = true;
                }
            }
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            // no-op
        }
    };


    Camera.PictureCallback photoCallback = new Camera.PictureCallback() {
        public void onPictureTaken(final byte[] data, final Camera camera) {
            dialog = ProgressDialog.show(CustomCameraActivity.this, "", "Saving Photo");
            new Thread() {
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception ex) {
                    }
                    onPictureTake(data, camera);
                }
            }.start();
        }
    };


    public void onPictureTake(byte[] data, Camera camera) {

        bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
        mutableBitmap = bmp.copy(Bitmap.Config.RGB_565, true);

        //SAVE PHOTO TO MEMORY
        savePhoto(mutableBitmap);
        dialog.dismiss();

        //SEND RESULT
        Intent intent = new Intent();
        intent.putExtra("data", mutableBitmap);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void savePhoto(Bitmap bmp) {
        imageFileFolder = new File(Environment.getExternalStorageDirectory(), "Kreditplus Mobile");
        imageFileFolder.mkdir();
        FileOutputStream out = null;
        Calendar c = Calendar.getInstance();
        String date = fromInt(c.get(Calendar.MONTH)) + fromInt(c.get(Calendar.DAY_OF_MONTH)) + fromInt(c.get(Calendar.YEAR)) + fromInt(c.get(Calendar.HOUR_OF_DAY)) + fromInt(c.get(Calendar.MINUTE)) + fromInt(c.get(Calendar.SECOND));
        imageFileName = new File(imageFileFolder, date.toString() + ".jpg");
        try {
            out = new FileOutputStream(imageFileName);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            scanPhoto(imageFileName.toString());
            out = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String fromInt(int val) {
        return String.valueOf(val);
    }

    public void scanPhoto(final String imageFileName) {
        msConn = new MediaScannerConnection(CustomCameraActivity.this, new MediaScannerConnection.MediaScannerConnectionClient() {
            public void onMediaScannerConnected() {
                msConn.scanFile(imageFileName, null);
                Log.i("objinPhotoUtility", "connection established");
            }

            public void onScanCompleted(String path, Uri uri) {
                msConn.disconnect();
                Log.i("objinPhotoUtility", "scan completed");
            }
        });
        msConn.connect();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0) {
            onBack();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onBack() {
//        Log.e("onBack :", "yes");
//        camera.takePicture(null, null, photoCallback);
//        inPreview = false;
    }

    @Override
    public void onClick(View v) {
        camera.takePicture(null, null, photoCallback);
        inPreview = false;
    }

}
