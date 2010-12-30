
package automatedwebwrapper.WebCrawler.Interfaces;
import java.util.Set;
/**
 *
 * @author s098828
 */
public interface InterfaceCrawlQueue {
        public Set getAllURLs(); //All Urls crawled by the crawler
	public Set getProcessedURLs(); // Those Urls which are processed

        public int getAllURLSize();
        public int getProcessedURLSize();

        public int getQueueSize(int level); //The size of the queue in a level
	
        public void setMaximumAllURLs(int value); //Maximum number of Urls for AllUrls Set
	
        public Object pop(int level); //poping a crawl object from the queue in a level
	public boolean push(Object crawlObject, int level); //pushing a crawl object in a queue in a level

        public void clear();
}
