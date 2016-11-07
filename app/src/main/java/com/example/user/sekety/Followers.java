package com.example.user.sekety;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.sekety.utils.Utils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class Followers extends ListActivity {
    public List<ParseObject> mFriend;
    ListView userList;
    private ArrayAdapter<ParseUser> mainAdapter;
    private ArrayList<ParseUser> resultList;
    ImageView imageView;
    EditText editText;

    private Button search;
    public static ParseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        ParseUser currentUser = ParseUser.getCurrentUser();
        String user = currentUser.getUsername().toString();
        if (currentUser != null){
            ParseQuery<ParseObject> query =  ParseQuery.getQuery("Friend");
            query.whereEqualTo("friend_name",user);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> friend, ParseException e) {
                    if (e == null){
                        mFriend = friend;
                        CustomAdapter adapter = new CustomAdapter(getListView().getContext(), mFriend);
                        setListAdapter(adapter);

                    }else {}
                }
            });
        }





       // search = (Button)findViewById(R.id.ok);
       // userList = (ListView) findViewById(R.id.list);
        imageView = (ImageView)findViewById(R.id.profile_image);
        editText = (EditText)findViewById(R.id.searchView);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Followers.this,SearchPeople.class);
                startActivity(in);
            }
        });
       // follow_btn = (Button)findViewById(R.id.follow);
        //following_btn = (Button) findViewById(R.id.unfollow);
        resultList = new ArrayList<ParseUser>();
        mainAdapter = new ArrayAdapter<ParseUser>(this, R.layout.custom_row, R.id.username,resultList);

        //QueryImagesFromParse();
       /* search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchInput = search.getText().toString();
                final ParseQuery<ParseUser> query = ParseQuery.getQuery("username");
                query.whereMatches("username", searchInput);
                query.findInBackground(new FindCallback<ParseUser>() {
                    public void done(List<ParseUser> objects, ParseException e) {
                        if (e == null) {

                            for (int i = 0; i < objects.size(); i++) {
                                resultList.add(objects.get(i));
                            }
                            userList.setAdapter(mainAdapter);

                        } else {
                            // Something went wrong.
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "User is not Found",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

                userList.setAdapter(mainAdapter);

            }
        });*/
        //   friendship(true);






        /*follow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                following_btn.setVisibility(View.VISIBLE);
            }
        });*/

    }
    /*  public void friendship (boolean following){
          user.put("following", following);
          user.saveEventually();
      }*/
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        //  friendship(false);
    }

    public void loadFollowersList(){
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null){
            ParseQuery<ParseObject> query =  ParseQuery.getQuery("Friend");
            query.whereEqualTo("username",currentUser);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> friend, ParseException e) {
                    if (e == null){
                        mFriend = friend;
                        CustomAdapter adapter = new CustomAdapter(getListView().getContext(), mFriend);
                        setListAdapter(adapter);

                    }else {}
                }
            });
        }

    }
    @Override
    protected void onResume()
    {
        super.onResume();
        //loadFollowersList();
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
