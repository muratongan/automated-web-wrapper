/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automatedwebwrapper;

import automatedwebwrapper.WebCrawler.UtilityClasses.UrlClusterer;
import automatedwebwrapper.WebCrawler.UtilityClasses.UrlTokenizer;
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
        Set<String> processedURLs = exMain.startCrawl("http://blog.nassabi.net", 200); //This is the set of all Processed URLS crawled by the crawler
        System.out.println("number of urls: " + processedURLs.size());

        Map<Object,Set<Object>> Clusters = new HashMap<Object, Set<Object>>();

        for( Object currURL : processedURLs)
        {

            int  index = currURL.toString().split("/").length - 3;



            if (Clusters.containsKey(index))
            {
                Set urlSet = Clusters.get(index);
                urlSet.add(currURL);

            }
            else
            {
                Set<Object> newSet = new HashSet<Object>();
                newSet.add(currURL);
                Clusters.put(index, newSet );
            }


        }


        for (String item : processedURLs) {
            System.out.println(item);
            UrlTokenizer tokenizer = new UrlTokenizer(item);
            tokenizer.printInfo();
        }

        UrlClusterer clusterer = new UrlClusterer(processedURLs);
        List<List<String>> clusters = clusterer.getClusters();
        for (List<String> cluster : clusters) {
            System.out.println("Cluster (" + cluster.size() + ")");
            for(String url : cluster) {
                System.out.println(" - " + url);
            }
        }
        
        TreeBuilder tree1=null;

//        tree1 = new TreeBuilder("http://www.milliyet.com.tr/2011/01/11/index.html");
//        tree1 = new TreeBuilder("http://www.milliyet.com.tr/cep-telefonundan-yalan-olcer-e-tepki/ekonomi/sondakika/09.01.2011/1337034/default.htm");
//        tree1 = new TreeBuilder("http://www.milliyet.com.tr/dizi-setini-aratmayan-protesto/turkiye/sondakika/09.01.2011/1337052/default.htm");

//        tree1 = new TreeBuilder("http://www.ntvmsnbc.com/");
//        tree1 = new TreeBuilder("http://www.cnn.com/");
//        tree1 = new TreeBuilder("http://sportsillustrated.cnn.com/2011/football/ncaa/01/10/auburn-oregon-bcs-championship/index.html?hpt=C2");
//        tree1 = new TreeBuilder("http://www.bbc.co.uk/");
//        tree1 = new TreeBuilder("http://www.bbc.co.uk/news/world-asia-pacific-12158608");
//        tree1 = new TreeBuilder("");


//        tree1 = new TreeBuilder("http://ongan.net/");
//        tree1 = new TreeBuilder("http://ongan.net/projects");
//        tree1 = new TreeBuilder("http://ongan.net/blog/");
//        tree1 = new TreeBuilder("http://ongan.net/blog/2010/09/who-removed-you-on-facebook/");

//        tree1 = new TreeBuilder("http://e-bergi.com/2011/Ocak/");
//        tree1 = new TreeBuilder("http://e-bergi.com/2011/Ocak/goruntu-donusumleri");

//        tree1.printCurrentNode(tree1.getRoot());

    }
}
