package com.example.user.sekety;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class Registration extends Activity {

    EditText editUserName,editE_mail,editPassword,editre_password,editCity,editphone;
    Spinner day,month,year;
    Button confirm;
    RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        rg=(RadioGroup)findViewById(R.id.radiogroup);

        editUserName = (EditText)findViewById(R.id.Tusername);

        editE_mail = (EditText)findViewById(R.id.email);

        editPassword = (EditText)findViewById(R.id.pass);
        editre_password = (EditText)findViewById(R.id.re_pass);
        day = (Spinner)findViewById(R.id.day);
        month = (Spinner)findViewById(R.id.month);
        year = (Spinner)findViewById(R.id.years);

        editCity = (EditText)findViewById(R.id.city);
        editphone = (EditText)findViewById(R.id.phone);
        confirm = (Button)findViewById(R.id.confirm);

        AddData();

    }
    public void AddData(){
        confirm = (Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ParseUser user = new ParseUser();
                        user.setUsername(editUserName.getText().toString().trim());
                        user.setPassword(editPassword.getText().toString().trim());
                        user.put("re_password", editre_password.getText().toString().trim());
                        user.setEmail(editE_mail.getText().toString());
                        user.put("day",day.getSelectedItem().toString());
                        user.put("month",month.getSelectedItem().toString());
                        user.put("year",year.getSelectedItem().toString());
                        user.put("phone", editphone.getText().toString());
                        user.put("city", editCity.getText().toString());
                        int id = rg.getCheckedRadioButtonId();
                        if (id == -1){
                            //no item selected
                        }
                        else{
                            if (id == R.id.radio_male){

                                user.put("type","male");
                            }
                            if (id == R.id.radio_female){
                                user.put("type","female");
                            }
                        }
                        user.signUpInBackground(new SignUpCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Toast.makeText(Registration.this, "sign up succeed", Toast.LENGTH_LONG).show();


                                    Toast.makeText(Registration.this, "Data inserted", Toast.LENGTH_LONG).show();
                                    Intent in = new Intent(Registration.this, UserPhoto.class);
                                    startActivity(in);
                                } else {
                                    Toast.makeText(Registration.this, e.getMessage()+"Sign up didn't success", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }) ;



                    }
                }

        );
       }



    public void showMessage (String title,String Message){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
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
