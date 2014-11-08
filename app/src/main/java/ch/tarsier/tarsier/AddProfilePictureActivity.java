package ch.tarsier.tarsier;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
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
import java.io.IOException;
import java.io.InputStream;

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
    private static final String TEMP_PHOTO_FILE = "profile_temp.png";

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile_picture);
        mImageView = (ImageView) findViewById(R.id.testImage);
    }

    public void onClickNewPicture(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    //crop from http://stackoverflow.com/questions/2085003/how-to-select-and-crop-an-image-in-android
    public void onClickAddExisting(View view) {
        Intent pickImageIntent = new Intent(Intent.ACTION_PICK);
        pickImageIntent.setType("image/*");
        pickImageIntent.putExtra("crop", "true");
        pickImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
        pickImageIntent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        startActivityForResult(pickImageIntent, PICK_IMAGE);
    }

    private Uri getTempUri() {
        return Uri.fromFile(getTempFile());
    }

    private File getTempFile() {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            File file = new File(Environment.getExternalStorageDirectory(),TEMP_PHOTO_FILE);
            try {
                file.createNewFile();
            } catch (IOException e) {
                Log.d("IOException","Unable to create new file");
            }

            return file;
        } else {

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

            default:
                // TODO: Handle unknown request code.
        }
    }

    private void onImageCaptured(int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            // TODO: Handle errors.
            return;
        }

        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        mImageView.setImageBitmap(addRoundMask(imageBitmap));
    }

    private void onImagePicked(int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            // TODO: Handle errors.
            return;
        }

        //InputStream imageStream;
        //File tempFile = getTempFile();

        String filePath= Environment.getExternalStorageDirectory()
                +"/"+TEMP_PHOTO_FILE;
        System.out.println("path "+filePath);


        Bitmap imageBitmap =  BitmapFactory.decodeFile(filePath);
        //imageStream = getContentResolver().openInputStream(data.getData());
        mImageView.setImageBitmap(addRoundMask(imageBitmap));
        //imageStream.close();
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
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
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
