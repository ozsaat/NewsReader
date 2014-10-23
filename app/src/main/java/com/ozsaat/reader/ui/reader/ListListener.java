package com.ozsaat.reader.ui.reader;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;

import com.ozsaat.reader.rss.RssItem;
import com.ozsaat.reader.ui.web.WebActivity;

import java.util.List;


public class ListListener implements AdapterView.OnItemClickListener {

    List<RssItem> listItems;
    Activity activity;

    public ListListener(List<RssItem> listItems, Activity activity) {
        this.listItems = listItems;
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        Intent intent = new Intent(activity, WebActivity.class);
        intent.setData(Uri.parse(listItems.get(pos).getLink()));

        activity.startActivity(intent);

    }
}
