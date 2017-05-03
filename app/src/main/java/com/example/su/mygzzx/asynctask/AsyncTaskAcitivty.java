package com.example.su.mygzzx.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.su.mygzzx.R;

public class AsyncTaskAcitivty extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task_acitivty);
    }

    class MyAsynctask extends AsyncTask<Void, Integer, Boolean>{
        @Override
        protected void onPreExecute() {

        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }



        @Override
        protected void onPostExecute(Boolean result) {

        }
    }
}
