package com.jsb.spider;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Fish {
    public static void main(String[] args) throws InterruptedException {
        WebDriver webDriver = getWebDriver("https://www.jianyu360.com/jylab/supsearch/index.html");
        Work.worker(webDriver);

    }


    public static WebDriver getWebDriver(String url) {
        System.setProperty("webdriver.chrome.driver", "F:/fishspider/src/main/resources/2.37/chromedriver_win32/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get(url);
        return driver;
    }
}
