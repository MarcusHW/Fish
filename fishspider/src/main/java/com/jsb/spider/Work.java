package com.jsb.spider;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

class Work {
    static void worker(WebDriver driver) throws InterruptedException {
//       登陆
        driver.findElement(By.cssSelector(".loginBtn")).click();
//        等待扫码
        Thread.sleep(10000);
        List<WebElement> elements = driver.findElements(By.cssSelector(".jy_province > div:nth-child(2) > ul:nth-child(1) > li > font:nth-child(2) > a:nth-child(1)"));
        for (WebElement element : elements) {
            element.click();
            List<WebElement> elements1 = driver.findElements(By.cssSelector(".lucene > ul:nth-child(1) > li > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > a:nth-child(1)"));
            for (WebElement webElement : elements1) {
                webElement.click();
                String currentWindow = driver.getWindowHandle();
                Set<String> handles = driver.getWindowHandles();
                Iterator<String> it = handles.iterator();
                while (it.hasNext()) {
                    String handle = it.next();
                    if (currentWindow.equals(handle))
                        continue;
                    driver.switchTo().window(handle);
                }
                WebElement e = driver.findElement(By.partialLinkText("查看原文"));
                String url = e.getAttribute("href");
                e.click();
                while (it.hasNext()) {
                    String handle = it.next();
                    if (currentWindow.equals(handle))
                        continue;
                    driver.switchTo().window(handle);
                }
                String title = driver.getTitle();
                String currentUrl = driver.getCurrentUrl();
                String[] strings = currentUrl.split("/");
                String hostUrl = strings[0] + strings[1] + strings[2];
                System.out.println(hostUrl);
            }
        }
    }
}