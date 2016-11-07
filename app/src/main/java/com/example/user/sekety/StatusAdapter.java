package com.example.user.sekety;

import android.content.Context;
import android.content.Intent;
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
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by profile2 on 3/27/2016.
 */
public class StatusAdapter extends ArrayAdapter<ParseObject> {

    protected Context mcontext;
    protected List<ParseObject> mstatus;
    public Uri fileUri;
    ParseObject statusObject;
    boolean flag = true;
    ParseObject obj;
    String Id;
    public StatusAdapter(Context context, List<ParseObject> status) {
        super(context, R.layout.home_status, status);
        mcontext = context;
        mstatus = status;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        statusObject=mstatus.get(position);
      //   Id = statusObject.getObjectId();

        if (convertView == null) {
            convertView = LayoutInflater.from(mcontext).inflate(
                    R.layout.home_status, null);
            holder = new ViewHolder();
            holder.usernameHomePage = (TextView) convertView.findViewById(R.id.user_name);
            holder.userPhoto = (ImageView) convertView.findViewById(R.id.user_photo);
            holder.statusHomePage = (TextView) convertView.findViewById(R.id.post);
            holder.dateHome = (TextView) convertView.findViewById(R.id.date);
            holder.homeImage = (ImageView) convertView.findViewById(R.id.photo);
            holder.check_in=(TextView)convertView.findViewById(R.id.check_in_status);
            holder.like=(ImageView)convertView.findViewById(R.id.imageView1);
            holder.like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* obj = new ParseObject("post");
                    id = obj.getObjectId();*/
                    switch(v.getId()){

                        case R.id.imageView1:{
                            if(flag)
                            {  ParseQuery<ParseObject> query = ParseQuery.getQuery("post");
                               query.getInBackground(Id, new GetCallback<ParseObject>() {
                                    public void done(ParseObject myobject, ParseException e) {
                                        if (e == null) {

                                            myobject.increment("like");
                                            myobject.saveInBackground();
                                        }
                                    }
                                });

                                holder.like.setImageResource(R.drawable.like);
                                flag=false;
                            }
                            else
                            {
                                ParseQuery<ParseObject> query = ParseQuery.getQuery("post");
                                query.getInBackground(Id, new GetCallback<ParseObject>() {
                                    public void done(ParseObject myobject, ParseException e) {
                                        if (e == null) {

                                            myobject.increment("like",-1);
                                            myobject.saveInBackground();
                                        }
                                    }
                                });
                                holder.like.setImageResource(R.drawable.unlike);
                                flag=true;
                            }
                            return;
                        }
                    }
                }
            });

            holder.add_comment = (ImageView) convertView.findViewById(R.id.commentbtn);
            holder.add_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mcontext, custom_dialog.class);
                    intent.putExtra("postId", statusObject.getObjectId());
                    //   intent.putExtra("name", statusObject.getString("username"));
                    mcontext.startActivity(intent);
                }
            } );
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        statusObject=mstatus.get(position);
        Id = statusObject.getObjectId();


        String userName = statusObject.getString("username");
        holder.usernameHomePage.setText(userName);


        String placeName=statusObject.getString("checkin_address");
        holder.check_in.setText(placeName);

        String status = statusObject.getString("status_text");
        holder.statusHomePage.setText(status);
        String time = statusObject.getCreatedAt().toString();
        holder.dateHome.setText(time);

        if(statusObject.getParseFile("photo_name") != null){
        String file = statusObject.getParseFile("photo_name").getUrl();

          Picasso.with(getContext().getApplicationContext()).load(file).noFade().into(holder.homeImage);
        }
        else {holder.homeImage.setEnabled(false);}

       /* ParseQuery<ParseObject> query = ParseQuery.getQuery("post");
        query.whereEqualTo("username", userName);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    if (list.size() > 0) {

                        for (ParseObject object : list) {

                            ParseFile file;
                            file = object.getParseFile("photo_name");
                            if (file.is) {
                                holder.homeImage.setEnabled(false);
                            } else {

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
            }
        });*/

        ParseQuery<ParseUser> query1 = ParseUser.getQuery();
        query1.whereEqualTo("username", userName);
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
                        Picasso.with(getContext().getApplicationContext()).load(fileUri.toString()).into(holder.userPhoto, new com.squareup.picasso.Callback() {
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

    public void onClick(View view) {

    }
}

class ViewHolder {
    TextView usernameHomePage;
    TextView statusHomePage;
    TextView check_in;
    ImageView userPhoto;
    TextView dateHome;
    ImageView like;
    ImageView add_comment;
    ImageView homeImage;

}
