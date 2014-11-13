package com.ozsaat.reader.ui.reader;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.ozsaat.reader.R;
import com.ozsaat.reader.rss.RssItem;
import com.ozsaat.reader.rss.RssReader;

import java.util.List;

public class ReaderAppActivity extends Activity {

    private ReaderAppActivity local;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        local = this;

        GetRSSDataTask task = new GetRSSDataTask();
        task.execute("http://feeds.feedburner.com/androidpolice?format=xml");
        Log.d("RssReader", Thread.currentThread().getName());

        final Button switchact = (Button) findViewById(R.id.button_1);
        switchact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent act2 = new Intent(view.getContext(), NewsListActivity.class);
                startActivity(act2);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    private class GetRSSDataTask extends AsyncTask<String, Void, List<RssItem>> {
        @Override
        protected List<RssItem> doInBackground(String... urls) {
            Log.d("RssReader", Thread.currentThread().getName());
            try {
                RssReader rssReader = new RssReader(urls[0]);
                return rssReader.getItems();
            } catch (Exception e) {
                Log.e("RssReader", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<RssItem> result) {

            ListView rssReaderItems = (ListView) findViewById(R.id.listView);
            ArrayAdapter<RssItem> adapter = new ArrayAdapter<>(local, android.R.layout.simple_list_item_1, result);
            rssReaderItems.setAdapter(adapter);
            rssReaderItems.setOnItemClickListener(new ListListener(result, local));
        }
    }

}
