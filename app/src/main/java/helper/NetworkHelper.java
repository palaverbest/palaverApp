package helper;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by stefa on 19.02.2016.
 */

public class NetworkHelper extends AsyncTask<String, Integer, ApiResult> {

    public enum ApiCommand {
        USER_VALIDATE,
        USER_REGISTER,
        USER_PUSHTOKEN,
        FRIENDS_ADD,
        FRIENDS_REMOVE,
        FRIENDS_GET,
        MESSAGE_GET,
        MESSAGE_GETOFFSET,
        MESSAGE_SEND
    }

    private OnDownloadFinished listener;

    public NetworkHelper(OnDownloadFinished listener){
        this.listener=listener;
    }

    public URL apiUrl(ApiCommand command) {
        try {
            String url = "http://palaver.se.paluno.uni-due.de/api/";
            switch (command) {
                case USER_REGISTER:
                    return new URL(url + "user/register");
                case USER_VALIDATE:
                    return new URL(url + "user/validate");
                case USER_PUSHTOKEN:
                    return new URL(url + "user/pushtoken");
                case FRIENDS_ADD:
                    return new URL(url + "friends/add");
                case FRIENDS_REMOVE:
                    return new URL(url + "friends/remove");
                case FRIENDS_GET:
                    return new URL(url + "friends/get");
                case MESSAGE_GET:
                    return new URL(url + "message/get");
                case MESSAGE_GETOFFSET:
                    return new URL(url + "message/getoffset");
                case MESSAGE_SEND:
                    return new URL(url + "message/send");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected ApiResult doInBackground(String... command) {
        try {
            ApiCommand apiCommand = ApiCommand.valueOf(command[0]);
            URL url = apiUrl(apiCommand);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(command[1].toString());
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String text = "";
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) sb.append(line + "\n");
            text = sb.toString();
            reader.close();
            return new ApiResult(new JSONObject(text), apiCommand);
        }
        catch(Exception e) {e.printStackTrace();}
        return null;
    }

    protected void onPostExecute(ApiResult result) {
        if(listener!=null)listener.onDownloadFinished(result);
    }
}