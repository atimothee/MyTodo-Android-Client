package com.atimothee.MyTodo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import com.atimothee.MyTodo.com.atimothee.MyTodo.adapter.ListAdapter;
import com.atimothee.MyTodo.com.atimothee.MyTodo.models.Task;

import java.util.ArrayList;
import java.util.List;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ListView lv = (ListView)findViewById(R.id.listV);
        List<Task> tasks = new ArrayList<Task>();
        tasks.add(new Task(Long.parseLong("1"), "eat lunch", "describe me"));
        ListAdapter adapter = new ListAdapter(getApplicationContext(),tasks);
        lv.setAdapter(adapter);

    }
}
