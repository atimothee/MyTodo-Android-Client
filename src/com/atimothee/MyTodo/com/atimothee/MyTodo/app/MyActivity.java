package com.atimothee.MyTodo.com.atimothee.MyTodo.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.atimothee.MyTodo.R;
import com.atimothee.MyTodo.com.atimothee.MyTodo.adapter.ListAdapter;
import com.atimothee.MyTodo.com.atimothee.MyTodo.helper.TaskDBHandler;
import com.atimothee.MyTodo.com.atimothee.MyTodo.models.Task;
import com.google.gson.*;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONArray;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyActivity extends SherlockActivity {
    TaskDBHandler dbHandler = new TaskDBHandler(MyActivity.this);
    AsyncHttpClient httpclient = new AsyncHttpClient();
    public static String baseUrl = "http://todo-list-c.herokuapp.com/api/";
    ProgressBar progressBar;
    ListView listView;
    public static List<Task> tasks = new ArrayList<Task>();
    Boolean isOnline;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        listView = (ListView) findViewById(R.id.listV);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        fetchAllTasks();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Intent dialogintent = new Intent("com.atimothee.MyTodo.addtaskdialog");
        menu.add("New Note")
                .setIcon(R.drawable.ic_compose_inverse)
                .setIntent(dialogintent)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean hasActiveInternetConnection() {
        try {
            HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
            urlc.setRequestProperty("User-Agent", "Test");
            urlc.setRequestProperty("Connection", "close");
            urlc.setConnectTimeout(4000);
            urlc.setReadTimeout(4000);
            urlc.connect();
            return (urlc.getResponseCode() == 200);
        } catch (IOException e) {
            Log.i("warning", "Error checking internet connection", e);
            return false;
        }
    }

    public void fetchAllTasks() {


        httpclient.get(baseUrl + "tasks", null, new JsonHttpResponseHandler() {
            public void onSuccess(int arg0, JSONArray response) {

                String jsonString = response.toString();
                Log.d("download", "All tasks result:" + jsonString);

                if (jsonString != null) {
                    JsonElement json = null;
                    try {
                        json = new JsonParser().parse(jsonString);
                    } catch (JsonSyntaxException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        json = null;
                    } finally {
                        if (json != null) {
                            JsonArray array = json.getAsJsonArray();

                            Iterator iterator = array.iterator();
                            tasks.clear();
                            while (iterator.hasNext()) {
                                JsonElement json2 = (JsonElement) iterator.next();
                                Gson gson = new Gson();
                                Task task = gson.fromJson(json2,
                                        Task.class);

                                dbHandler.addTask(task);
                                Log.e("saveToDb",
                                        "--------------------------------created  Task"
                                                + task.getLabel());
                                Log.e("saveToDb",
                                        "The id of this task is " + task.getId());

                            }
                        }

                    }
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                tasks = dbHandler.retrieveAllTasks();
                ListAdapter adapter = new ListAdapter(getApplicationContext(), tasks);
                listView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Throwable arg0, JSONArray arg1) {
                progressBar.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        //finish();
    }
}
