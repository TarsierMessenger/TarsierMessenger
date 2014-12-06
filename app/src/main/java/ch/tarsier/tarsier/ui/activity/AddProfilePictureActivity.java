package ch.tarsier.tarsier.ui.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ActivityNotFoundException;
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
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import ch.tarsier.tarsier.R;
import ch.tarsier.tarsier.Tarsier;

/**
 * Activity to upload a profile picture, either from the Gallery or
 * by taking a new picture.
 *
 * @author benpac
 * @author romac
 *
 */
public class AddProfilePictureActivity extends Activity {

    private static final int PICK_IMAGE            = 0x01;
    private static final int REQUEST_IMAGE_CAPTURE = 0x02;
    private static final int PIC_CROP              = 0x03;
    private static final int SIZE_OUTPUT = 200;
    private static final int PNG_QUALITY = 100;

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile_picture);
        mImageView = (ImageView) findViewById(R.id.preview_profile_picture);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);
        }
    }

    /**
     * Click listener for the add new picture button.
     *
     * Create Intent to get the camera picture and save it to the tmp file
     *
     * @param view The view that is clicked on.
     */
    public void onClickNewPicture(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
        takePictureIntent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    //crop from http://stackoverflow.com/questions/2085003/how-to-select-and-crop-an-image-in-android

    /**
     * Click listener for the add existing picture button.
     *
     * Create intent to select image from the Gallery and crop it to a square
     * @param view view clicked on
     */
    public void onClickAddExisting(View view) {
        Intent pickImageIntent = new Intent(Intent.ACTION_PICK);
        pickImageIntent.setType("image/*");
        pickImageIntent.putExtra("crop", "true");
        pickImageIntent.putExtra("aspectX", 1);
        pickImageIntent.putExtra("aspectY", 1);
        pickImageIntent.putExtra("outputX", SIZE_OUTPUT);
        pickImageIntent.putExtra("outputY", SIZE_OUTPUT);
        pickImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
        pickImageIntent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        try {
            startActivityForResult(pickImageIntent, PICK_IMAGE);
        } catch (ActivityNotFoundException anfe) {
            // in case the intent is not supported by the device, do nothing. the image
            // is still to be stored and cropped. Toast should appear
            Toast.makeText(this, getString(R.string.error_toast_no_crop), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Get an URI for the temporary file to store the profile picture
     * @return the Uri of the temporary file
     */
    public Uri getTempUri() {
        return Uri.fromFile(getTempFile());
    }

    /**
     * Create a temporary file
     * @return the temporary file
     */
    private File getTempFile() {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

             // File tmp = null;
            File file = new File(Tarsier.app().getUserPreferences().getPicturePath());

            boolean fileExists = false;
            try {
                fileExists = file.createNewFile();
            } catch (IOException e) {
                Log.d("IOException", "Unable to create new file");
            }
            if (!fileExists) {
                Log.d("File", "File already created");
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

        try {
            switch (requestCode) {
                case PICK_IMAGE:
                    Log.d("onActivityResult", "Return from gallery + crop");
                    onImagePicked(resultCode);
                    break;

                case REQUEST_IMAGE_CAPTURE:
                    Log.d("onActivityResult", "Return from camera");
                    onImageCaptured(resultCode);
                    break;

                case PIC_CROP:
                    Log.d("onActivityResult", "Return from crop from camera");
                    onImagePicked(resultCode);
                    break;

                default:
                    throw new AddProfilePictureException();
            }
        } catch (AddProfilePictureException e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.error_add_profile_picture), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Launch the crop action once the capture is done.
     *
     * @param resultCode the result code from the onActivityResult
     * @throws AddProfilePictureException
     */
    private void onImageCaptured(int resultCode) throws AddProfilePictureException {
        if (resultCode != Activity.RESULT_OK) {
            throw new AddProfilePictureException();
        }
        performCrop();
    }

    /**
     * Take the image save in the temp file, add the mask and save the masked image.
     * @param resultCode the result code from the onActivityResult
     * @throws AddProfilePictureException
     */
    private void onImagePicked(int resultCode) throws AddProfilePictureException {
        if (resultCode != Activity.RESULT_OK) {
            Log.d("ResultCode", "Result code in Image picked is not ok");
            throw new AddProfilePictureException();
        }
        try {
            Bitmap imageBitmap = getImageFrom(getTempUri());
            imageBitmap = addRoundMask(squareBitmap(imageBitmap));
            savePicture(imageBitmap);
            mImageView.setImageBitmap(imageBitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new AddProfilePictureException();
        }
    }

    /**
     * Recover the Bitmap from the temp file. Throws a FileNotFoundException if the file isn't present
     * @param tempUri Uri of the temporary file
     * @return the bitmap from the temporary file
     * @throws FileNotFoundException
     */
    private Bitmap getImageFrom(Uri tempUri) throws FileNotFoundException {
        Log.d("FilePathGet", tempUri.getPath());
        Bitmap image = BitmapFactory.decodeFile(tempUri.getPath());
        if (image == null) {
            throw new FileNotFoundException();
        }
        return image;
    }

    /**
     *  Apply a round mask on the image. The "removed" pixels are transparent.
     *  Taken from : http://stackoverflow.com/questions/11932805/cropping-circular-area-from-bitmap-in-android
     *
     * @param original The image to apply the mask to
     * @return the original image with the round mask applied
     */
    private Bitmap addRoundMask(Bitmap original) {
        int imageWidth  = original.getWidth();
        int imageHeight = original.getHeight();
        int size = (imageHeight < imageWidth) ? imageHeight : imageWidth;

        Bitmap output = Bitmap.createBitmap(size, size,
                                            Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rectDest = new Rect(0, 0, size, size);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(size / 2, size / 2, size / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(original, rectDest, rectDest, paint);

        return output;
    }

    /**
     * Perform a "squarification" of a rectangular Bitmap. Takes the biggest centered square possible
     * in the rectangle
     * @param rectangle  The original bitmap, usually a rectangle
     * @return A square Bitmap
     */
    private Bitmap squareBitmap(Bitmap rectangle) {
        int imageWidth  = rectangle.getWidth();
        int imageHeight = rectangle.getHeight();
        int size = (imageHeight < imageWidth) ? imageHeight : imageWidth;
        return Bitmap.createBitmap(rectangle,
                imageWidth / 2 - size /2,
                imageHeight / 2 - size / 2,
                size, size);
    }

    /**
     * Launch the crop operation after the picture has been taken.
     * Take the image saved in the tmp file.
     *
     * inspired from : http://code.tutsplus.com/tutorials/capture-and-crop-an-image-with-the-device-camera--mobile-11458
     * @throws AddProfilePictureException
     */
    private void performCrop() throws AddProfilePictureException {
        Intent cropIntent = new Intent("com.android.camera.action.CROP");

        cropIntent.setDataAndType(getTempUri(), "image/*");
        cropIntent.putExtra("crop", "true");
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        cropIntent.putExtra("outputX", SIZE_OUTPUT);
        cropIntent.putExtra("outputY", SIZE_OUTPUT);
        cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
        cropIntent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        try {
            startActivityForResult(cropIntent, PIC_CROP);
        } catch (ActivityNotFoundException anfe) {
            // in case the intent is not supported by the device
            // do nothing. the image is still to be cropped in the center
            Toast.makeText(this, getString(R.string.error_toast_no_crop), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Save the bitmap on the temporary file. TODO this should be changed to a better location /better way to do it
     * @param bitmap the bitmap to be saved.
     * @throws AddProfilePictureException
     */
    private void savePicture(Bitmap bitmap) throws AddProfilePictureException {
        FileOutputStream out = null;
        try {
            Log.d("FilePathSave", getTempUri().getPath());
            out = new FileOutputStream(getTempUri().getPath());
            bitmap.compress(Bitmap.CompressFormat.PNG, PNG_QUALITY, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new AddProfilePictureException();
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
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
}
