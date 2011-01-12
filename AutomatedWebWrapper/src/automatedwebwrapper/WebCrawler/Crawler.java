/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automatedwebwrapper.WebCrawler;

import java.io.IOException;
import java.net.URL;
import automatedwebwrapper.WebCrawler.Interfaces.InterfaceCrawlQueue;
import automatedwebwrapper.WebCrawler.Interfaces.InterfaceThreadMsgReceiver;
import automatedwebwrapper.WebCrawler.UtilityClasses.URLExtractor;
import java.net.MalformedURLException;
import java.util.concurrent.atomic.AtomicReference;

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
            throws InstantiationException, IllegalAccessException, IOException{
            try{
                AtomicReference<Object> hostName = new AtomicReference<Object>("NULL");
                URLExtractor.getURL(crawlURL, hostName);
                
                threadManager = new ThreadManager((URL)hostName.get(), 0, maxLevel, maxThreads, crawlQueue, this, CrawlThread.class );
            
            }
            catch (MalformedURLException e)
            {
                    System.err.println("Error occured while trying to cast string into URL!");
            
            }





        }
    
    
    public void msgReceived(Object theMessage, int threadId) {
        System.out.println("Thread with threadId ->" + threadId + "<- " + "started processing URL -> " + theMessage.toString());
    }

    public void threadFinishedProcessing(int threadId) {
        System.out.println("Thread with threadId ->" + threadId + "<- finished processesing assigned URLs");
    }

    public void allThreadsFinishedProcessing() {
        System.out.println("\n*********THE CRAWLER HAS FINISHED CRAWLING*********");
        queue = threadManager.getCrawlQueue();
    }




    

}
