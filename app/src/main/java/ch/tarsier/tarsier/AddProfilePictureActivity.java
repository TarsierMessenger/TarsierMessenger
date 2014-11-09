package ch.tarsier.tarsier;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Activity to upload a profile picture, either from the Gallery or
 * by taking a new picture.
 *
 * @author Benjamin Paccaud
 * @author Romain Ruetschi
 *
 */
public class AddProfilePictureActivity extends Activity {

    private static final int PICK_IMAGE            = 0x01;
    private static final int REQUEST_IMAGE_CAPTURE = 0x02;
    private static final int PIC_CROP              = 0x03;
    private static final String TEMP_PHOTO_FILE = "profile_picture_temp.png";

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile_picture);
        mImageView = (ImageView) findViewById(R.id.testImage);
    }
//
    public void onClickNewPicture(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
        takePictureIntent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    //crop from http://stackoverflow.com/questions/2085003/how-to-select-and-crop-an-image-in-android
    public void onClickAddExisting(View view) {
        Intent pickImageIntent = new Intent(Intent.ACTION_PICK);
        pickImageIntent.setType("image/*");
        pickImageIntent.putExtra("crop", "true");
        pickImageIntent.putExtra("aspectX", 1);
        pickImageIntent.putExtra("aspectY", 1);
        pickImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
        pickImageIntent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        startActivityForResult(pickImageIntent, PICK_IMAGE);
    }

    private Uri getTempUri() {
        return Uri.fromFile(getTempFile());
    }

    private File getTempFile() {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

             // File tmp = null;
            File file = new File(Environment.getExternalStorageDirectory(),TEMP_PHOTO_FILE);
            try {
                file.createNewFile();
            } catch (IOException e) {
                Log.d("IOException","Unable to create new file");
            }

            return file;
        } else {
            //Error
            return null;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PICK_IMAGE:
                onImagePicked(resultCode, data);
                break;

            case REQUEST_IMAGE_CAPTURE:
                onImageCaptured(resultCode, data);
                break;
            case PIC_CROP:
                onImageCapturedAndCropped(resultCode, data);

            default:
                // TODO: Handle unknown request code.
        }
    }

    private void onImageCapturedAndCropped(int resultCode, Intent data) {
        onImagePicked(resultCode,data);
    }

    // crop from here: http://code.tutsplus.com/tutorials/capture-and-crop-an-image-with-the-device-camera--mobile-11458
    private void onImageCaptured(int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            Log.d("CameraError","No image found");
            return;
        }
          performCrop();
//        Bundle extras = data.getExtras();
//        Bitmap imageBitmap = (Bitmap) extras.get("data");
//        mImageView.setImageBitmap(addRoundMask(imageBitmap));
    }

    private void onImagePicked(int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            // TODO: Handle errors.
            return;
        }

        //InputStream imageStream;
        //File tempFile = getTempFile();

        Bitmap imageBitmap = getImageFrom(getTempUri());
        imageBitmap = addRoundMask(imageBitmap);
        savePicture(imageBitmap);
        //imageStream = getContentResolver().openInputStream(data.getData());
        mImageView.setImageBitmap(imageBitmap);
        //imageStream.close();
    }


    private Bitmap getImageFrom(Uri tempUri) {
        String filePath= Environment.getExternalStorageDirectory()
                +"/"+TEMP_PHOTO_FILE;
        Log.d("FilePathGet",filePath);
        Log.d("FilePathGet",tempUri.getPath());


        //TODO handle File not found. (decodeFile returns null)
        return  BitmapFactory.decodeFile(filePath);
    }

    private Bitmap addRoundMask(Bitmap original) {
        Bitmap output = Bitmap.createBitmap(original.getWidth(),
                                            original.getHeight(),
                                            Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, original.getWidth(), original.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(original.getWidth() / 2, original.getHeight() / 2,
                original.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(original, rect, rect, paint);

        return output;
    }

    public void performCrop() {
        //call the standard crop action intent (the user device may not support it)
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        //indicate image type and Uri
        cropIntent.setDataAndType(getTempUri(), "image/*");
        //set crop properties
        cropIntent.putExtra("crop", "true");
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        cropIntent.putExtra("outputX", 200);
        cropIntent.putExtra("outputY", 200);
        cropIntent.putExtra("return-data", true);
        //start the activity - we handle returning in onActivityResult
        startActivityForResult(cropIntent, PIC_CROP);
    }

    private void savePicture(Bitmap bitmap) {
        FileOutputStream out = null;
        try {
            Log.d("FilePathSave",getTempUri().getPath());
            out = new FileOutputStream(getTempUri().getPath());
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_profile_picture, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
