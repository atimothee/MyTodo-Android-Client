package com.atimothee.MyTodo.com.atimothee.MyTodo.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockActivity;
import com.atimothee.MyTodo.R;
import com.atimothee.MyTodo.com.atimothee.MyTodo.helper.TaskDBHandler;
import com.atimothee.MyTodo.com.atimothee.MyTodo.models.Task;

public class AddTask extends SherlockActivity {
    TaskDBHandler dbHandler = new TaskDBHandler(AddTask.this);

    final Context mcontext = this;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);
        Button savetask = (Button)findViewById(R.id.savetask);
        Button cancel = (Button)findViewById(R.id.cancelsavetask);

        savetask.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                EditText title = (EditText)findViewById(R.id.edittasktitle);
                EditText description = (EditText)findViewById(R.id.edittaskdescription);
                Task task = new Task();
                task.setLabel(title.getText().toString());
                task.setDescription(description.getText().toString());
                dbHandler.addTask(task);
                finish();
                //Intent i = new Intent(AddTask.this, MyActivity.class);
                //startActivity(i);
                Toast.makeText(mcontext, "Your task has been saved", Toast.LENGTH_SHORT).show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                Intent i = new Intent(AddTask.this, MyActivity.class);
//                startActivity(i);
                finish();
            }
        });
    }
}

