package com.bobkov.crawler.transformer;

import com.bobkov.crawler.result.CrawlResult;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;

public class SitemapGenerator {

    public SitemapGenerator() {

    }

    public void generate(final File baseDir, final CrawlResult crawlResult) {

        System.out.println("starting to generate sitemap");

        XmlMapper xmlMapper = new XmlMapper();
        File output = null;
        try {
            output = new File(baseDir, "crawl_out.xml");
            xmlMapper.writeValue(output, crawlResult);
        } catch (IOException e) {
            System.out.println("[error] can't write data to xml file: " + output + " exception: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("finished generate sitemap");
    }


}
