package com.ozsaat.reader.rss.parsers;

import com.ozsaat.reader.rss.RssItem;
import com.ozsaat.reader.rss.RssReader;

import java.io.InputStream;
import java.util.List;

public class XmlParser implements Parser {
    @Override
    public List<RssItem> parse(InputStream inputStream) throws Exception {
        RssReader rssReader = new RssReader(urls[0]);
        return rssReader.getItems();
    }
}
