package com.example.user.sekety;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class DashBoard extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
    }

    public void openHomepage(View view){
        Intent in = new Intent(DashBoard.this,HomePage.class);
        startActivity(in);
    }
    public void openProfilepage(View view){
        Intent in = new Intent(DashBoard.this,Profile.class);
        startActivity(in);
    }
    public void openChatpage(View view){
        Intent in = new Intent(DashBoard.this,UserList.class);
        startActivity(in);
    }
    public void openSeketypage(View view){
        Intent in = new Intent(DashBoard.this,SmartTransportation.class);
        startActivity(in);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dash_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
}
