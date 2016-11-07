package com.example.user.sekety;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

public class MyListViewAdapter extends BaseAdapter {
    private Context myContext;
    private List<ParseObject> myNotificationsList;

    public MyListViewAdapter(Context c, List<ParseObject> notificationsList) {
        //super();
        myContext = c;
        myNotificationsList = notificationsList;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return myNotificationsList.size();
    }

    @Override
    public Object getItem(int pos) {
        // TODO Auto-generated method stub
        return myNotificationsList.get(pos);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater myInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View myRow = myInflater.inflate(R.layout.list_view_item, null);

        ParseObject notification = myNotificationsList.get(position);

        TextView tvTime = (TextView) myRow.findViewById(R.id.tvTime);
        tvTime.setText(notification.getString("time"));

        TextView tvID = (TextView) myRow.findViewById(R.id.tvID);
        tvID.setText(notification.getString("id"));

        TextView tvLike = (TextView) myRow.findViewById(R.id.tvLike);
        tvLike.setText(notification.getString("like"));

        TextView tvComment = (TextView) myRow.findViewById(R.id.tvComment);
        tvComment.setText(notification.getString("comment"));

        TextView tvShare = (TextView) myRow.findViewById(R.id.tvShare);
        tvShare.setText(notification.getString("share"));

        return myRow;
    }


}
