package com.example;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class WebCrawler implements Runnable {

    private final static int MAX_DEPTH = 3;
    private Thread[] myThread;
    private int Threads_num;
    private ArrayList<String> Thread_seed;
    private String firstLink;
    //public ArrayList<String> vistedLinks;
    public HashMap<String, Integer> visited_links = new HashMap<String, Integer>();
    private boolean running = true;


    public WebCrawler(String url, int Threads_num) {
        firstLink = url;
        //vistedLinks = new ArrayList<String>();
        this.Threads_num = Threads_num;
        myThread = new Thread[this.Threads_num];
        for (int i = 0; i < Threads_num; i++) {
            myThread[i] = new Thread(this);
            myThread[i].setName(String.valueOf(i));
        }
        Thread_seed = new ArrayList<String>();

    }


    @Override
    public void run() {

        crawl(1, Thread_seed.get(Integer.parseInt(Thread.currentThread().getName())));
    }

    public void begin() {
        Document doc = request(firstLink);
        if (doc != null) {
            int counter = 1;
            String next_link;
            for (Element link : doc.select("a[href]")) {
                if (counter > Threads_num)
                    break;
                next_link = link.absUrl("href");
                Thread_seed.add(next_link);
                counter++;
            }
            for (int i = 0; i < Threads_num; i++)
                myThread[i].start();

            try {
                waitToFinish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void crawl(int level, String url) {
        if (level > MAX_DEPTH)
            return;

        if (visited_links.size() >= 1000)
            running = false;

        Document doc = request(url);
        if (doc != null && running) {
            for (Element link : doc.select("a[href]")) {
                if (!running)
                    break;
                String next_link = link.absUrl("href");
                if (visited_links.get(next_link) == null)
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
                System.out.println("opened : " + title);
                synchronized (this) {
                    if (visited_links.get(url) == null) {
                        visited_links.put(url, 1);
                        return doc;
                    }
                }
                return null;
            }
            return null;
        } catch (Exception e) {
            return null;
        }

    }

    private void waitToFinish() throws InterruptedException {
        for (int i = 0; i < Threads_num; i++)
            myThread[i].join();
    }


}
