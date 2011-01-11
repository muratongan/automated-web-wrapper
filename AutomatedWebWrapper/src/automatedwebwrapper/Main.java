/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automatedwebwrapper;

import automatedwebwrapper.tree.TreeBuilder;


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
        /*
        automatedwebwrapper.WebCrawler.Main exMain = new automatedwebwrapper.WebCrawler.Main();
        Set<String> processedURLs = exMain.startCrawl(); //This is the set of all Processed URLS crawled by the crawler
        System.out.println("number of urls: " + processedURLs.size());
        for (String item : processedURLs) {
            System.out.println(item);
        }
         * */

        TreeBuilder tree1=null;

//        tree1 = new TreeBuilder("http://www.milliyet.com.tr/2011/01/11/index.html");
//        tree1 = new TreeBuilder("http://www.milliyet.com.tr/cep-telefonundan-yalan-olcer-e-tepki/ekonomi/sondakika/09.01.2011/1337034/default.htm");
//        tree1 = new TreeBuilder("http://www.milliyet.com.tr/dizi-setini-aratmayan-protesto/turkiye/sondakika/09.01.2011/1337052/default.htm");

        tree1 = new TreeBuilder("http://www.ntvmsnbc.com/");


//        tree1 = new TreeBuilder("http://ongan.net/");
//        tree1 = new TreeBuilder("http://ongan.net/projects");
//        tree1 = new TreeBuilder("http://ongan.net/blog/");
//        tree1 = new TreeBuilder("http://ongan.net/blog/2010/09/who-removed-you-on-facebook/");

//        tree1 = new TreeBuilder("http://e-bergi.com/2011/Ocak/");
//        tree1 = new TreeBuilder("http://e-bergi.com/2011/Ocak/goruntu-donusumleri");

//        tree1.printCurrentNode(tree1.getRoot());

    }
}
