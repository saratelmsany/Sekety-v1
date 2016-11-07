package com.example.user.sekety;

import android.app.Activity;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import java.util.List;


public class Profile extends ListActivity {
    protected List<ParseObject> mstatus;
    protected List<ParseObject> mphoto;
    TextView tv , Bio;
    ImageView pro_photo;
    Button btneditProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        EditText ed = (EditText) findViewById(R.id.statusbarProfile);
        pro_photo = (ImageView)findViewById(R.id.previewImageView);
        pro_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Profile.this, ProfilePhoto.class);
                startActivity(in);
            }
        });
        ParseUser currentUser=ParseUser.getCurrentUser();
        ParseFile postImage = currentUser.getParseFile("profile_photo");
        String imageUrl = postImage.getUrl() ;//live url
        Uri imageUri = Uri.parse(imageUrl);
        Uri fileUri = Uri.parse(postImage.getUrl());

        Picasso.with(Profile.this).load(fileUri.toString()).into(pro_photo, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                Toast.makeText(Profile.this, "not done", Toast.LENGTH_LONG).show();
            }
        });
        ed.setOnClickListener(new View.OnClickListener()

                              {
                                  @Override
                                  public void onClick (View v){
                                      Intent intent = new Intent(getApplicationContext(), Post.class);
                                      startActivity(intent);
                                  }
                              }

        );
        btneditProfile=(Button)

                findViewById(R.id.EditProfile);

        btneditProfile.setOnClickListener(new View.OnClickListener()

                                          {
                                              @Override
                                              public void onClick (View v){
                                                  Intent in = new Intent(Profile.this, EditProfile.class);
                                                  startActivity(in);
                                              }
                                          }

        );

        String username = currentUser.getUsername().toString();
        String bio = currentUser.getString("biography");
        tv=(TextView)

                findViewById(R.id.usernameProfile);

        Bio=(TextView)

                findViewById(R.id.bio);

        Bio.setText(bio);
        tv.setText(username);
        if(currentUser!=null)

        {
            //show profile2 the home page
            //ParseObject statusObject = new ParseObject("post");

            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("post");
            query.orderByDescending("createdAt");
            query.whereEqualTo("username", username);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> status, com.parse.ParseException e) {
                    if (e == null) {
                        //success
                        mstatus = status;

                        StatusAdapter adapter = new StatusAdapter(getListView().getContext(), mstatus);
                        setListAdapter(adapter);

                    } else {
                        //there was aproblem alert profile2
                        Intent takeUserToLogin = new Intent(Profile.this, Login.class);
                        startActivity(takeUserToLogin);
                    }
                }

            });


        }


    }


    //check if external storage is mounted. helper method

    private boolean isExternalStorageAvailable(){
        String state = Environment.getExternalStorageState();
        if(state.equals(Environment.MEDIA_MOUNTED)){
            return true;
        }else{
            return false;
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_logout) {

            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_friendList) {

            Intent intent = new Intent(this, Following.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
