package com.example.user.sekety;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ParseException;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class HomePage extends ListActivity{
    protected List<ParseObject> mstatus;
    protected List<ParseObject> mphoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        EditText ed = (EditText) findViewById(R.id.statusbar);
        ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Post.class);
                startActivity(intent);
            }
        });
        ParseUser currentUser=ParseUser.getCurrentUser();
        if(currentUser!=null){
            //show profile2 the home page
            //ParseObject statusObject = new ParseObject("post");

            ParseQuery<ParseObject> query=new ParseQuery<ParseObject>("post");
            query.orderByDescending("createdAt");
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
                        Intent takeUserToLogin=new Intent(HomePage.this,Login.class);
                        startActivity(takeUserToLogin);
                    }
                }

            });



        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_home_page, menu);
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

        if (id == R.id.action_search) {

            Intent intent = new Intent(this, Search.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_follower) {

            Intent intent = new Intent(this, Followers.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
