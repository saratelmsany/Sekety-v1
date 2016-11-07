package com.example.user.sekety;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ProfilePhoto extends Activity {

    private static final int RESULT_LOAD_IMAGE = 1;
    Button save;
    String picturePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_photo);

        Button buttonLoadImage = (Button) findViewById(R.id.btnPictureSelect);
        buttonLoadImage.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View arg0) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);

            }
        });

        save = (Button) findViewById(R.id.save_btn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
// Locate the image in res >
                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
// Convert it to byte
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
// Compress image to lower quality scale 1 - 100
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
/*

Object image = null;
try {
String path = null;
image = readInFile(path);
} catch (Exception e) {
e.printStackTrace();
}
*/

// Create the ParseFile
                ParseFile file = new ParseFile(new File(picturePath));
// Upload the image into Parse Cloud
                file.saveInBackground();

// Create a New Class called "ImageUpload" in Parse
                ParseObject object = new ParseObject("User");
                ParseUser currentUser = ParseUser.getCurrentUser();

// Create a column named "ImageName" and set the string
// imgupload.put("photo", "url");
                object.put("username", currentUser.getUsername().toString());
                object.put("profile_photo", file);
                object.saveInBackground();
// Create a column named "ImageFile" and insert the image
                currentUser.put("profile_photo", file);

// Create the class and the columns
                currentUser.saveInBackground();

// Show a simple toast message
                Toast.makeText(ProfilePhoto.this, "Image Uploaded", Toast.LENGTH_SHORT).show();

            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
                && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            Log.e("path",picturePath+" ");
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.profilePicturePreview);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }

    }

    private byte[] readInFile(String path) throws IOException {
// TODO Auto-generated method stub
        byte[] data = null;
        File file = new File(path);
        InputStream input_stream = new BufferedInputStream(new FileInputStream(
                file));
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        data = new byte[16384]; // 16K
        int bytes_read;
        while ((bytes_read = input_stream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, bytes_read);
        }
        input_stream.close();
        return buffer.toByteArray();

    }

}
