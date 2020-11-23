package com.bobkov.crawler.result;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.HashSet;
import java.util.Set;

@JacksonXmlRootElement(localName = "page")
public class CrawledPage {

    public CrawledPage(final String url) {
        this.url = url;
    }

    @JacksonXmlProperty(localName = "url")
    private String url;

    @JacksonXmlElementWrapper(localName = "medias")
    @JacksonXmlProperty(localName = "media")
    private Set<String> media = new HashSet<>();

    @JacksonXmlElementWrapper(localName = "localUrls")
    @JacksonXmlProperty(localName = "localUrl")
    private Set<String> localLinks = new HashSet<>();

    @JacksonXmlElementWrapper(localName = "externalUrls")
    @JacksonXmlProperty(localName = "externalUrl")
    private Set<String> externalLinks = new HashSet<>();

    public void addMedia(final String url) {
        media.add(url);
    }

    public void addLocalLink(final String url) {
        localLinks.add(url);
    }

    public void addExternalLink(final String url) {
        externalLinks.add(url);
    }

    public Set<String> getLocalLinks() {
        return localLinks;
    }

}
