package com.example.sang.baitaplon;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sang on 29/05/2017.
 */

public class GetData extends AsyncTask<Void, Void, JSONObject> {
    private String link;
    private Context context;
    private ProgressDialog pDialog;
    private CallbackInterface callback;

    public GetData() {
    }

    public GetData(String link, Context context, CallbackInterface callback) {
        this.link = link;
        this.context = context;
        this.callback = callback;
    }

    public void initProgress() {
        pDialog = new ProgressDialog(context);
        pDialog.setTitle("Vui lòng chờ trong ít phút...");
        pDialog.setIndeterminate(true);
        pDialog.show();
    }

    private void checkInternetConnection() {
        ConnectivityManager check = (ConnectivityManager) this.context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = check.getActiveNetworkInfo();
        if (ni != null) {
            NetworkInfo[] info = check.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        Log.d("Status","Connected");
                        break;
                    }

        } else {
            pDialog.dismiss();
            Toast.makeText(context, "Not connected to the internet", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPreExecute() {
        initProgress();
        checkInternetConnection();
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        String webPage = "";
        HttpURLConnection conn = null;
        JSONObject jsonObj = new JSONObject();
        try {
            URL url = new URL(link);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(15000);
            conn.setDoInput(true);
            conn.connect();
            int connect_status = conn.getResponseCode();
            if (connect_status == 200) {
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is, "UTF-8"));
                String data = null;
                while ((data = reader.readLine()) != null) {
                    webPage += data + "\n";
                }
                Log.d("Data", webPage);
                jsonObj = new JSONObject(webPage);
            } else {
                return null;
            }

        } catch (Exception e) {
            return null;
        } finally {
            conn.disconnect();
        }
        return jsonObj;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        if (pDialog.isShowing())
            pDialog.dismiss();
        callback.onGetDataFinished(result);
    }
}
