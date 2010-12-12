/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automatedwebwrapper;

import java.util.List;

/**
 *
 * @author murat
 */
public class CrawlerTest {
    public static void main(String[] args) {
        Crawler myCrawler = new Crawler("http://sekerinrengi.blogspot.com/");
        List<String> urls = myCrawler.getAllURLs();
        System.out.println("All pages:");
        for (String url : urls) {
            System.out.println(" - " + url);
        }
    }
}
