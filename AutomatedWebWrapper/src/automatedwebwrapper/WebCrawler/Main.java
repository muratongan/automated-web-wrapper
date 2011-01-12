/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automatedwebwrapper.WebCrawler;

import java.net.URL;
import java.util.Set;


/**
 *
 * @author s098828
 */
public class Main {

    /**
     * @param args the command line arguments
     */
        private Set processedURLs;
        private Thread mainThread;

        public Set getProcessedURLs()
        {
            return this.processedURLs;
        }

        public void setProcessedURLs(Set URLs)
        {
            this.processedURLs = URLs;
        }

    	public Set startCrawl(String mainURL, int maxNumberOfURLs) {
		try {
			mainThread = new Thread("MainThread");
                        mainThread.start();
                        URL crawlURL = new URL(mainURL);
                        int maxCrawlLevel = 2;
			int maxNumberThreads = 7;

                        System.out.println("Crawler is initiated with => " + maxNumberThreads + " threads.");
                        System.out.println("Crawler has maximum crawl level of => " + maxCrawlLevel + ".");
                        System.out.println("Maximum number of URLs to be crawled are => " + maxNumberOfURLs + " URLs");
                        System.out.println("*************Crawler Started Crawling*******************");

                        CrawlQueue crawlQueue = new CrawlQueue();
			crawlQueue.setFilenamePrefix("");
			crawlQueue.setMaximumAllURLs(maxNumberOfURLs);
			crawlQueue.push(crawlURL, 0);
			
                        Crawler crawler = new Crawler(crawlURL, crawlQueue, maxCrawlLevel, maxNumberThreads);

                        while ( crawler.getQueue() == null)
                        {
                            Thread.sleep(100);
                        }
                        mainThread.stop();
                        setProcessedURLs(crawler.getQueue().getProcessedURLs());
                        return this.getProcessedURLs();
		} catch (Exception e) {
			System.err.println("An error occured: ");
			e.printStackTrace();
			// System.err.println(e.toString());
                        return null;
		}
		
	}

}
