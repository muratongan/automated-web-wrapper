
package automatedwebwrapper.WebCrawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import automatedwebwrapper.WebCrawler.Interfaces.InterfaceCrawlQueue;
import automatedwebwrapper.WebCrawler.UtilityClasses.URLExtractor;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * M. H. Nassabi
 * @author s098828
 */
public class CrawlQueue implements InterfaceCrawlQueue {

        private LinkedList evenQueue;
	private LinkedList oddQueue;

        private Set allURLs;
	private Set processedURLs;

	private int maxNumberOfAllURLs; //Maximum number of all URLs to be crawled on

	/**
	 * Additional data to be passed to the thread: A filename prefix that
	 * specifies where the spidered files should be stored
	 */
	String filenamePrefix;

	public CrawlQueue() {
		evenQueue = new LinkedList();
		oddQueue = new LinkedList();
		allURLs = new HashSet();
		processedURLs = new HashSet();
		maxNumberOfAllURLs = -1;
		filenamePrefix = "";
	}

	public CrawlQueue(int maxVal, String prefix) {
		evenQueue = new LinkedList();
		oddQueue = new LinkedList();
		allURLs = new HashSet();
		processedURLs = new HashSet();
		maxNumberOfAllURLs = maxVal;
		filenamePrefix = prefix;
	}

	public void setFilenamePrefix(String prefix) {
		this.filenamePrefix = prefix;
	}

	public String getFilenamePrefix() {
		return filenamePrefix;
	}

	
	 //This method is not synchronized, BE CAREFUL
	public Set getAllURLs() {
		return allURLs;
	}

	//This method is not synchronized, BE CAREFUL
	public Set getProcessedURLs() {
		return processedURLs;
	}

	public int getProcessedURLSize() {
		return processedURLs.size();
	}

	public int getAllURLSize() {
		return allURLs.size();
	}


        //How many Elements in a queue.
	public int getQueueSize(int level) {
		if (level % 2 == 0) {
			return evenQueue.size();
		} else {
			return oddQueue.size();
		}
	}



        public void setMaximumAllURLs(int value) {
		maxNumberOfAllURLs = value;
	}


	public synchronized Object pop(int level) {
		String urlString;
                String [] returnArray = new String[3];
                String rawPage = null;
                AtomicReference<Object> hostName = new AtomicReference<Object>("NULL");

                // try to get element from the appropriate queue
		// is the queue is empty, return null
		URL url = null;

                do{
                   if (level % 2 == 0) {
			if (evenQueue.size() == 0) {
				return null;
			} else {
				urlString = (String) evenQueue.removeFirst();
			}
		} else {
			if (oddQueue.size() == 0) {
				return null;
			} else {
				urlString = (String) oddQueue.removeFirst();
			}
		}

                   try {

                       url = new URL(urlString);
                       rawPage = URLExtractor.getURL(url, hostName);

                   } catch (MalformedURLException ex) {

                       Logger.getLogger(CrawlQueue.class.getName()).log(Level.SEVERE, null, ex);

                   }
                   catch(Exception exp){
                       Logger.getLogger(CrawlQueue.class.getName()).log(Level.SEVERE, null, exp);
                   }


                }while ( rawPage.equals("") );
                processedURLs.add(urlString);


		returnArray[0] = hostName.get().toString();
                returnArray[1] = rawPage;
                returnArray[2] = url.toString();
                return returnArray;
	}

	public synchronized boolean push(Object url, int level) {
		
		if (maxNumberOfAllURLs != -1 && maxNumberOfAllURLs <= allURLs.size())
			return false;

                String urlString = ((URL) url).toString();
		if (allURLs.add(urlString)) {
			
			if (level % 2 == 0) {
				evenQueue.addLast(urlString);
			} else {
				oddQueue.addLast(urlString);
			}
			return true;
		} else {
			return false;
		}
	}

	public synchronized void clear() {
		evenQueue.clear();
		oddQueue.clear();
	}

}

