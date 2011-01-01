

package automatedwebwrapper.WebCrawler;

import java.net.URL;
import automatedwebwrapper.WebCrawler.AbstractClasses.BaseCrawlThread;
import automatedwebwrapper.WebCrawler.Interfaces.InterfaceCrawlQueue;
import automatedwebwrapper.WebCrawler.Interfaces.InterfaceThreadMsgReceiver;

/**
 * M. H. Nassabi
 * @author s098828
 */
public class ThreadManager {

  
	private int currentLevel;
	private int maximumLevel; //assign -1 For an unlimited number of levels
	private int maximumNoThreads; //assign -1 For an unlimited number of threads
        private URL baseURL;

        private InterfaceCrawlQueue crawlQueue;
 	private InterfaceThreadMsgReceiver threadMsgReceiver;

	private Class threadClass;

	private int idCounter; // A unique synchronized counter
	 
	private int numberOfThreads; //Current Number of threads


        //Getters

        URL getBaseURL() {
            return this.baseURL;
        }

        public synchronized int getNewId() {
		return idCounter++;
	}


        public int getMaxNoThreads() {
		return maximumNoThreads;
	}


	public int getMaxCrawlLevel() {
		return maximumLevel;
	}

	public int getRunningThreads() {
		return numberOfThreads;
	}

        public InterfaceCrawlQueue getCrawlQueue(){
            return this.crawlQueue;
        }

        //Setters

        public synchronized void setMaxThreads(int maximumNoThreads)
            throws InstantiationException, IllegalAccessException
        {
		this.maximumNoThreads = maximumNoThreads;
		createThreads();
	}


       //Constuctors

       public ThreadManager( URL baseURL, int currentLevel, int maximumLevel, int maximumNoThreads, InterfaceCrawlQueue crawlQueue, InterfaceThreadMsgReceiver threadMsgReceiver,Class threadClass)
               throws InstantiationException, IllegalAccessException{

        
        this.currentLevel = currentLevel;
        this.maximumLevel = maximumLevel;
        this.maximumNoThreads = maximumNoThreads;
        this.crawlQueue = crawlQueue;
        this.threadMsgReceiver = threadMsgReceiver;
       	this.threadClass = threadClass;
        this.baseURL = baseURL;

        this.idCounter = 0;
	this.numberOfThreads = 0;
	createThreads();
       }


       //Methods

	public synchronized void threadDoneTask(int threadId) {

            numberOfThreads--;
            threadMsgReceiver.threadFinishedProcessing(threadId);

            if (numberOfThreads == 0) {
                currentLevel++;
		
                if (currentLevel > maximumLevel) {

		   threadMsgReceiver.allThreadsFinishedProcessing();
                    return;
                }

                if (crawlQueue.getQueueSize(currentLevel) == 0) {
                    threadMsgReceiver.allThreadsFinishedProcessing();
                    return;
                    }
		try {
                    createThreads();
                    }
                catch (InstantiationException e) {
                    System.out.println(e.toString());
                }
		catch (IllegalAccessException e) {
                    System.out.println(e.toString());
                }

            }
	}


	public synchronized void createThreads()
                throws InstantiationException, IllegalAccessException {

            int remainingThreads = maximumNoThreads - numberOfThreads;
            int remainingURLsToBeCrawled = crawlQueue.getQueueSize(currentLevel);
            
            if (remainingURLsToBeCrawled < remainingThreads || maximumNoThreads == -1) {
			remainingThreads = remainingURLsToBeCrawled;
		}
	
		for (int n = 0; n < remainingThreads; n++) {
                    BaseCrawlThread thread =  (BaseCrawlThread) threadClass.newInstance();
                    thread.setThreadManager(this);
                    thread.setThreadMsgReceiver(threadMsgReceiver);
                    thread.setLevel(currentLevel);
                    thread.setQueue(crawlQueue);
                    thread.setId(numberOfThreads++);
		
                    thread.start();

		}
	}


}
