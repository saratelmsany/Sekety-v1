package com.example.user.sekety;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by user on 4/20/2016.
 */
public class CommentAdapter extends ArrayAdapter<ParseObject> {
    protected Context mcontext;
    protected List<ParseObject>mcomments;
    public Uri fileUri;

    public CommentAdapter(Context context, List<ParseObject> comments) {
        super(context,R.layout.comment,comments);

        mcontext=context;
        mcomments=comments;
    }
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewCommentHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.comment, null);
            holder = new ViewCommentHolder();
            holder.usernameHomePage=(TextView)convertView.findViewById(R.id.user_name);

            holder.userPhoto=(ImageView)convertView.findViewById(R.id.user_photo);

            holder.comment=(TextView)convertView.findViewById(R.id.comment);

            convertView.setTag(holder);

        }else {
            holder = (ViewCommentHolder) convertView.getTag();
        }
        ParseObject commentObject=mcomments.get(position);
        ParseUser currentUser=ParseUser.getCurrentUser();

      // String currrentUserName=currentUser.getUsername();
       // holder.usernameHomePage.setText(currrentUserName);

        String userName=commentObject.getString("user_name");
        holder.usernameHomePage.setText(userName);


        String comment = commentObject.getString("comment");
        holder.comment.setText(comment);

        /////

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
}

class ViewCommentHolder {
    TextView usernameHomePage;
    ImageView userPhoto;
    TextView comment;
}


