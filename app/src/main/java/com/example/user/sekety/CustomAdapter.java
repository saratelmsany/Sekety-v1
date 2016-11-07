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
public class CustomAdapter extends ArrayAdapter<ParseObject> {
    private Context mContext;
    private List<ParseObject> mFriend;
    public Uri fileUri;
    ParseObject object;
    String user;
    String otherUser;


    public CustomAdapter(Context context, List<ParseObject> friend) {
        super(context, R.layout.custom_row,friend);
        mContext = context;
        mFriend = friend;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        object = mFriend.get(position);
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.custom_row,null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView
                    .findViewById(R.id.profile_image);
            holder.textView = (TextView) convertView
                    .findViewById(R.id.username);
            holder.followbtn = (Button) convertView.findViewById(R.id.follow);
            holder.followbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    otherUser=holder.textView.getText().toString();
                    user = ParseUser.getCurrentUser().getUsername().toString();

                    Toast.makeText(getContext(),"followed",LENGTH_LONG).show();
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Friend");
                    query.whereEqualTo("friend_name", otherUser);
                    query.whereEqualTo("username", user);
                    query.findInBackground(new FindCallback<ParseObject>() {

                        @Override
                        public void done(List<ParseObject> list, ParseException e) {
                            if (list.isEmpty()) {
                                ParseObject follow = new ParseObject("Friend");
                                follow.put("username",user);
                                follow.put("friend_name", otherUser);
                                follow.put("following",true);
                                follow.saveInBackground();
                                }
                            }
                        });
                    holder.followbtn.setBackgroundColor(Color.parseColor("#BE2619"));
                    holder.followbtn.setText("following");
                }
            });

            convertView.setTag(holder);
        }else {

            holder = (ViewHolder) convertView.getTag();
        }



        String username = object.getString("username");
        holder.textView.setText(username);
        //follow
       /* int follows = object.getInt("follow");
        holder.follow.setText("" + follows + "follow");
        //following
        int following = object.getInt("follow");
        holder.following.setText(""+following+"follow");*/
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

    public static class ViewHolder{
        ImageView imageView;
        TextView textView;
        Button followbtn;
        Button following;
    }
}
