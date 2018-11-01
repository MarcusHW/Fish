package com.jsb.spider;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

class Work {
    static void worker(WebDriver driver) throws InterruptedException {
        ArrayList<String> list = new ArrayList<>();
        list.add("https://www.jianyu360.com/list/area/XJ.html");
        list.add("https://www.jianyu360.com/list/area/XZ.html");
        list.add("https://www.jianyu360.com/list/area/YN.html");

//       登陆
        driver.findElement(By.cssSelector(".loginBtn")).click();
//        等待扫码
        Thread.sleep(10000);
        driver.manage().window().maximize();
//        省份
        List<WebElement> elements = driver.findElements(By.cssSelector(".jy_province > div:nth-child(2) > ul:nth-child(1) > li > font:nth-child(2) > a:nth-child(1)"));
        ArrayList<String> list1 = new ArrayList<String>();
        for (WebElement element : elements) {
            String href = element.getAttribute("href");
            String sUrl = href;
            list1.add(sUrl);
        }
        ArrayList<ArrayList<String>> list3 = new ArrayList<ArrayList<String>>();
        for (String s : list) {
            Thread.sleep(10000);
            driver.get(s);
            ArrayList<String> list2 = new ArrayList<String>();
            String currentUrl1 = driver.getCurrentUrl();
            List<WebElement> elements1 = driver.findElements(By.cssSelector(".lucene > ul:nth-child(1) > li > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > a:nth-child(1)"));
            for (WebElement webElement : elements1) {
                String currentWindow = driver.getWindowHandle();
                Set<String> handles = driver.getWindowHandles();
                Iterator<String> it = handles.iterator();
                while (it.hasNext()) {
                    String handle = it.next();
                    if (currentWindow.equals(handle))
                        continue;
                    driver.switchTo().window(handle);
                }
                String url1 = webElement.getAttribute("dataid");
                list2.add("https://www.jianyu360.com/article/content/" + url1 + ".html");
            }
            list3.add(list2);
        }
        new Work().process(list3, driver);
    }

    private void process(ArrayList<ArrayList<String>> list, WebDriver driver) {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("ryze");
        MongoCollection<Document> collection = mongoDatabase.getCollection("FISH");
        for (ArrayList<String> strings : list) {
            for (String url : strings) {
                if (!url.contains("https")) {
                    url = "https://www.jianyu360.com/article/content/" + url + ".html";
                }
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                driver.get(url);
//                String currentWindow = driver.getWindowHandle();
//                Set<String> handles = driver.getWindowHandles();
//                Iterator<String> it = handles.iterator();
//                while (it.hasNext()) {
//                    String handle = it.next();
//                    if (currentWindow.equals(handle))
//                        continue;
//                    driver.switchTo().window(handle);
////                }
                try {
                    driver.findElement(By.partialLinkText("查看原文")).click();
                    String title = driver.getTitle();
                    String currentUrl = driver.getCurrentUrl();
                    String[] strings2 = currentUrl.split("/");
                    String hostUrl = strings2[0] + strings2[1] + strings2[2];
                    Document document = new Document();
                    document.append("name", title);
                    document.append("url", hostUrl);
                    collection.insertOne(document);
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }
    }

}
