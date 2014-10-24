package com.ozsaat.reader.rss.parsers;


import java.io.InputStream;

public interface Parser {

    public String parse(InputStream inputStream) throws Exception;

}
