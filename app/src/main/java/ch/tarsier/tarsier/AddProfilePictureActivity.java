package ch.tarsier.tarsier;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile_picture);
    }

    public void onClickNewPicture(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void onClickAddExisting(View view) {
        Intent pickImageIntent = new Intent(Intent.ACTION_PICK);
        pickImageIntent.setType("image/*");

        startActivityForResult(pickImageIntent, PICK_IMAGE);
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
        // mImageView.setImageBitmap(imageBitmap);
    }

    private void onImagePicked(int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            // TODO: Handle errors.
            return;
        }

        InputStream imageStream;

        try {
            imageStream = getContentResolver().openInputStream(data.getData());
            Bitmap imageBitmap = BitmapFactory.decodeStream(imageStream);
            // mImageView.setImageBitmap(imageBitmap);

            imageStream.close();
        } catch (FileNotFoundException e) {
            // TODO: Show error message.
        } catch (IOException e) {
            // TODO: Show error message.
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
