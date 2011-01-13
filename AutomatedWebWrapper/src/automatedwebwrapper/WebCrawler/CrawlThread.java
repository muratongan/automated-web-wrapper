/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automatedwebwrapper.WebCrawler;

import automatedwebwrapper.WebCrawler.AbstractClasses.BaseCrawlThread;
import automatedwebwrapper.WebCrawler.UtilityClasses.URLExtractor;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicReference;

/**
 * M. H. Nassabi
 * @author s098828
 */
public class CrawlThread extends BaseCrawlThread {
   @Override
    public void process(Object o) {
		try {
			String[] returnedArray = (String[])o;
                        

                        String hostName = returnedArray[0];

                        URL baseURL = threadManager.getBaseURL();

                        String rawPage = returnedArray[1];
                        


                        String smallPage = rawPage.toLowerCase().replaceAll("\\s", " ");

                        Vector links = URLExtractor.extractLinks(rawPage, smallPage);

                        URL pageURL = new URL(hostName);

                        for (int n = 0; n < links.size(); n++) {
				try {
					// urls might be relative to current page
					URL link = new URL(pageURL, (String) links.elementAt(n));


                                         if (!(link.getHost().equals(baseURL.getHost())))
                                           continue;

                                        // If layers are not used, write everything into same layer
                                        if (threadManager.getMaxCrawlLevel() == -1)
						queue.push(link, level);
					else
						queue.push(link, level + 1);
				} catch (MalformedURLException e) {

                                }

			}
		}
                 catch (MalformedURLException e)
                {
                    System.err.println("Error occured while trying to cast string into URL!");
                }
                catch (Exception ex)
                {
                    System.err.println("CRAWLTHREAD_An error Occured => " + ex.toString());
                }

                
    }
}