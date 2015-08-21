package com.example.hzzhanyawei.learnandroid;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hzzhanyawei on 2015/8/19.
 * Email hzzhanyawei@corp.netease.com
 */
public class MsgAdapter extends BaseAdapter {
    private Context mContext;
    private List<Msg> mMsg;

    public MsgAdapter(Context context){
        this.mContext = context;
    }
    public MsgAdapter(Context context, List<Msg> msg){
        mContext = context;
        mMsg = msg;
    }

    public void setMsg(List<Msg> msg) {
        this.mMsg = msg;
    }

    public List<Msg> getMsg() {
        return mMsg;
    }

    @Override
    public int getCount() {
        if(mMsg == null || mMsg.equals("")){
            return 0;
        }else {
            return mMsg.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return mMsg.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = parent.inflate(mContext, R.layout.item_msg, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.setData(position);
        return convertView;
    }

    private class ViewHolder{
        LinearLayout ll_left;
        LinearLayout ll_right;
        TextView tv_left_msg;
        TextView tv_right_msg;

        public ViewHolder(View root){
            ll_left = (LinearLayout) root.findViewById(R.id.left_layout);
            ll_right = (LinearLayout) root.findViewById(R.id.right_layout);
            tv_left_msg = (TextView) root.findViewById(R.id.left_msg);
            tv_right_msg = (TextView) root.findViewById(R.id.right_msg);
        }

        public void setData(final int postion){
            Msg msg = mMsg.get(postion);
            if (Msg.TYPE_REVD == msg.getType()){
                ll_right.setVisibility(View.GONE);
                ll_left.setVisibility(View.VISIBLE);
                tv_left_msg.setText(msg.getMsg());
            }else {
                ll_left.setVisibility(View.GONE);
                ll_right.setVisibility(View.VISIBLE);
                tv_right_msg.setText(msg.getMsg());
            }

        }

    }
}
