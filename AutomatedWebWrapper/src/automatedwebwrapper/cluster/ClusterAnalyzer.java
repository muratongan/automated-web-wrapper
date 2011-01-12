/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automatedwebwrapper.cluster;

import automatedwebwrapper.tree.TreeBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author murat
 */
public class ClusterAnalyzer {
    private List<String> cluster;

    public ClusterAnalyzer(List<String> cluster) {
        this.cluster = cluster;
    }

    public String analyzeCluster() {
        String analysisResult = "";
        Map<String, Integer> mainContentNodes = new HashMap<String, Integer>();
        Map<String, Integer> navigationNodes = new HashMap<String, Integer>();
        int navigationPageCount = 0;
        int contentPageCount = 0;
        int totalPageCount = 0;

        for (String url : cluster) {
            try {
                TreeBuilder tree = new TreeBuilder(url);

                // page type
                if (tree.isNavigationPage()) {
                    navigationPageCount++;
                } else {
                    contentPageCount++;
                }
                totalPageCount++;

                // add main content block
                String mainPart = tree.getMainContentBlockPath();
                if (mainContentNodes.containsKey(mainPart)) {
                    mainContentNodes.put(mainPart, mainContentNodes.get(mainPart)+1);
                } else {
                    mainContentNodes.put(mainPart, 1);
                }

                // add navigation blocks
                List<String> navBlocks = tree.getNavigationBlocks();
                for (String navBlock : navBlocks) {
                    if (navigationNodes.containsKey(navBlock)) {
                        navigationNodes.put(navBlock, navigationNodes.get(navBlock)+1);
                    } else {
                        navigationNodes.put(navBlock, 1);
                    }
                }
            } catch (Exception ex) {
                System.err.println("Page cannot be processed for URL: " + url);
                System.err.println(ex.getMessage());
            }
        }

        if (totalPageCount > 0) {
            // cluster type
            if (navigationPageCount > contentPageCount) {
                analysisResult += "This is a NAVIGATION PAGE cluster " +
                        "(" + 100*navigationPageCount/totalPageCount + "%)\n";
            } else {
                analysisResult += "This is a CONTENT PAGE cluster " +
                        "(" + 100*contentPageCount/totalPageCount + "%)\n";
            }

            if (navigationPageCount <= contentPageCount) {
                // find most frequent main content block
                int maxCount = -1;
                String maxItem = null;
                for (Entry<String, Integer> node : mainContentNodes.entrySet()) {
                    if (node.getValue() > maxCount) {
                        maxCount = node.getValue();
                        maxItem = node.getKey();
                    }
                }
                analysisResult += "Main content block for the cluster ("+100*maxCount/totalPageCount+"%):\n";
                analysisResult += " " + maxItem + "\n";

                // find navigation parts
                analysisResult += "Navigation nodes :\n";
                for (Entry<String, Integer> node : navigationNodes.entrySet()) {
                    analysisResult += node.getKey() + " ("+100*maxCount/totalPageCount+"%)\n";
                }
            }
        }
        return analysisResult;
    }
}
