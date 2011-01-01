/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automatedwebwrapper.WebCrawler;

import automatedwebwrapper.WebCrawler.AbstractClasses.BaseCrawlThread;
import automatedwebwrapper.WebCrawler.UtilityClasses.URLExtractor;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

/**
 * M. H. Nassabi
 * @author s098828
 */
public class CrawlerThread extends BaseCrawlThread {
   @Override
    public void process(Object o) {
		try {
			URL pageURL = (URL) o;

                        URL baseURL = threadManager.getBaseURL();

                        String rawPage = URLExtractor.getURL(pageURL);
                        String smallPage = rawPage.toLowerCase().replaceAll("\\s", " ");

			Vector links = URLExtractor.extractLinks(rawPage, smallPage);

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
		} catch (Exception e) {}
    }
}