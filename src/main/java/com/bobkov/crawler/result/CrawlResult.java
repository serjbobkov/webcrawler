package com.bobkov.crawler.result;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.LinkedList;
import java.util.List;

@JacksonXmlRootElement(localName = "sitemap")
public class CrawlResult {

    @JacksonXmlProperty
    private String host;

    @JacksonXmlElementWrapper(localName = "pages")
    @JacksonXmlProperty(localName = "page")
    List<CrawledPage> pages = new LinkedList<>();

    public CrawlResult(final String host) {
        this.host = host;
    }

    public void addCrawledPage(final CrawledPage page) {
        pages.add(page);
    }

}
