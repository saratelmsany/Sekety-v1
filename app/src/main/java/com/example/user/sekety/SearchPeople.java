package com.example.user.sekety;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.sekety.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class SearchPeople extends ListActivity {
    EditText ed;
    Button searchbtn;
    String name;
    List<ParseUser> mfriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_people);

        ed = (EditText)findViewById(R.id.searchView);

        searchbtn = (Button)findViewById(R.id.ok);
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = ed.getText().toString();
                ParseQuery<ParseUser> query = ParseUser.getQuery();
                query.whereEqualTo("username",name);
                query.findInBackground(new FindCallback<ParseUser>() {
                    @Override
                    public void done(List<ParseUser> list, ParseException e) {
                        if(e == null) {
                            if (list.size() > 0) {
                                mfriend = list;
                                SearchAdapter adapter = new SearchAdapter(getListView().getContext(), mfriend);
                                setListAdapter(adapter);
                                Toast.makeText(getApplicationContext(),"Found",Toast.LENGTH_LONG).show();

                            }
                        }
                    }
                });
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_people, menu);
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
