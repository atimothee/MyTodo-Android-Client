package com.atimothee.MyTodo.com.atimothee.MyTodo.helper;
        import java.util.ArrayList;
        import java.util.List;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import com.atimothee.MyTodo.com.atimothee.MyTodo.models.Task;

public class TaskDBHandler extends SQLiteOpenHelper{

        public TaskDBHandler(Context context) {
            super(context, DB_Name, null, DBVersion);
        }

        String CREATE_TASK_TABLE = "CREATE TABLE "+TableName+"(" +KEY_ID+ " INTEGER PRIMARY KEY, " + KEY_LABEL + " VARCHAR(40), " +KEY_DESCRIPTION+" TEXT "+")";
        private static final String DB_Name="Tasks";
        private static final int DBVersion = 1;
        private static final String TableName = "Task";

        //Column names
        private static final String KEY_ID = "id";
        private static final String KEY_LABEL = "label";
        private static final String KEY_DESCRIPTION = "description";


        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TASK_TABLE);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+ TableName);
            onCreate(db);
        }

        public void addTask(Task task){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            try{
            values.put(KEY_ID, task.getId());
            }
            catch (Exception e){

            }
            values.put(KEY_LABEL, task.getLabel());
            values.put(KEY_DESCRIPTION, task.getDescription());
            db.insert(TableName, null, values);
            db.close();
        }

        public Task getTask(int id) {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(TableName, new String[] { KEY_ID,
                     KEY_LABEL, KEY_DESCRIPTION }, KEY_ID + "=?",
                    new String[] { String.valueOf(id) }, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();

            Task task = new Task(cursor.getLong(0),cursor.getString(1),
                    cursor.getString(2));

            return task;
        }

        public List<Task> retrieveAllTasks(){
            List<Task> retrievedTasks = new ArrayList<Task>();
            String selectallquery = "SELECT * FROM "+TableName;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectallquery, null);
            if(cursor.moveToFirst()){
                do{
                    Task task = new Task();
                    task.setId(cursor.getLong(0));
                    task.setLabel(cursor.getString(1));
                    task.setDescription(cursor.getString(2));
                    retrievedTasks.add(task);
                }
                while(cursor.moveToNext());

            }
            db.close();
            return retrievedTasks;

        }

        public int UpdateTasks(Task task){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_LABEL, task.getLabel());
            values.put(KEY_DESCRIPTION, task.getDescription());
            return db.update(TableName, values, KEY_ID + " = ?", new String[] {String.valueOf(task.getId())});


        }
        public void DeleteTask(int id){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            //db.del
            //db.delete(TableName, KEY_ID, Integer.toString(id));

            db.close();
        }







    }




