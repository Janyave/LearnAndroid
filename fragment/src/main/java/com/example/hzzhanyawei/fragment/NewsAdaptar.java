package com.example.hzzhanyawei.fragment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hzzhanyawei on 2015/8/20.
 * Email hzzhanyawei@corp.netease.com
 */
public class NewsAdaptar extends BaseAdapter {
    private Context context;
    private List<News> news;

    public NewsAdaptar(Context context){this.context = context;}

    public NewsAdaptar(Context context, List<News> news) {
        this.context = context;
        this.news = news;
    }

    @Override
    public int getCount() {
        if(news == null || news.equals(""))
            return 0;
        else
            return news.size();
    }

    @Override
    public Object getItem(int position) {
        return news.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = parent.inflate(context, R.layout.item_news, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.setData(position);
        return convertView;
    }

    class ViewHolder {
        TextView title;

        public ViewHolder(View root){
            title = (TextView) root.findViewById(R.id.news_title);
        }
        public void setData(final int position){
            News data = news.get(position);
            title.setText(data.getTitle());
        }
    }
}
