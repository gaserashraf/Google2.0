package com.example;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

public class WebCrawler implements Runnable {

    private final static int MAX_DEPTH = 3;
    private Thread myThread;
    private String firstLink;
    private ArrayList<String> vistedLinks;

    public WebCrawler(String url) {
        firstLink = url;
        vistedLinks = new ArrayList<String>();
        myThread = new Thread(this);
        myThread.start();
    }


    @Override
    public void run() {
        crawl(1 , firstLink);
    }

    private void crawl(int level, String url) {
        if (level > MAX_DEPTH)
            return;
        Document doc = request(url);
        if (doc != null) {
            for (Element link : doc.select("a[href]")) {
                String next_link = link.absUrl("href");
                if (!vistedLinks.contains(next_link))
                    crawl(level++, next_link);
            }
        }
    }

    private Document request(String url) {
        try {
            Connection con = Jsoup.connect(url);
            Document doc = con.get();
            if (con.response().statusCode() == 200) {
                System.out.println("Received webpage at url :" + url);
                String title = doc.title();
                System.out.println("opened : "+title);
                vistedLinks.add(url);
                return doc;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }



}
