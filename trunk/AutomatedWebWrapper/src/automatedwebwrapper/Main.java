/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automatedwebwrapper;

import automatedwebwrapper.tree.TreeBuilder;
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
        Set<String> processedURLs = exMain.startCrawl(); //This is the set of all Processed URLS crawled by the crawler
        System.out.println("number of urls: " + processedURLs.size());
        for (String item : processedURLs) {
            System.out.println(item);
            TreeBuilder treeBuilder = new TreeBuilder(item);
            System.out.println();
            System.out.println(treeBuilder);
            break;
        }
    }
}
