package com.ozsaat.reader.rss.parsers;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonParser implements Parser {
    @Override
    public String parse(InputStream inputStream) throws Exception {
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder stringBuilder = new StringBuilder();

        String inputStr;
        while ((inputStr = streamReader.readLine()) != null) {
            stringBuilder.append(inputStr);
        }

        return stringBuilder.toString();


        // TODO make a list of RssItem from jsonResponse
    }
}
