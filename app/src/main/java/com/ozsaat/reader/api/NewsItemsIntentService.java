package com.ozsaat.reader.api;


import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ozsaat.reader.rss.RssItem;
import com.ozsaat.reader.rss.parsers.JsonParser;
import com.ozsaat.reader.rss.parsers.Parser;
import com.ozsaat.reader.utils.EventBus;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsItemsIntentService extends IntentService {
    public static final String TAG = NewsItemsIntentService.class.getSimpleName();

    public static final String RSS_EXTRA = "rss list";
    public static final String ACTION = NewsItemsIntentService.class.getName() + ".ACTION";

    public NewsItemsIntentService() {
        super(NewsItemsIntentService.class.getSimpleName());
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, NewsItemsIntentService.class);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        List<RssItem> rssItemList = null;

        try {

            URL blogFeedUrl = new URL("http://blog.teamtreehouse.com/api/get_recent_summary/?count=" + 20);
            HttpURLConnection connection = (HttpURLConnection) blogFeedUrl.openConnection();
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                Parser parser = new JsonParser();
                rssItemList = parser.parse(inputStream);
                sendResult(rssItemList);

            } else {
                Log.i(TAG, "Unsuccessful HTTP Response Code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void sendResult(List<RssItem> rssItems) {


        EventBus.get().post(new ArrayList<>(rssItems));
    }

}
