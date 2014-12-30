package com.ozsaat.reader.ui.reader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ozsaat.reader.R;
import com.ozsaat.reader.api.NewsItemsIntentService;
import com.ozsaat.reader.rss.RssItem;
import com.ozsaat.reader.ui.BaseActivity;
import com.ozsaat.reader.utils.EventBus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;


public class NewsListActivity extends BaseActivity implements AdapterView.OnItemClickListener {


    public static final String TAG = NewsListActivity.class.getSimpleName();
    private ListView listView;
    private NewsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(this);
        EventBus.get().register(this);

        if (isNetworkAvailable()) {
            NewsItemsIntentService.start(this);
        } else {
            Toast.makeText(this, "Network is unavailable!", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.get().unregister(this);
    }

    @Subscribe
    public void onRssItemsDownloaded(ArrayList<RssItem> rssItems) {
        updateList(rssItems);
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



}
