package com.example.user.sekety;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ParseException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.sekety.custom.CustomActivity;
import com.example.user.sekety.utils.Utils;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseUser;


public class Login extends CustomActivity {

    EditText usernameTxt, passwordTxt;
    Button loginBtn;
    TextView Breg;

    public void define() {
        usernameTxt = (EditText) findViewById(R.id.userName);
        passwordTxt = (EditText) findViewById(R.id.password);
        loginBtn = (Button) findViewById(R.id.login);
        Breg = (Button) findViewById(R.id.reg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTouchNClick(R.id.login);
        setTouchNClick(R.id.reg);
        Parse.initialize(this, "JOBxifiEtNy48afecqsGnT14xqcQ2xkLdMJqPLDG", "E1lJUTO3vVWdFRfhqa0Dem67KEZJMAq5huDSYjwQ");
        define();
        NewAccountBtnOnClick();

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.login) {
            String u = usernameTxt.getText().toString();
            String p = passwordTxt.getText().toString();
            if (u.length() == 0 || p.length() == 0) {
                Utils.showDialog(this, R.string.err_fields_empty);
                return;
            }
            final ProgressDialog dia = ProgressDialog.show(this, null,
                    getString(R.string.alert_wait));
            ParseUser.logInInBackground(u, p, new LogInCallback() {

                @Override
                public void done(ParseUser pu, com.parse.ParseException e) {
                    dia.dismiss();
                    if (pu != null) {
                        UserList.user = pu;
                        startActivity(new Intent(Login.this, DashBoard.class));
                        finish();
                    } else {
                        Utils.showDialog(
                                Login.this,
                                getString(R.string.err_login) + " "
                                        + e.getMessage());
                        e.printStackTrace();
                    }
                }
            });
        } else {
            startActivityForResult(new Intent(this, Registration.class), 10);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK)
            finish();

    }

    public void NewAccountBtnOnClick() {
        Breg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginToMain = new Intent(Login.this, Registration.class);
                startActivity(LoginToMain);
            }
        });
    }


}
