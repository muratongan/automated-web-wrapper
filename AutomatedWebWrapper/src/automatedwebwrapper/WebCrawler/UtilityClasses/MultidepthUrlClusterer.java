/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automatedwebwrapper.WebCrawler.UtilityClasses;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 *
 * @author murat
 */
public class MultidepthUrlClusterer extends UrlClusterer {
    private Collection<String> urls;
    private Map<Integer,Set<String>> sameDepthClusters = new HashMap<Integer, Set<String>>();

    public MultidepthUrlClusterer(Collection<String> urls) {
        super(urls);
        this.urls = urls;
    }

    public List<List<String>> getClusters() {
        for(String currURL : urls) {
            int  index = currURL.toString().split("/").length - 3;

            if (sameDepthClusters.containsKey(index)) {
                Set urlSet = sameDepthClusters.get(index);
                urlSet.add(currURL);

            } else {
                Set<String> newSet = new HashSet<String>();
                newSet.add(currURL);
                sameDepthClusters.put(index, newSet );
            }
        }

        List<List<String>> allClusters = new LinkedList<List<String>>();
        for (Integer key : sameDepthClusters.keySet()) {
            if (key != null) {
                Set listURLs = sameDepthClusters.get(key);
                UrlClusterer clusterer = new UrlClusterer(listURLs);
                List<List<String>> clusters = clusterer.getClusters();
                for (List<String> cluster : clusters) {
                    if (cluster.size() > 0) {
                        allClusters.add(cluster);
                    }
                }
            }
        }
        return allClusters;
    }
}
