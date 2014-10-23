package com.ozsaat.reader.rss.parsers;


import com.ozsaat.reader.rss.RssItem;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

public class JsonParser implements Parser {
    @Override
    public List<RssItem> parse(InputStream inputStream) throws Exception {
        Reader reader = new InputStreamReader(inputStream);

        int nextCharacter;
        String responseData = "";
        while (true) {
            nextCharacter = reader.read();
            if (nextCharacter == -1)
                break;
            responseData += (char) nextCharacter;
        }

        jsonResponse = new JSONObject(responseData);

        // TODO make a list of RssItem from jsonResponse
    }
}
