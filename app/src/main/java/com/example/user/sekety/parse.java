package com.example.user.sekety;

import android.app.Application;

import com.parse.Parse;


/**
 * Created by profile2 on 3/25/2016.
 */
public class parse extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
      Parse.initialize(this, "JOBxifiEtNy48afecqsGnT14xqcQ2xkLdMJqPLDG", "E1lJUTO3vVWdFRfhqa0Dem67KEZJMAq5huDSYjwQ");


    }
}
