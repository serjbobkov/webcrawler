package com.bobkov;


import com.bobkov.crawler.CrawlerImpl;
import com.bobkov.crawler.result.CrawlResult;
import com.bobkov.crawler.transformer.SitemapGenerator;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class App {
    public static void main(String[] args) {
        System.out.println("Starting the app!");

        long start = System.currentTimeMillis();

        String url;
        File outputPath;
        int crawlDepth;


        if (args == null || args.length != 3) {
            System.out.println("bad params. use: $ java com.bobkov.App [DOMAIN_URL] [OUTPUT_PATH] [CRAWL_DEPTH]");
            System.out.println("example: $ java com.bobkov.App http://www.homecredit.ru /tmp 2");
            return;
        } else {
            url = args[0];

            try {
                new URL(url);
            } catch (MalformedURLException e) {
                System.out.println("[error] bad url format: " + url);
                return;
            }

            outputPath = new File(args[1]);
            if (!outputPath.exists()) {
                System.out.println("[error] output path doesn't exist: " + outputPath);
                return;
            }

            try {
                crawlDepth = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                System.out.println("[error] crawl depth is not number: " + args[2]);
                return;
            }

            if (crawlDepth <= 0) {
                System.out.println("[error] crawl depth should be > 0: " + args[2]);
                return;
            }

            System.out.println("use: url = " + url + " output path = " + outputPath + " crawl depth = " + crawlDepth);
        }

        CrawlerImpl impl = new CrawlerImpl();
        CrawlResult crawlResult = impl.crawl(url, crawlDepth);

        SitemapGenerator generator = new SitemapGenerator();
        generator.generate(outputPath, crawlResult);

        System.out.println("End!");
        System.out.println("time  = " + (System.currentTimeMillis() - start) + " ms");
    }
}
