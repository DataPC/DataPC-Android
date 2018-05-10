package sk.datapc.datapc_android;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    View view1;
    int request = 1;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view1 = view;
                startActivityForResult(new Intent(MainActivity.this, BarcodeScan.class), request);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // ina aktivita vratila vysledok
        if (requestCode == request) {
            if (resultCode == RESULT_OK) {
                String PcID = data.getStringExtra("PcID");
                String server = Configuration.getConfigValue(this, "server_url");
                String fromServer = getHTML(server + "getPCInfo?id="+PcID);


                //  TextView textView = findViewById(R.id.textView);
                //  textView.setText(fromServer);
                Snackbar.make(getWindow().getDecorView(), fromServer, Snackbar.LENGTH_LONG).show();

                //Intent i = new Intent(this, PCDetailActivity.class);
                //i.putExtra("json", fromServer);
                //startActivity(i);
            }
        }
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
        if (id == R.id.add_pc) {
            Snackbar.make(getWindow().getDecorView(), "vypis textu nastal", Snackbar.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String getHTML(String urlToRead) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        StringBuilder result = new StringBuilder();
        URL url = null;
        try {
            url = new URL(urlToRead);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            rd.close();
            return result.toString();
        } catch (MalformedURLException e) {
            //TODO Zalogovat vynimku
            e.printStackTrace();
            //Snackbar.make(getWindow().getDecorView(), "malformed", Snackbar.LENGTH_LONG).show();
        } catch (ProtocolException e) {
            //TODO Zalogovat vynimku
            e.printStackTrace();
            //Snackbar.make(getWindow().getDecorView(), "protocol", Snackbar.LENGTH_LONG).show();
        } catch (IOException e) {
            //TODO Zalogovat vynimku
            e.printStackTrace();
            //Snackbar.make(getWindow().getDecorView(), "io", Snackbar.LENGTH_LONG).show();
        }

        return null;
    }
}
