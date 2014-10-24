package com.ozsaat.reader.rss.parsers;

import com.ozsaat.reader.rss.RssParseHandler;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class XmlParser implements Parser {

    @Override
    public String parse(InputStream inputStream) throws Exception {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        XMLReader reader = saxParser.getXMLReader();
        RssParseHandler handler = new RssParseHandler();
        InputSource input = new InputSource(inputStream);

        reader.setContentHandler(handler);
        reader.parse(input);

        return String.valueOf(handler.getItems());

    }
}
