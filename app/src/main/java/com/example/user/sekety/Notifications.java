package com.example.user.sekety;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class Notifications extends Activity {
    ListView lvNotificationsList;

    private String notificationTable = "notifications";
    protected List<ParseObject> myNotificationsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        lvNotificationsList = (ListView) findViewById(R.id.lv_actions);

        showNotificationsInListView();
    }
    private void showNotificationsInListView()
    {

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(notificationTable);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null)  //If fetching data is successful
                {
                    myNotificationsList = list;

                    lvNotificationsList.setDivider(new ColorDrawable(getResources().getColor(android.R.color.black)));
                    lvNotificationsList.setDividerHeight(2);

                    lvNotificationsList.setAdapter(new MyListViewAdapter(getApplicationContext(), myNotificationsList));
                } else  //If Error in fetching data
                {

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notifications, menu);
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
