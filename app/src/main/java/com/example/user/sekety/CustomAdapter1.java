package com.example.user.sekety;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by profile2 on 3/30/2016.
 */
public class CustomAdapter1 extends ArrayAdapter<ParseObject> {
    private Context mContext;
    private List<ParseObject> mFriend;
    public Uri fileUri;
    String current_user;

    public CustomAdapter1(Context context, List<ParseObject> friend) {
        super(context, R.layout.custom_row, friend);
        mContext = context;
        mFriend = friend;
    }

   /* public CustomAdapter(Context context, List<ParseObject> friend) {
        super(context, R.layout.custom_row,friend);
        mContext = context;
        mFriend = friend;
    }*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.custom_row, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView
                    .findViewById(R.id.profile_image);
            holder.textView = (TextView) convertView
                    .findViewById(R.id.username);
            holder.follow = (Button) convertView
                    .findViewById(R.id.follow);
            holder.follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // suppose we have a user we want to follow
                    String otherUser = holder.textView.getText().toString();

                    // create an entry in the Follow table
                    ParseObject follow = new ParseObject("Friend");
                    follow.put("username", ParseUser.getCurrentUser());
                    follow.put("friend_name", otherUser);
                    follow.put("following", true);
                    follow.saveInBackground();
                }
            });

            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        ParseObject object = mFriend.get(position);
        //Profile image
        // Picasso.with(getContext().getApplicationContext())
        //     .load(object.getParseFile("profile_photo").getUrl())
        //   .noFade().into(holder.imageView);
        //UserName
        String username = object.getString("friend_name");
        holder.textView.setText(username);
        //follow
       /* int follows = object.getInt("follow");
        holder.follow.setText("" + follows + "follow");
        //following
        int following = object.getInt("follow");
        holder.following.setText(""+following+"follow");*/

        holder.follow.setBackgroundColor(Color.parseColor("#BE2619"));
        holder.follow.setText("following");
        current_user = ParseUser.getCurrentUser().getUsername().toString();

        holder.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Friend");
                query.whereEqualTo("friend_name", holder.textView.getText().toString());
                query.whereEqualTo("username", current_user);
                query.findInBackground(new FindCallback<ParseObject>() {

                    @Override
                    public void done(List<ParseObject> list, ParseException e) {
                        if (e == null) {
                            for (ParseObject obj : list
                                    ) {

                                obj.deleteInBackground();

                            }

                        }
                    }
                });
                holder.follow.setBackgroundColor(Color.parseColor("#FFFFFF"));
                holder.follow.setText("follow");
                    }
                });


        ParseQuery<ParseUser> query1 = ParseUser.getQuery();
        query1.whereEqualTo("username", username);
        query1.findInBackground(new FindCallback<ParseUser>() {
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
                        Picasso.with(getContext().getApplicationContext()).load(fileUri.toString()).into(holder.imageView, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                                Toast.makeText(getContext().getApplicationContext(), "not done", LENGTH_LONG).show();
                            }

                        });
                    }
                }
            }


        });

        return convertView;
    }

    public static class ViewHolder {
        ImageView imageView;
        TextView textView;
        Button follow;
        Button following;
    }
}
