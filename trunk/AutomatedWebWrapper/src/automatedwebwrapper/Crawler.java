/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automatedwebwrapper;

import java.net.HttpURLConnection; // to connect a url and get the content
import java.util.LinkedList; // queue for urls (pop, push methods)
import java.util.ArrayList;  // which pages have already been crawled (contains method)
import java.util.List;

/**
 *
 * @author hossein
 */
public class Crawler {
    private String URL;
    private int maxNumPages = Integer.MAX_VALUE;

    public Crawler() {
    }

    public Crawler(String URL) {
        this.URL = URL;
    }

    public Crawler(String URL, int maxNumPages) {
        this.URL = URL;
        this.maxNumPages = maxNumPages;
    }

    public List<String> getAllURLs() {
        List<String> urls = new ArrayList<String>();
        urls.add("test");
        return urls;
    }

    public List<List<String>> getURLClusters() {
        return null;
    }
}
