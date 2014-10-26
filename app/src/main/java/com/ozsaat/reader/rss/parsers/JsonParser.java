package com.ozsaat.reader.rss.parsers;


import com.ozsaat.reader.rss.RssItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class JsonParser implements Parser {
    public static List<RssItem> getListFromJsonObject(JSONObject jsonObject) throws JSONException {
        List<RssItem> returnList = new ArrayList<>();
        Iterator<String> keys = jsonObject.keys();

        List<String> keysList = new ArrayList<>();
        while (keys.hasNext()) {
            keysList.add(keys.next());
        }
        Collections.sort(keysList);

        for (String key : keysList) {
            List<String> rssList = new ArrayList<>();
            rssList.add(key);
            rssList.add(convertjsonItem(jsonObject.get(key)));
            returnList.add((RssItem) rssList);

        }
        return returnList;
    }

    @Override
    public String parse(InputStream inputStream) throws Exception {
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder stringBuilder = new StringBuilder();

        String inputStr;
        while ((inputStr = streamReader.readLine()) != null) {
            stringBuilder.append(inputStr);

            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
        }

        return stringBuilder.toString();


        // TODO make a list of RssItem from jsonResponse
    }


}

// parse inputstream to jsonobject
// iterate through jsonobject and put them in RssItem
// put RssItem to List