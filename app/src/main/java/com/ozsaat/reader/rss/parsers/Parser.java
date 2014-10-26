package com.ozsaat.reader.rss.parsers;


import com.ozsaat.reader.rss.RssItem;

import java.io.InputStream;
import java.util.List;

public interface Parser {

    public List<RssItem> parse(InputStream inputStream) throws Exception;

}
