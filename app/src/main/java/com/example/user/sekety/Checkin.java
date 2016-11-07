package com.example.user.sekety;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.sekety.R;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.ParseException;

public class Checkin extends Activity {
    String placeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);
        final EditText check_in_text=(EditText)findViewById(R.id.check_in_text);

        placeName=getIntent().getStringExtra("PlaceName");

        check_in_text.setText("- is At " + placeName + ".");

        Button post=(Button)findViewById(R.id.make_check_in);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the status has entered
                ParseUser currentUser = ParseUser.getCurrentUser();
                String currentUserName = currentUser.getUsername();
                //save the status on parse
                String PlaceName = check_in_text.getText().toString();

                if (PlaceName.isEmpty()) {
                    //there was aproblem
                    AlertDialog.Builder builder = new AlertDialog.Builder(Checkin.this);
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
                } else {

                    //save the status on parse
                    ParseObject statusObject = new ParseObject("post");
                    statusObject.put("checkin_address", PlaceName);
                    statusObject.put("username", currentUserName);

                    statusObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(com.parse.ParseException e) {
                            if (e == null) {
                                //successfully status stored in parse
                                Toast.makeText(Checkin.this, "success", Toast.LENGTH_LONG).show();
                                //take the user to home page
                                Intent takeUserToHome = new Intent(Checkin.this, HomePage.class);
                                startActivity(takeUserToHome);

                            } else {
                                //there was aproblem
                                AlertDialog.Builder builder = new AlertDialog.Builder(Checkin.this);
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
                        }


            }

        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_checkin, menu);
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
