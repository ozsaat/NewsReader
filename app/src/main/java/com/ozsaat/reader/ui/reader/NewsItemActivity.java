package com.ozsaat.reader.ui.reader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ozsaat.reader.R;
import com.ozsaat.reader.rss.RssItem;
import com.squareup.picasso.Picasso;

public class NewsItemActivity extends Activity {
    public static final String EXTRA_RSS_ITEM = "EXTRA_RSS_ITEM";

    public static void start(Context context, RssItem rssItem) {
        Intent intent = new Intent(context, NewsItemActivity.class);
        intent.putExtra(EXTRA_RSS_ITEM, rssItem);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_item);

        RssItem rssItem = getIntent().getParcelableExtra(EXTRA_RSS_ITEM);

        ImageView photoImageView = (ImageView) findViewById(R.id.photoImageView);
        TextView titleTextView = (TextView) findViewById(R.id.titleTextView);
        TextView authorTextView = (TextView) findViewById(R.id.authorTextView);
        TextView dateTextView = (TextView) findViewById(R.id.dateTextView);

        titleTextView.setText(rssItem.getTitle());
        dateTextView.setText(rssItem.getDate());
        authorTextView.setText(rssItem.getAuthor());
        Picasso.with(this).load(rssItem.getThumbnail()).fit().centerCrop().into(photoImageView);

    }


}
