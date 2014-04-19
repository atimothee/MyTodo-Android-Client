package com.atimothee.MyTodo.com.atimothee.MyTodo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.atimothee.MyTodo.R;
import com.atimothee.MyTodo.com.atimothee.MyTodo.models.Task;

import java.util.List;


public class ListAdapter extends BaseAdapter{

    private Context mcontext;
    private List<Task> tasks;

    public ListAdapter(Context mcontext, List<Task> tasks){
        this.mcontext = mcontext;
        this.tasks = tasks;
    }
    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return tasks.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder viewHolder;
        if (convertView==null){
        convertView = View.inflate(mcontext, R.layout.list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView)convertView.findViewById(R.id.title);
            viewHolder.description = (TextView)convertView.findViewById(R.id.description);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.description.setText(tasks.get(position).description);
        viewHolder.title.setText(tasks.get(position).name);
        return convertView;
    }

    static class ViewHolder{
            TextView title;
        TextView description;
    }
}
