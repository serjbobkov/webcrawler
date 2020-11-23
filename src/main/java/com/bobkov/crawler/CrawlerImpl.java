package com.bobkov.crawler;

import com.bobkov.crawler.result.CrawlResult;
import com.bobkov.crawler.result.CrawledPage;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.*;

public class CrawlerImpl implements Crawler<CrawlResult> {

    @Override
    public CrawlResult crawl(final String url, int maxDepth) {
        System.out.println("starting crawl for url = " + url);

        String host;
        try {
            host = new URL(url).getHost();
        } catch (MalformedURLException e) {
            throw new RuntimeException("bad url: '" + url + "' " + e);
        }

        ExecutorService executorService = new ThreadPoolExecutor(50, 50, 1,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

        ConcurrentMap<String, Future<CrawledPage>> resultMap = new ConcurrentHashMap<>();

        Phaser phaser = new Phaser(1);

        CrawlTask crawlTask = new CrawlTask(phaser, url, resultMap, executorService, host, 1, maxDepth);
        Future<CrawledPage> future = executorService.submit(crawlTask);
        resultMap.putIfAbsent(url, future);

        phaser.arriveAndAwaitAdvance();
        executorService.shutdown();

        System.out.println("finished crawl");

        CrawlResult result = new CrawlResult(host);
        for (Future<CrawledPage> f : resultMap.values()) {
            try {
                CrawledPage page = f.get();
                if (page != null) {
                    result.addCrawledPage(page);
                }
            } catch (InterruptedException | ExecutionException e) {
                //skip it
                System.out.println("Can't get future: " + f);
            }
        }

        return result;
    }


}
