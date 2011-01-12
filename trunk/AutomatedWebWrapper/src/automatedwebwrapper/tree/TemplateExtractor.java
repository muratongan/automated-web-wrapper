/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automatedwebwrapper.tree;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

/**
 *
 * @author murat
 */
public class TemplateExtractor {
    private String url;
    Document document;
    TreeBuilder tree;
    
    public TemplateExtractor(String url) throws MalformedURLException {
        this.url = url;
        tree = new TreeBuilder(url);
            // convert relative link hrefs
            document = tree.getDocument();
            URL original = new URL(url);
            NodeList links = document.getElementsByTagName("link");
            for (int i=0; i<links.getLength(); i++) {
                Node link = links.item(i);
                if (link.hasAttributes() && link.getAttributes().getNamedItem("href") != null) {
                    Node href = link.getAttributes().getNamedItem("href");
                    URL sub = new URL(original, href.getNodeValue());
                    href.setNodeValue(sub.toString());
                }
            }

            // convert relative script srcs
            links = document.getElementsByTagName("script");
            for (int i=0; i<links.getLength(); i++) {
                Node link = links.item(i);
                if (link.hasAttributes() && link.getAttributes().getNamedItem("src") != null) {
                    Node href = link.getAttributes().getNamedItem("src");
                    URL sub = new URL(original, href.getNodeValue());
                    href.setNodeValue(sub.toString());
                }
            }

            // convert relative img srcs
            links = document.getElementsByTagName("img");
            for (int i=0; i<links.getLength(); i++) {
                Node link = links.item(i);
                if (link.hasAttributes() && link.getAttributes().getNamedItem("src") != null) {
                    Node href = link.getAttributes().getNamedItem("src");
                    URL sub = new URL(original, href.getNodeValue());
                    href.setNodeValue(sub.toString());
                }
            }

            // convert relative a hrefs
            links = document.getElementsByTagName("a");
            for (int i=0; i<links.getLength(); i++) {
                Node link = links.item(i);
                if (link.hasAttributes() && link.getAttributes().getNamedItem("href") != null) {
                    Node href = link.getAttributes().getNamedItem("href");
                    if (href.getNodeValue().toLowerCase().startsWith("javascript:")) continue;
                    URL sub = new URL(original, href.getNodeValue());
                    href.setNodeValue(sub.toString());
                }
            }

            // convert relative img srcs
            links = document.getElementsByTagName("img");
            for (int i=0; i<links.getLength(); i++) {
                Node link = links.item(i);
                if (link.hasAttributes() && link.getAttributes().getNamedItem("src") != null) {
                    Node href = link.getAttributes().getNamedItem("src");
                    URL sub = new URL(original, href.getNodeValue());
                    href.setNodeValue(sub.toString());
                }
            }

            // mark content & navigation nodes
            for (NodeInfo node : tree.getNavigationNodes()) {
                System.out.println("Navigation: " + node.getPath());
                makeNavigation(node.getNode());
            }

            System.out.println("Main node: " + tree.getMainNodeInfo().getPath());
            makeContent(tree.getMainNode());

            Node script = document.createElement("script");
            String scriptContent = "" +
                    "var list = document.getElementsByTagName('makethisnavigation'); " +
                    "for (var i=0; i<list.length; i++) { " +
                    "  list[i].parentNode.style.backgroundColor = '#0000ff'; " +
                    "} " +
                    "document.getElementsByTagName('makethismain')[0].parentNode.style.backgroundColor = '#ff0000';";
            Node text = document.createTextNode(scriptContent);
            script.appendChild(text);
            document.getElementsByTagName("body").item(0).appendChild(script);
    }

    public void writeHtmlToFile(String fileName) throws IOException {
        File file = new File(fileName);
        System.out.println("Writing to file: " + file.getAbsolutePath());
        Tidy tidy = new Tidy();
        OutputStream os = new FileOutputStream(file);
        tidy.pprint(document, os);
        os.close();
    }

    public String getMainContent() throws FileNotFoundException {
        StringBuffer content = new StringBuffer();

        Queue<NodeInfo> nodes = new LinkedList<NodeInfo>();
        nodes.add(tree.getMainNodeInfo());

        List<String> ignoreList = new ArrayList<String>();
        ignoreList.add("script");
        ignoreList.add("head");
        ignoreList.add("style");
        ignoreList.add("form");
        ignoreList.add("frameset");

        while (!nodes.isEmpty()) {
            NodeInfo currentNodeInfo = nodes.poll();
            Node currentNode = currentNodeInfo.getNode();
            if (currentNode.getNodeType() == Node.TEXT_NODE) {
                content.append(currentNode.getNodeValue());
            } else if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                if ((!ignoreList.contains(currentNode.getNodeName())) && (!currentNodeInfo.isNavigationNode())) {
                    List<NodeInfo> children = currentNodeInfo.getChildren();
                    for (NodeInfo child : children) {
                        nodes.add(child);
                    }
                }
            }
        }
        return content.toString();
    }

    public TreeBuilder getTree() {
        return tree;
    }

    private void makeNavigation(Node node) {
        /*
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node elem = children.item(i);
            if (elem.getNodeType() == Node.ELEMENT_NODE) {
                makeNavigation(elem);
            }
        }
         * */
        if (node.getNodeType() == node.ELEMENT_NODE) {
            Node style = node.getOwnerDocument().createElement("makethisnavigation");
            node.appendChild(style);
        }
    }

    private void makeContent(Node node) {
        if (node.getNodeType() == node.ELEMENT_NODE) {
            Node style = node.getOwnerDocument().createElement("makethismain");
            node.appendChild(style);
        }
    }
}
