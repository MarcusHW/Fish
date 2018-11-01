package com.jsb.tyw;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class TySpider {
    public static void spider() throws IOException {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("ryze");
        MongoCollection<org.bson.Document> collection = mongoDatabase.getCollection("TYW");

        Document doc = Jsoup.connect("http://www.ty360.com/show/zhaobiao.asp").get();
        Elements elements = doc.select("#zoom > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr> td> a");
        for (Element element : elements) {
            String href = element.attr("href");
            String name = element.text();
            org.bson.Document document = new org.bson.Document();
            document.append("name", name);
            document.append("url", href);
            collection.insertOne(document);
        }
    }
}
