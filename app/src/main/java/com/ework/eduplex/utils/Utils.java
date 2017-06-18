package com.ework.eduplex.utils;

/**
 * Created by eWork on 3/11/2016.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    /**
     * This method is used to check mobile phone number format 08xxx.
     * @param pn String mobile phone number
     * @return
     */
    public static boolean checkInputHP(String pn) {
        //Test String
        try {
            if (pn.substring(0, 2).equals("08"))
                return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * This method is used to get formatted nominal, (i.e. 5000 to Rp. 5.000).
     * @param nominal String nominal
     * @return
     */
    public static String getShownNominal(String nominal) {
        String result = "";

        String a = nominal.replace(" ", "");

        String j = "";
        String k = a;
        if (a.endsWith(".00")) {
            j = a.substring(0, a.length() - 3);
            k = j;
        }
        if (a.endsWith(".0")) {
            j = a.substring(0, a.length() - 2);
            k = j;
        }

        String z = k.replace("-", "");
        String b = z.replace("Rp.", "");
        String c = b.replace("Rp", "");
        String d = c.replace(",00", "");

        nominal = d.replace(".", "");

        int count = 1;
        for (int i = nominal.length() - 1; i >= 0; i--) {
            result = nominal.charAt(i) + result;
            if (count % 3 == 0 && i != 0)
                result = "." + result;
            count++;
        }
        return "Rp. " + result;
    }

    /**
     * This method is used to normalize formatted nominal, (i.e. Rp.5.000 to 5000)
     * @param nominal String formatted nominal
     * @return
     */
    public static String getNormalizeNominal(String nominal) {
        try {
            String result = "";
            String a = nominal.replace(" ", "");

            String j = "";
            String k = a;
            if (a.endsWith(".00")) {
                j = a.substring(0, a.length() - 3);
                k = j;
            }

            String z = k.replace("-", "");
            String b = z.replace("Rp.", "");
            String c = b.replace("Rp", "");
            String d = c.replace(",00", "");

            nominal = d.replace(".", "");

            return nominal;
        } catch (Exception e) {
            e.printStackTrace();
            return nominal;
        }
    }

    /**
     * This method is used to get formatted nominal by int input type, (i.e. 5000 to Rp. 5.000).
     * @param nominal Nominal in integer
     * @return
     */
    public static String getShownNominal(int nominal) {
        String result = "";
        String s = nominal + "";

        String a = s.replace(" ", "");

        String j = "";
        String k = a;
        if (a.endsWith(".00")) {
            j = a.substring(0, a.length() - 3);
            k = j;
        }

        String z = k.replace("-", "");
        String b = z.replace("Rp.", "");
        String c = b.replace("Rp", "");
        String d = c.replace(",00", "");

        s = d.replace(".", "");

        int count = 1;
        for (int i = s.length() - 1; i >= 0; i--) {
            result = s.charAt(i) + result;
            if (count % 3 == 0 && i != 0)
                result = "." + result;
            count++;
        }
        return "Rp. " + result;
    }

    /**
     * This method is used to get formatted date dd mm yyyy.
     * @return
     */
    public static String getMyFormattedDate() {
        String timeStamp = new SimpleDateFormat("dd MMM yyyy").format(new Date());
        return timeStamp;
    }

    /**
     * This method is used to encode bitmap image to base64.
     * @param image Bitmap image
     * @param compressFormat Compression format such as JPEG, etc.
     * @param quality Quality in percent 50, 80, 100, etc.
     * @return
     */
    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        try {
            ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
            image.compress(compressFormat, quality, byteArrayOS);
            return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * This method is used to decode String base64 of image to bitmap image.
     * @param input String base64 of image.
     * @return Return the bitmap image.
     */
    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    /**
     * This method is used to decodes image and scales it to reduce memory consumption.
     * @param f File of image.
     * @return Return compressed bitmap image.
     */
    public static Bitmap decodeFile(File f) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE=480;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;

            Bitmap mBitmapImage = BitmapFactory.decodeStream(new FileInputStream(f), null, o2);

            return compressImageBitmap(mBitmapImage);

        } catch (FileNotFoundException e) {}
        return null;
    }

    /**
     *
     * @param bitmap Bitmap of image that want to be compressed.
     * @return Compressed bitmap of image.
     */
    public static Bitmap compressImageBitmap(Bitmap bitmap) {

        Bitmap mBitmap = bitmap;

        int maxWidth = 600;
        int maxHeight = 600;
        int mBitmapWidth = mBitmap.getWidth();
        int mBitmapHeight = mBitmap.getHeight();
        while (mBitmapWidth > maxHeight || mBitmapHeight  > maxHeight) {
            Log.d("dkdkdkdkdk", "w: "+mBitmapWidth);
            Log.d("dkdkdkdkdk", "h: "+mBitmapHeight);
            mBitmapWidth = (int) (mBitmapWidth * 0.5);
            mBitmapHeight = (int) (mBitmapHeight * 0.5);
        }

        mBitmap = Bitmap.createScaledBitmap(mBitmap, mBitmapWidth, mBitmapHeight, true);

        Log.d("dkdkdkdkdk", "fw: "+mBitmap.getWidth());
        Log.d("dkdkdkdkdk", "fh: "+mBitmap.getHeight());

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        Bitmap decodedBitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));

        return decodedBitmap;
    }

    /**
     * This method is used to get TextWatcher for nominal input field, to modified shown nominal with the format i.e. Rp.100.000.
     * @param mEditText Passing the input edittext
     * @return Return the TextWatcher
     */
    public static TextWatcher getNominalFormatTextWatcher(final EditText mEditText) {

        TextWatcher mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try {
                    String nominal = Utils.getNormalizeNominal(mEditText.getText().toString()) + "";

                    if (nominal.substring(0,1).equals("0")) {
                        mEditText.removeTextChangedListener(this);
                        mEditText.setText("");
                        mEditText.addTextChangedListener(this);
                    } else {
                        mEditText.removeTextChangedListener(this);
                        mEditText.setText(Utils.getShownNominal(nominal));
                        mEditText.addTextChangedListener(this);
                        mEditText.setSelection(mEditText.getText().length());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mEditText.removeTextChangedListener(this);
                    mEditText.setText("");
                    mEditText.addTextChangedListener(this);
                }
            }
        };

        return mTextWatcher;
    }
}

