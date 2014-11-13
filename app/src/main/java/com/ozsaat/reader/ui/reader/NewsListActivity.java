package com.ozsaat.reader.ui.reader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ozsaat.reader.R;
import com.ozsaat.reader.rss.RssItem;
import com.ozsaat.reader.rss.parsers.JsonParser;
import com.ozsaat.reader.rss.parsers.Parser;
import com.ozsaat.reader.ui.BaseActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

        if (isNetworkAvailable()) {
            GetBlogPostsTask getBlogPostsTask = new GetBlogPostsTask();
            getBlogPostsTask.execute();
        } else {
            Toast.makeText(this, "Network is unavailable!", Toast.LENGTH_LONG).show();
        }

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

    private class GetBlogPostsTask extends AsyncTask<Object, Void, List<RssItem>> {
        public static final int NUMBER_OF_POSTS = 20;

        @Override
        protected List<RssItem> doInBackground(Object[] objects) {

            List<RssItem> rssItemList = null;

            try {

                URL blogFeedUrl = new URL("http://blog.teamtreehouse.com/api/get_recent_summary/?count=" + NUMBER_OF_POSTS);
                HttpURLConnection connection = (HttpURLConnection) blogFeedUrl.openConnection();
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    Parser parser = new JsonParser();
                    rssItemList = parser.parse(inputStream);

                } else {
                    Log.i(TAG, "Unsuccessful HTTP Response Code: " + responseCode);
                }
            } catch (Exception e) {
                logException(e);
            }
            return rssItemList;

        }

        @Override
        protected void onPostExecute(List<RssItem> result) {
            updateList(result);

        }
    }


}
