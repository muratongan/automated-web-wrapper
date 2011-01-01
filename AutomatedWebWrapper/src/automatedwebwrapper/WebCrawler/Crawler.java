/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automatedwebwrapper.WebCrawler;

import java.net.URL;
import automatedwebwrapper.WebCrawler.Interfaces.InterfaceCrawlQueue;
import automatedwebwrapper.WebCrawler.Interfaces.InterfaceThreadMsgReceiver;

/**
 * M. H. Nassabi
 * @author s098828
 */
public class Crawler implements InterfaceThreadMsgReceiver {

    private ThreadManager threadManager;
    private InterfaceCrawlQueue queue;

    public InterfaceCrawlQueue getQueue(){
        return queue;
    }

    public Crawler(URL crawlURL, InterfaceCrawlQueue crawlQueue, int maxLevel, int maxThreads)
            throws InstantiationException, IllegalAccessException{

         threadManager = new ThreadManager(crawlURL, 0, maxLevel, maxThreads, crawlQueue, this, CrawlThread.class );

        }
    
    
    public void msgReceived(Object theMessage, int threadId) {
        System.out.println("Thread [" + threadId + "] " + "Started Processing " + theMessage.toString());
    }

    public void threadFinishedProcessing(int threadId) {
        System.out.println("Thread with threadId: [" + threadId + "] finished processesing");
    }

    public void allThreadsFinishedProcessing() {
        System.out.println("\n*********THE CRAWLER HAS FINISHED CRAWLING*********");
        queue = threadManager.getCrawlQueue();
    }




    

}
