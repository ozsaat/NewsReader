package com.ozsaat.reader.rss;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class RssParseHandler extends DefaultHandler {

    private List<RssItem> rssItems;
    private RssItem currentItem;
    private boolean parsingTitle;
    private boolean parsingLink;

    public RssParseHandler() {
        rssItems = new ArrayList<>();
    }

    public List<RssItem> getItems() {
        return rssItems;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
            case "item":
                currentItem = new RssItem();
                break;
            case "title":
                parsingTitle = true;
                break;
            case "link":
                parsingLink = true;
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case "item":
                rssItems.add(currentItem);
                currentItem = null;
                break;
            case "title":
                parsingTitle = false;
                break;
            case "link":
                parsingLink = false;
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (parsingTitle) {
            if (currentItem != null)
                currentItem.setTitle(new String(ch, start, length));
        } else if (parsingLink) {
            if (currentItem != null) {
                currentItem.setLink(new String(ch, start, length));
                parsingLink = false;
            }
        }
    }
}
