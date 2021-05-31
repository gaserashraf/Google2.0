package com.example;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.util.*;

public class WebCrawler implements Runnable {


    private int threadsNum;
    public HashMap<String, Integer> visited_links = new HashMap<String, Integer>();
    private LinkedList<String> list = new LinkedList<String>();
    private static final int MAX_NUM = 1040;
    private Thread[] myThread;
    public int counter = 0;
    private BufferedWriter listWriter;
    private BufferedWriter docsWriter;
    private boolean finished = false;


    public WebCrawler(int Threads_num) {
        threadsNum = Threads_num;
        // adding the crawler seed
        if(init()) {
            list.addLast("https://www.wikipedia.org/");
            list.addLast("https://www.youm7.com/");
            list.addLast("javatpoint.com");
        }

        try {
            docsWriter = new BufferedWriter(new FileWriter("C:\\Users\\pc\\Desktop\\file\\Document.txt"));
        } catch (IOException e) {
            System.out.println("error in file path");
        }
        try {
            listWriter = new BufferedWriter(new FileWriter("C:\\Users\\pc\\Desktop\\file\\list.txt"));
        } catch (IOException e) {
            System.out.println("error in file path");
        }
        myThread = new Thread[this.threadsNum];
        for (int i = 0; i < this.threadsNum; i++)
            myThread[i] = new Thread(this, String.valueOf(i));


    }

    private boolean init() {
        File obj = new File("C:\\Users\\pc\\Desktop\\file\\Document.txt");
        try {
            String data;
            Scanner docs = new Scanner(obj);
            while (docs.hasNextLine()) {
                data = docs.nextLine();
                visited_links.put(data, 1);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        counter = visited_links.size();

        obj = new File("C:\\Users\\pc\\Desktop\\file\\list.txt");
        try {
            String data;
            Scanner lst = new Scanner(obj);
            while (lst.hasNextLine()) {
                data = lst.nextLine();
                list.add(data);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (list.size() == 0)
            return true;
        else
            return false;


    }


    @Override
    public void run() {
        crawl();
    }

    public void interrupt() {
        for (int i = 0; i < this.threadsNum; i++)
            myThread[i].interrupt();
    }

    public void begin() {
        for (int i = 0; i < this.threadsNum; i++)
            myThread[i].start();
        try {
            waitToFinish();
        } catch (InterruptedException e) {
            System.out.println("error");
        }
        finish();
    }

    private void finish() {

        Iterator it = visited_links.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry mp = (Map.Entry) it.next();
            try {
                docsWriter.write(mp.getKey() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            docsWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            try {
                listWriter.write((String) iterator.next() + "\n");
            } catch (IOException e) {
                System.out.println("error");
            }
        }
        try {
            listWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void crawl() {

        String next_link = "";
        while (list.peekFirst() != null && counter < MAX_NUM) {
            synchronized (this) {
                if (list.peekFirst() != null)
                    next_link = list.removeFirst();
                else
                    return;
            }

            if (Thread.currentThread().isInterrupted())
                return;

            Document doc = request(next_link);
            if (doc != null) {
                String nxt_link;
                synchronized (this) {
                    for (Element link : doc.select("a[href]")) {
                        if (counter > MAX_NUM)
                            return;
                        nxt_link = link.absUrl("href");
                        if (visited_links.get(nxt_link) == null)
                            list.addLast(nxt_link);

                    }
                }
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
                        counter++;
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
        for (int i = 0; i < this.threadsNum; i++)
            myThread[i].join();
    }


}
