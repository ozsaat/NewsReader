package com.ozsaat.reader.ui.reader;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.ozsaat.reader.R;
import com.ozsaat.reader.rss.RssItem;
import com.ozsaat.reader.rss.parsers.JsonParser;
import com.ozsaat.reader.rss.parsers.Parser;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class BlogReaderActivity extends ListActivity {

    public static final int NUMBER_OF_POSTS = 20;
    public static final String TAG = BlogReaderActivity.class.getSimpleName();
    public static final String TITLE_KEY = "title";
    public static final String AUTHOR_KEY = "author";
    public static final String DATE_KEY = "date";
    public static final String THUMBNAIL_KEY = "thumbnail";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_reader);

        if (isNetworkAvailable()) {
            GetBlogPostsTask getBlogPostsTask = new GetBlogPostsTask();
            getBlogPostsTask.execute();
        } else {
            Toast.makeText(this, "Network is unavailable!", Toast.LENGTH_LONG).show();
        }

        findViewById(R.id.button_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ParcelableActivity.class);
                intent.putExtra("title", TITLE_KEY);
                intent.putExtra("author", AUTHOR_KEY);
                intent.putExtra("date", DATE_KEY);
                intent.putExtra("thumbnail", THUMBNAIL_KEY);
                startActivity(intent);


            }
        });

    }

    private void updateList(List<RssItem> rssItems) {
        NewsAdapter newsAdapter = new NewsAdapter(this);
        newsAdapter.setRssItems(rssItems);
        setListAdapter(newsAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
//        super.onListItemClick(l, v, position, id);
//        try {
//            JSONArray jsonPosts = mBlogData.getJSONArray("posts");
//            JSONObject jsonPost = jsonPosts.getJSONObject(position);
//            String blogUrl = jsonPost.getString("url");
//            Intent intent = new Intent(this, WebActivity.class);
//            intent.setData(Uri.parse(blogUrl));
//            startActivity(intent);
//
//        } catch (JSONException e) {
//            logException(e);
//        }
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

    private class GetBlogPostsTask extends AsyncTask<Object, Void, List<RssItem>> {

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
