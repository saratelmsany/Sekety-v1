package com.example.user.sekety;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.sekety.R;
import com.parse.FindCallback;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

public class photoAdapter extends ArrayAdapter<ParseObject> {

    protected Context mcontext;
    protected List<ParseObject> mphoto;
    public Uri fileUri;

    public photoAdapter(Context context, List<ParseObject> photo) {

        super(context, R.layout.upload_photo, photo);

        mcontext = context;
        mphoto = photo;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final viewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mcontext).inflate(
                    R.layout.upload_photo, null);
            holder = new viewHolder();
            holder.usernameHomePage = (TextView) convertView.findViewById(R.id.user_name);
            holder.userPhoto = (ImageView) convertView.findViewById(R.id.user_photo);

            holder.dateHome = (TextView) convertView.findViewById(R.id.date);
            holder.homeImage = (ImageView) convertView.findViewById(R.id.photo);
            holder.add_comment = (Button) convertView.findViewById(R.id.commentbtn);
            holder.add_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mcontext, custom_dialog.class);
                    mcontext.startActivity(intent);
                    //upload photo

                }
            });
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }
        ParseObject photoObject = mphoto.get(position);

        String userName = photoObject.getString("username");
        holder.usernameHomePage.setText(userName);


        String time = photoObject.getCreatedAt().toString();
        holder.dateHome.setText(time);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("UploadedPhoto");
        query.whereEqualTo("username", userName);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, com.parse.ParseException e) {
                if (e == null) {
                    if (list.size() > 0) {
                        for (ParseObject object : list) {
                            ParseFile file;
                            file = object.getParseFile("photo_name");

                            String imageUrl = file.getUrl();
                            Uri imageUri = Uri.parse(imageUrl);
                            fileUri = Uri.parse(file.getUrl());

                            Picasso.with(getContext().getApplicationContext()).load(fileUri.toString()).into(holder.homeImage, new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                }

                                @Override
                                public void onError() {
                                    Toast.makeText(getContext().getApplicationContext(), "not done", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }


                }
            }

        });


        ParseQuery<ParseUser> query1 = ParseUser.getQuery();
        query1.whereEqualTo("username", userName);
        query1.findInBackground(new FindCallback<ParseUser>()

                                {
                                    @Override
                                    public void done(List<ParseUser> list, com.parse.ParseException e) {
                                        if (e == null) {
                                            if (list.size() > 0) {
                                                for (ParseUser user : list) {
                                                    ParseFile file;
                                                    file = user.getParseFile("profile_photo");
                                                    String imageUrl = file.getUrl();//live url
                                                    Uri imageUri = Uri.parse(imageUrl);
                                                    fileUri = Uri.parse(file.getUrl());
                                                }
                                                Picasso.with(getContext().getApplicationContext()).load(fileUri.toString()).into(holder.userPhoto, new com.squareup.picasso.Callback() {
                                                    @Override
                                                    public void onSuccess() {

                                                    }

                                                    @Override
                                                    public void onError() {
                                                        Toast.makeText(getContext().getApplicationContext(), "not done", Toast.LENGTH_LONG).show();
                                                    }

                                                });
                                            }
                                        }
                                    }


                                }

        );

        return convertView;
    }

    class viewHolder {
        TextView usernameHomePage;

        TextView dateHome;

        ImageView homeImage;

        ImageView userPhoto;

        Button add_comment;


    }
}
