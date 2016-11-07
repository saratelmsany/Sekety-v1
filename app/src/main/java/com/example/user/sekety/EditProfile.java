package com.example.user.sekety;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class EditProfile extends Activity {
    EditText username, Password, bio, city, phone_number,Email;
    Button save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        final ParseUser currentUser=ParseUser.getCurrentUser();
        final String userName = currentUser.getUsername().toString();
        username=(EditText)findViewById(R.id.editText);
        Email=(EditText)findViewById(R.id.editTextemail);
        bio=(EditText)findViewById(R.id.editText2);
        city=(EditText)findViewById(R.id.editText5);
        username.setText(userName);
        String email = currentUser.getEmail().toString();
        Email.setText(email);
        String City = currentUser.getString("city");
        city.setText(City);
        save=(Button)findViewById(R.id.button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser user = ParseUser.getCurrentUser();
                user.put("username",username.getText().toString());
                user.put("biography",bio.getText().toString());
                user.put("email",Email.getText().toString());
                user.put("city", city.getText().toString());
                user.saveInBackground(new SaveCallback() {
                    public void done(ParseException e) {
                        if (e != null) {
                            // Saved successfully
                            Toast.makeText(getApplication(), "Not updated", Toast.LENGTH_LONG).show();
                        } else {
                            // ParseException
                            Toast.makeText(getApplication(), "Updated Successfully.", Toast.LENGTH_LONG).show();

                            Intent in = new Intent(EditProfile.this,Profile.class);
                            startActivity(in);
                        }
                    }
                });
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
}
