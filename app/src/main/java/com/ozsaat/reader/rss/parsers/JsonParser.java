package com.ozsaat.reader.rss.parsers;


import com.ozsaat.reader.rss.RssItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JsonParser implements Parser {

    @Override
    public List<RssItem> parse(InputStream inputStream) throws Exception {
        JSONObject jsonObject = parseInputStream(inputStream);

        return parseJsonObject(jsonObject);
    }

    private JSONObject parseInputStream(InputStream inputStream) throws IOException, JSONException {
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder stringBuilder = new StringBuilder();

        String inputStr;
        while ((inputStr = streamReader.readLine()) != null) {
            stringBuilder.append(inputStr);
        }
        return new JSONObject(stringBuilder.toString());
    }

    private List<RssItem> parseJsonObject(JSONObject jsonObject) throws JSONException {
        JSONArray posts = jsonObject.getJSONArray("posts");
        List<RssItem> rssList = new ArrayList<>();
        for (int i = 0; i < posts.length(); i++) {
            JSONObject post = posts.getJSONObject(i);
            RssItem rssItem = new RssItem();
            rssItem.setId(post.getLong("id"));
            rssItem.setTitle(post.getString("title"));
            rssItem.setAuthor(post.getString("author"));
            rssItem.setDate(post.getString("date"));
            rssItem.setLink(post.getString("link"));
            rssItem.setThumbnail(post.getString("thumbnail"));

            rssList.add(rssItem);
        }

        return rssList;
    }

}