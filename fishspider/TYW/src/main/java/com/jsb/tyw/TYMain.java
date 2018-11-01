package com.jsb.tyw;

import java.io.IOException;

public class TYMain {
    public static void main(String[] args) {
        try {
            TySpider.spider();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
