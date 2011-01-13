/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automatedwebwrapper;

import automatedwebwrapper.cluster.ClusterAnalyzer;
import automatedwebwrapper.cluster.MultidepthUrlClusterer;
import automatedwebwrapper.cluster.UrlClusterer;
import automatedwebwrapper.tree.TreeBuilder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
        Set<String> processedURLs = exMain.startCrawl("http://e-bergi.com", 100); //This is the set of all Processed URLS crawled by the crawler
        System.out.println("number of urls: " + processedURLs.size());

        MultidepthUrlClusterer clusterer = new MultidepthUrlClusterer(processedURLs);
        List<List<String>> clusters = clusterer.getClusters();
        for (List<String> cluster : clusters) {
            System.out.println();
            System.out.println("Cluster (" + cluster.size() + ")");
            for(String url : cluster) {
                System.out.println(" - " + url);
            }

            ClusterAnalyzer analyzer = new ClusterAnalyzer(cluster);
            
            System.out.println();
            analyzer.analyzeCluster();
            System.out.println();
            System.out.println("=================");
        }

//        TreeBuilder tree=null;

//        String url = "http://www.cnn.com/";
//        String url = "http://sportsillustrated.cnn.com/2011/football/ncaa/01/10/auburn-oregon-bcs-championship/index.html?hpt=C2";
//        String url = "http://www.bbc.co.uk/";
//        String url = "http://www.bbc.co.uk/news/world-asia-pacific-12158608";
//        String url = "http://ongan.net/";
//        String url = "http://ongan.net/blog/";
//        String url = "http://ongan.net/blog/2010/09/who-removed-you-on-facebook/";
//        String url = "http://e-bergi.com/2011/Ocak/";
//        String url = "http://e-bergi.com/2011/Ocak/goruntu-donusumleri";
//        String url = "http://www.milliyet.com.tr/dizi-setini-aratmayan-protesto/turkiye/sondakika/09.01.2011/1337052/default.htm";

//        tree = new TreeBuilder(url);
    }
}
