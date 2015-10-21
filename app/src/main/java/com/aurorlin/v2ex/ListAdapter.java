package com.aurorlin.v2ex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by aurorlin on 2015/10/20.
 */
public class ListAdapter extends BaseAdapter {
    private Context mContext;
    private RequestQueue mRequestQueue;
    private JSONArray mArrayList;
    private ImageLoader mImageLoader;
    private LayoutInflater mLayoutInflater;

    public ListAdapter(Context context, RequestQueue requestQueue, JSONArray arrayList) {
        mContext = context;
        mRequestQueue = requestQueue;
        mArrayList = arrayList;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LruImageCache lruImageCache = LruImageCache.instance();
        mImageLoader = new ImageLoader(mRequestQueue, lruImageCache);
    }

    @Override
    public int getCount() {
        return mArrayList.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return mArrayList.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.post_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.node_title = (TextView) convertView.findViewById(R.id.node_title);
            viewHolder.username = (TextView) convertView.findViewById(R.id.username);
            viewHolder.last_modified = (TextView) convertView.findViewById(R.id.last_modified);
            viewHolder.avatar = (NetworkImageView) convertView.findViewById(R.id.avatar);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) mArrayList.get(position);
            JSONObject member = jsonObject.getJSONObject("member");
            JSONObject node = jsonObject.getJSONObject("node");
            viewHolder.title.setText(jsonObject.getString("title"));
            viewHolder.node_title.setText(node.getString("title"));
            viewHolder.username.setText(member.getString("username"));
            viewHolder.last_modified.setText("最后回复:" + format_data(jsonObject.getString("last_modified")));
            viewHolder.avatar.setImageUrl("http:" + member.getString("avatar_large"), mImageLoader);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    public String format_data(String time) {
        Long times = new Long(time + "000");
        Date date = new Date(times);
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
        String str = format.format(date);
        return str;
    }

    class ViewHolder {
        TextView title;
        TextView node_title;
        TextView username;
        TextView last_modified;
        NetworkImageView avatar;
    }
}
