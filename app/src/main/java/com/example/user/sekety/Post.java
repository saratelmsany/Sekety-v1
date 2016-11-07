package com.example.user.sekety;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Post extends Activity {


    protected EditText mUpdateStatus;
    protected Button mStatusUpdatebtn;

    protected ImageButton upload;
    protected ImageView mPreviewImageView;


    protected Button madd;
    ParseObject statusObject;
    private static final int RESULT_LOAD_IMAGE = 1;
    Button save;
    String picturePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ImageButton imb = (ImageButton) findViewById(R.id.imageButton);
        imb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Post.this, Search.class);
                startActivity(intent);
            }
        });


        mUpdateStatus = (EditText) findViewById(R.id.update_status_text);
        mStatusUpdatebtn = (Button) findViewById(R.id.update_status_button);
        ImageButton buttonLoadImage = (ImageButton) findViewById(R.id.uploadphoto);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        mStatusUpdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the status has entered
                ParseUser currentUser = ParseUser.getCurrentUser();
                String currentUserName = currentUser.getUsername();
                statusObject = new ParseObject("post");
                //save the status on parse
                String newStatus = mUpdateStatus.getText().toString();

                if (newStatus.isEmpty() && picturePath.isEmpty()) {
                    //there was aproblem
                    AlertDialog.Builder builder = new AlertDialog.Builder(Post.this);
                    builder.setMessage("Status should not be empty");
                    builder.setTitle("Oops!");
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            //close the dialog
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else if (picturePath!=null) {
                    Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                    // Convert it to byte
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    // Compress image to lower quality scale 1 - 100
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);


                    ParseFile file = new ParseFile(new File(picturePath));

                    file.saveInBackground();
                    //  statusObject.put("status_text", newStatus);
                    statusObject.put("username", currentUserName);
                    // if (file != null) {
                    statusObject.put("photo_name", file);
                    statusObject.saveInBackground();
                    statusObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                //successfully status stored in parse
                                Toast.makeText(Post.this, "success", Toast.LENGTH_LONG).show();
                                //take the user to home page
                                Intent takeUserToHome = new Intent(Post.this, HomePage.class);
                                startActivity(takeUserToHome);

                            } else {
                                //there was aproblem
                                AlertDialog.Builder builder = new AlertDialog.Builder(Post.this);
                                builder.setMessage(e.getMessage());
                                builder.setTitle("sorry");
                                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        //close the dialog
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }

                        }
                    });


                } else {
                    // ParseObject statusObject = new ParseObject("post");
                    statusObject.put("status_text", newStatus);
                    //  statusObject.put("new status", newStatus);
                    statusObject.put("username", currentUserName);
                    statusObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                //successfully status stored in parse
                                Toast.makeText(Post.this, "success", Toast.LENGTH_LONG).show();
                                //take the user to home page
                                Intent takeUserToHome = new Intent(Post.this, HomePage.class);
                                startActivity(takeUserToHome);

                            } else {
                                //there was aproblem
                                AlertDialog.Builder builder = new AlertDialog.Builder(Post.this);
                                builder.setMessage(e.getMessage());
                                builder.setTitle("sorry");
                                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        //close the dialog
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }

                        }
                    });
                    // statusObject.put("status_text", newStatus);
                    // statusObject.put("username", currentUserName);
                    //if (file != null) {
                    //   statusObject.put("photo_name", file);
                }

              /* else if(picturePath != null){


                    statusObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                //successfully status stored in parse
                                Toast.makeText(Post.this, "success", Toast.LENGTH_LONG).show();
                                //take the user to home page
                                Intent takeUserToHome = new Intent(Post.this, HomePage.class);
                                startActivity(takeUserToHome);

                            } else {
                                //there was aproblem
                                AlertDialog.Builder builder = new AlertDialog.Builder(Post.this);
                                builder.setMessage(e.getMessage());
                                builder.setTitle("sorry");
                                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        //close the dialog
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    });


                    Toast.makeText(Post.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                }
            }*/
            }
        });

        /////////////////////////////////////////////////////////
        //initialize



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
                && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            Log.e("path", picturePath + " ");
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.preview);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

}








