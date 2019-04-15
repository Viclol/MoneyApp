package com.example.moneyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

public class ListViewAdapter extends BaseAdapter {

    private LinkedList<RecordBean> records = new LinkedList<>();//初始化数据
    private LayoutInflater mInflater;//初始化界面
    private Context mContext;//上下文
    //构造方法
    public ListViewAdapter(Context context){
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setData(LinkedList<RecordBean> records){
        this.records = records;
        notifyDataSetChanged();//有数据改变需要重新加载
    }

    @Override
    public int getCount() {
        return records.size();
    }

    @Override
    public Object getItem(int i) {
        return records.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;

        if (view == null){
            view = mInflater.inflate(R.layout.cell_list_view,null);

            RecordBean recordBean = (RecordBean) getItem(i);
            holder = new ViewHolder(view,recordBean);

            view.setTag(holder);

        }else {
            holder = (ViewHolder) view.getTag();
        }

        return view;
    }
}

class ViewHolder{

    ImageView categoryIcon;
    TextView remarkTV;
    TextView timeTV;
    TextView amountTV;

    public ViewHolder(View itemView, RecordBean record){
        categoryIcon = itemView.findViewById(R.id.imageView_category);
        remarkTV = itemView.findViewById(R.id.textView_remark);
        timeTV = itemView.findViewById(R.id.textView_time);
        amountTV = itemView.findViewById(R.id.textView_amount);

        remarkTV.setText(record.getRemark());
        if (record.getType() == 1){
            amountTV.setText("- "+record.getAmount());
        }else {
            amountTV.setText("+ "+record.getAmount());
        }
        timeTV.setText(DateUtil.getFormattedTime(record.getTimetamp()));
        categoryIcon.setImageResource(GlobalUtil.getInstance().getResourceIcon(record.getCategory()));
    }
}