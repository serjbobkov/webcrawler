package com.bobkov.crawler.parser;

import com.bobkov.crawler.result.CrawledPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Parser {

    private String url;
    private String host;

    public Parser(String url, String host) {
        this.url = url;
        this.host = host;
    }

    public CrawledPage parse() {

        int attempts = 5;

        Document doc;

        CrawledPage page = null;

        boolean parsed = false;

        while (attempts > 0) {
            try {
                doc = Jsoup.connect(url)
                        .timeout(60_000)
                        .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.193 Safari/537.36")
                        .get();

                Elements links = doc.select("a[href]");
                Elements media = doc.select("img[src]");

                page = new CrawledPage(url);

                for (Element src : media) {
                    if (!src.normalName().equals("script")) {
                        String mediaUrl = src.attr("abs:src");
                        if (mediaUrl != null && !mediaUrl.isBlank()) {
                            page.addMedia(mediaUrl);
                        }
                    }
                }

                for (Element link : links) {
                    String linkUrl = link.attr("abs:href");
                    if (linkUrl != null && !linkUrl.isBlank() && !linkUrl.startsWith("mailto:")) {
                        try {
                            if (host.equalsIgnoreCase(new URL(linkUrl).getHost())) {
                                page.addLocalLink(linkUrl);
                            } else {
                                page.addExternalLink(linkUrl);
                            }
                        } catch (MalformedURLException e) {//skip it
                            System.out.println("bad url on page = " + linkUrl);
                        }
                    }
                }
                parsed = true;
                break;
            } catch (java.net.SocketTimeoutException | java.net.SocketException ste) {
                //retry
                attempts--;
                try {
                    Thread.sleep(3_000);
                } catch (InterruptedException e) {
                    //skip it
                    e.printStackTrace();
                }
            } catch (IOException e) {
                break;
            }
        }

        if (parsed) {
            System.out.println("parsed = '" + url + "'");
        } else {
            System.out.println("not parsed ='" + url + "'");
        }

        return page;
    }
}
