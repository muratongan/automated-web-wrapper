/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automatedwebwrapper.WebCrawler;

import java.net.URL;


/**
 *
 * @author s098828
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    	public static void main(String[] args) {
		try {
			URL crawlURL = new URL("http://bbc.co.uk/persian");
                        int maxCrawlLevel = 2;
			int maxNumberThreads = 5;
			int maxNumberOfURLs = -1;

                        CrawlQueue crawlQueue = new CrawlQueue();
			crawlQueue.setFilenamePrefix("");
			crawlQueue.setMaximumAllURLs(maxNumberOfURLs);
			crawlQueue.push(crawlURL, 0);
			
                        new Crawler(crawlURL, crawlQueue, maxCrawlLevel, maxNumberThreads);
				return;
		} catch (Exception e) {
			System.err.println("An error occured: ");
			e.printStackTrace();
			// System.err.println(e.toString());
		}
		
	}

}
