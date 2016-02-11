package com.magic.wiraazharan.openweathermaprest;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    Button checkweather;
    TextView weather;
    TextView windspeed;
    TextView temperature;
    EditText requestedcity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkweather = (Button)findViewById(R.id.btn_checkweather);
        weather = (TextView)findViewById(R.id.tv_weather);
        windspeed = (TextView)findViewById(R.id.tv_windspeed);
        temperature = (TextView)findViewById(R.id.tv_temperature);
        requestedcity = (EditText)findViewById(R.id.ed_requestcity);

        checkweather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                APITask lfmTask = new APITask(MainActivity.this);
                try {

                    String consolidated = null;
                    String requested = requestedcity.getText().toString();
                    String[] splited = requested.split("\\s+");

                    StringBuilder builder = new StringBuilder();
                    for(String s : splited) {
                        builder.append(s+"+");
                    }
                    consolidated =  builder.toString();

                    lfmTask.execute(consolidated);
                }
                catch (Exception e)
                {
                    lfmTask.cancel(true);
                    alert (getResources().getString(R.string.no_data));
                }

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void alert(String s) {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }
}
