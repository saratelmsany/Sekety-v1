package com.example.user.sekety;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.sekety.R;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.ParseException;
import java.util.List;

public class custom_dialog extends ListActivity {
    protected List<ParseObject> mcomment;
    String post_id1;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_dialog);


        Button write = (Button) findViewById(R.id.write);
        final TextView comment_text = (TextView) findViewById(R.id.comment_bar);
        final ParseUser currentUser = ParseUser.getCurrentUser();
        post_id1=getIntent().getStringExtra("postId");
        // date=getIntent().getStringExtra("date");

        if (currentUser != null) {
            //show user the home page
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("actions");
            query.whereEqualTo("post_id", post_id1);
            //   query.whereEqualTo("pointer",post_id1);
            query.orderByAscending("createdAt");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> comment, com.parse.ParseException e) {
                    if (e == null) {
                        //success
                        mcomment = comment;


                        CommentAdapter adapter = new CommentAdapter(getListView().getContext(), mcomment);
                        setListAdapter(adapter);

                    } else {
                        //there was aproblem alert user
                    }
                }
            });
        } else {
            //show the login screen
            Intent takeUserToLogin = new Intent(custom_dialog.this, Login.class);
            startActivity(takeUserToLogin);
        }
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newComment = comment_text.getText().toString();
                String currentUserName=currentUser.getUsername();

                if (newComment.isEmpty()) {
                    //there was aproblem
                    AlertDialog.Builder builder = new AlertDialog.Builder(custom_dialog.this);
                    builder.setMessage("Comment shouldn't be Empty");
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

                    ParseObject commentObject = new ParseObject("actions");
                    commentObject.put("comment", newComment);
                    commentObject.put("post_id", post_id1);
                    //commentObject.put("pointer",ParseObject.createWithoutData("post",post_id1));
                    commentObject.put("user_name",currentUserName);
                    commentObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(com.parse.ParseException e) {
                            if (e == null) {
                                //successfully comment stored in parse
                                Toast.makeText(custom_dialog.this, "success", Toast.LENGTH_LONG).show();

                            } else {
                                //there was aproblem
                                AlertDialog.Builder builder = new AlertDialog.Builder(custom_dialog.this);
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
        getMenuInflater().inflate(R.menu.menu_custom_dialog, menu);
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
