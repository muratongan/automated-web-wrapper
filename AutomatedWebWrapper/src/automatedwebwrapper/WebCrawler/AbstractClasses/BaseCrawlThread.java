/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automatedwebwrapper.WebCrawler.AbstractClasses;

import automatedwebwrapper.WebCrawler.Interfaces.InterfaceCrawlQueue;
import automatedwebwrapper.WebCrawler.Interfaces.InterfaceThreadMsgReceiver;
import automatedwebwrapper.WebCrawler.*;


/**
 * This is the base class for all crawl threads used to crawl over a website.
 * Each class that inhertis this abstract class implements the
 * The thread
 */
abstract public class BaseCrawlThread extends Thread {

    //Fields

    protected int level;
    protected int id;
    protected InterfaceCrawlQueue queue;
    protected InterfaceThreadMsgReceiver threadMsgReceiver;
    protected ThreadManager threadManager;


    //GETTERS and SETTERS

    public ThreadManager getThreadManager() {
        return threadManager;
    }

    public void setThreadManager(ThreadManager threadManager) {
        this.threadManager = threadManager;
    }

    public int getThreadId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public InterfaceCrawlQueue getQueue() {
        return queue;
    }

    public void setQueue(InterfaceCrawlQueue queue) {
        this.queue = queue;
    }

    public InterfaceThreadMsgReceiver getThreadMsgReceiver() {
        return threadMsgReceiver;
    }

    public void setThreadMsgReceiver(InterfaceThreadMsgReceiver threadMsgReceiver) {
        this.threadMsgReceiver = threadMsgReceiver;
    }

    // pop new urls from the queue until queue is empty


    public void run()
    {
        Object newCrawlObj = queue.pop(level);
        String[] URLtext = (String [])newCrawlObj;
        String text = URLtext[2];

        while (newCrawlObj != null)
        {
            threadMsgReceiver.msgReceived( text , this.getThreadId());

            process(newCrawlObj);  //New crawled object is processed

            if ( this.getThreadManager().getMaxNoThreads() > this.getThreadManager().getRunningThreads())
            {
                try
                {
                    getThreadManager().createThreads();

                }
                catch (Exception e)
                {
                    System.err.println("Error happend for Thread with id => [" + id + "] while trying to start new threads " + e.toString());
		}
            }
            newCrawlObj = queue.pop(level);
        }

        this.getThreadManager().threadDoneTask(id);

    }
    /* Abstract Method process() will be implemented by classes inherting
     * this class. Basically this is the place where the retreived URL 
     * gets processed in order to extract new URLs and saving the html document
     * 
    */
    public abstract void process(Object o);















}
