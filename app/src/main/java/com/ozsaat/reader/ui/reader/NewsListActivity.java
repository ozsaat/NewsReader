package com.ozsaat.reader.ui.reader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ozsaat.reader.R;
import com.ozsaat.reader.api.NewsItemsIntentService;
import com.ozsaat.reader.rss.RssItem;
import com.ozsaat.reader.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;


public class NewsListActivity extends BaseActivity implements AdapterView.OnItemClickListener {


    public static final String TAG = NewsListActivity.class.getSimpleName();
    private ListView listView;
    private NewsAdapter adapter;
    private BroadcastReceiver newsReciever = new NewsItemsBroadcast();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(this);

        registerReceiver(newsReciever, new IntentFilter(NewsItemsIntentService.ACTION));

        if (isNetworkAvailable()) {
            NewsItemsIntentService.start(this);
        } else {
            Toast.makeText(this, "Network is unavailable!", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(newsReciever);
    }

    private void updateList(List<RssItem> rssItems) {
        adapter = new NewsAdapter(this);
        adapter.setRssItems(rssItems);
        listView.setAdapter(adapter);
    }

    private void logException(Exception e) {
        Log.e(TAG, "Exception caught: ", e);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        NewsItemActivity.start(this, (RssItem) adapter.getItem(position));

    }


    private class NewsItemsBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Parcelable[] rssValues = intent.getParcelableArrayExtra(NewsItemsIntentService.RSS_EXTRA);
            List<RssItem> rssItems = new ArrayList<>();
            for (int i = 0; i < rssValues.length; i++) {
                rssItems.add((RssItem) rssValues[i]);

            }

            updateList(rssItems);

        }

    }

}
