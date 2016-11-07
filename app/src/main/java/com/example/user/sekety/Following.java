package com.example.user.sekety;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.net.ParseException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class Following extends ListActivity {
    public List<ParseObject> mFriend;
    ListView listView;
    EditText editText;
    List<ParseObject> mfriend;
    ImageView profile_image;
    TextView username;
    Button following , search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);
      //  listView = (ListView) findViewById(R.id.followinglistview);
        editText = (EditText) findViewById(R.id.searchView);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Following.this,SearchPeople.class);
                startActivity(in);
            }
        });




     //   search = (Button) findViewById(R.id.search_people);

       /* search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String username = searchView.getText().toString();

                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Friend");
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, com.parse.ParseException e) {
                        if (e == null) {
                            if (objects.size() > 0) {
                                ParseObject userObject = objects.get(0);
                                listView.setVisibility(View.VISIBLE);
                                CustomAdapter adapter = new CustomAdapter(getListView().getContext(), mfriend);
                                setListAdapter(adapter);
                            } else {
                                listView.setVisibility(View.INVISIBLE);
                                Toast.makeText(getApplicationContext(), "No results found",
                                        Toast.LENGTH_LONG).show();
                            }
                        }


                    }
                });
            }
        });*/
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        loadFollowersList();
    }

    public void loadFollowersList(){
        ParseUser currentUser = ParseUser.getCurrentUser();
        String user = currentUser.getUsername().toString();
        if (currentUser != null){
            ParseQuery<ParseObject> query = new ParseQuery<>("Friend");
            query.whereEqualTo("username",user);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> friend, com.parse.ParseException e) {
                    if (e == null){
                        mFriend = friend;
                        CustomAdapter1 adapter = new CustomAdapter1(getListView().getContext(), mFriend);
                        setListAdapter(adapter);

                    }else {}
                }
            });
        }

    }  @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        ParseObject listObject = mfriend.get(position);
        String objectId = listObject.getObjectId();
        Intent gotoProfile = new Intent(Following.this,Profile.class);
        gotoProfile.putExtra("objectId",objectId);
        startActivity(gotoProfile);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_following, menu);
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
