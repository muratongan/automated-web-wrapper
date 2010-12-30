
package automatedwebwrapper.WebCrawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import automatedwebwrapper.WebCrawler.Interfaces.InterfaceCrawlQueue;

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
		// try to get element from the appropriate queue
		// is the queue is empty, return null
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
			URL url = new URL(urlString);
			processedURLs.add(urlString);
			return url;
		} catch (MalformedURLException e) {
                        System.out.println("Error in STRING FORMAT, NOT URL");
                    	return null;
		}
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

