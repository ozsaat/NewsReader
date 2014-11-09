package com.ozsaat.reader.ui.reader;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ozsaat.reader.R;
import com.ozsaat.reader.rss.RssItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends BaseAdapter {
    private final List<RssItem> rssItems;
    private final Context context;

    public NewsAdapter(Context context) {
        this.context = context;
        rssItems = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return rssItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rssItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.li_news, parent, false);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final RssItem rssItem = rssItems.get(position);
        holder.titleTextView.setText(rssItem.getTitle());
        holder.dateTextView.setText(rssItem.getDate());
        holder.authorTextView.setText(rssItem.getAuthor());
        Picasso.with(context).load(rssItem.getThumbnail()).into(holder.photoImageView);

        return convertView;
    }

    public void setRssItems(List<RssItem> rssItems) {
        this.rssItems.clear();
        if (rssItems != null) {
            this.rssItems.addAll(rssItems);
        }
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        public final ImageView photoImageView;
        public final TextView titleTextView;
        public final TextView authorTextView;
        public final TextView dateTextView;

        private ViewHolder(View view) {
            photoImageView = (ImageView) view.findViewById(R.id.photoImageView);
            titleTextView = (TextView) view.findViewById(R.id.titleTextView);
            authorTextView = (TextView) view.findViewById(R.id.authorTextView);
            dateTextView = (TextView) view.findViewById(R.id.dateTextView);
            view.setTag(this);
        }
    }
}
