package com.magic.wiraazharan.openweathermaprest;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.spec.DESKeySpec;

/**
 * Created by wiraazharan on 7/18/15.
 */
public class APITask extends AsyncTask<String,Integer,String>
{

    private ProgressDialog progDialog;
    private Context context;
    private MainActivity activity;
    private static final String debugTag = "OpenWeatherApiTask";


    public APITask(MainActivity activity) {
        this.activity = activity;
        this.context = this.activity.getApplicationContext();
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progDialog = ProgressDialog.show(this.activity, "Retrieving", this.context.getResources().getString(R.string.looking_for_tracks) , true, false);
    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            Log.d(debugTag, "Background:" + Thread.currentThread().getName());
            String result = Helper.downloadFromServer(strings);
            return result;
        } catch (Exception e) {
            return new String();
        }


    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        //WeatherData wd = new WeatherData();

        progDialog.dismiss();
        if (s.length() == 0) {
            this.activity.alert ("Unable to find weather for the location due to network error. Try again later.");
            return;
        }



        try {

            String description = null;


            JSONObject respObj = new JSONObject(s);
            String id = respObj.getString("id");
            String name = respObj.getString("name");
            //Log.e(debugTag,"id = "+id);

            JSONObject windobject = respObj.getJSONObject("wind");
            String speed = windobject.getString("speed");
            //Log.e(debugTag,"speed = "+speed);

            JSONObject mainobject = respObj.getJSONObject("main");
            String humidity = mainobject.getString("humidity");
            String pressure = mainobject.getString("pressure");
            String temperature = mainobject.getString("temp");

            JSONObject sysobject = respObj.getJSONObject("sys");
            String country = sysobject.getString("country");
            String sunrise = sysobject.getString("sunrise");
            String sunset = sysobject.getString("sunset");
            //Log.e(debugTag,"speed = "+speed);

            JSONArray weatherarray = respObj.getJSONArray("weather");
           for(int i=0; i<weatherarray.length(); i++) {
                 JSONObject weatherlist = weatherarray.getJSONObject(i);
                 description = weatherlist.getString("description");
           }

            activity.alert("id = "+id+"\n"+
                            "name = "+name+"\n"+
                            "speed = "+speed+"\n"+
                            "country = "+country+"\n"+
                            "sunrise = "+sunrise+"\n"+
                            "sunset = "+sunset+"\n"+
                            "humidity = "+humidity+"\n"+
                            "pressure = "+pressure+"\n"+
                            "temperature = "+temperature+"\n"+
                            "description = "+ description+"\n"
            );





//            JSONObject respObj = new JSONObject(s);
//            JSONObject topTracksObj = respObj.getJSONObject("toptracks");
//            JSONArray tracks = respObj.getJSONArray("wind");
//            for(int i=0; i<tracks.length(); i++) {
//                JSONObject track = tracks.getJSONObject(i);
//                String trackName = tracks.getString("name");
//                String trackUrl = track.getString("url");
//                JSONObject artistObj = track.getJSONObject("artist");
//                String artistName = artistObj.getString("name");
//                String artistUrl = artistObj.getString("url");


                //trackdata.add(new TrackData(trackName,artistName,imageUrl, artistUrl, trackUrl));
//            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //this.activity.setTracks(trackdata);

    }

}
