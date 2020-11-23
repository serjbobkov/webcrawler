package com.bobkov.crawler;

public interface Crawler<T> {
    T crawl(String url, int maxDepth);
}
