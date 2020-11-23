package com.bobkov.crawler;

import com.bobkov.crawler.parser.Parser;
import com.bobkov.crawler.result.CrawledPage;

import java.util.concurrent.*;

public class CrawlTask implements Callable<CrawledPage> {

    private Phaser phaser;
    private String url;
    private ConcurrentMap<String, Future<CrawledPage>> resultMap;
    private ExecutorService executorService;
    private String host;
    private int depth;
    private int maxDepth;

    public CrawlTask(final Phaser phaser, final String url, final ConcurrentMap<String, Future<CrawledPage>> resultMap,
                     final ExecutorService executorService, final String host, final int depth, final int maxDepth) {
        this.phaser = phaser;
        this.phaser.register();

        this.url = url;
        this.resultMap = resultMap;
        this.executorService = executorService;
        this.host = host;

        this.depth = depth;
        this.maxDepth = maxDepth;
    }

    @Override
    public CrawledPage call() {
        System.out.println("get '" + url + "'");
        CrawledPage result = null;
        try {
            Parser parser = new Parser(url, host);
            result = parser.parse();

            if (result != null && depth <= maxDepth) {
                result.getLocalLinks().forEach(link -> {
                    if (!resultMap.containsKey(link)) {
                        CrawlTask task = new CrawlTask(phaser, link, resultMap, executorService, host, depth + 1, maxDepth);
                        Future<CrawledPage> future = executorService.submit(task);
                        resultMap.putIfAbsent(link, future);
                    }
                });
            }

        } catch (Exception e) {
            System.out.println("Exception occured " + e.getMessage() + " for url: " + url);
            e.printStackTrace();
        } finally {
            this.phaser.arriveAndDeregister();
        }

        System.out.println("got '" + url + "'");
        return result;
    }
}