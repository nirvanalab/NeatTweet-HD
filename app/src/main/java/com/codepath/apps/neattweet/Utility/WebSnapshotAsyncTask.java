package com.codepath.apps.neattweet.Utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by vidhurvoora on 8/6/16.
 */
public class WebSnapshotAsyncTask extends AsyncTask<String, Void, Bitmap>  {

    public interface OnTaskCompleted{
        void onTaskCompleted(Bitmap snapshot);
    }

    private OnTaskCompleted listener;

    public WebSnapshotAsyncTask(OnTaskCompleted listener){
        this.listener=listener;
    }

    protected void onPreExecute() {
        // Runs on the UI thread before doInBackground
        // Good for toggling visibility of a progress indicator

    }

    protected Bitmap doInBackground(String... strings) {
        // Some long-running task like downloading an image.
        try {
//            InputStream is = (InputStream) new URL(strings[0]).getContent();
//            Drawable d = Drawable.createFromStream(is, "src name");
//            return d;
            String url = strings[0];
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(url).getContent());
            return bitmap;
        } catch (Exception e) {
            Log.d("Exception:",e.toString());
            return null;
        }

    }

    protected void onProgressUpdate() {
        // Executes whenever publishProgress is called from doInBackground
        // Used to update the progress indicator

    }

    protected void onPostExecute(Bitmap result) {
        // This method is executed in the UIThread
        // with access to the result of the long running task
        listener.onTaskCompleted(result);

    }
}
