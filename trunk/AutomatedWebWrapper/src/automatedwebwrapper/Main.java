/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automatedwebwrapper;

import java.util.Set;


/**
 *
 * @author murat
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        automatedwebwrapper.WebCrawler.Main exMain = new automatedwebwrapper.WebCrawler.Main();
        Set processedURLs = exMain.startCrawl(); //This is the set of all Processed URLS crawled by the crawler
        
    }

}
