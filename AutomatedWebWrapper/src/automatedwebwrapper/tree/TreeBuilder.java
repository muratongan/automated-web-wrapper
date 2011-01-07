/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automatedwebwrapper.tree;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;
import sun.net.www.protocol.http.HttpURLConnection;

/**
 *
 * @author murat
 */
public class TreeBuilder {
    private String url;

    public TreeBuilder(String url) {
        this.url = url;
        try {
            HttpURLConnection conn = new HttpURLConnection(new URL(url), Proxy.NO_PROXY);
            conn.connect();
            Tidy tidy = new Tidy();
            Document document = tidy.parseDOM(conn.getInputStream(), null);
            printCurrentNode(document);
        } catch (IOException ex) {
            Logger.getLogger(TreeBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(url);
        return buffer.toString();
    }

    private void printCurrentNode(Node node) {
        printCurrentNode(node, 0);
    }

    private void printCurrentNode(Node node, int spaceCount) {
        String spaces = "";
        for (int i=0; i<spaceCount; i++) spaces += " ";
        System.out.println(spaces + node.getNodeName());
        NodeList nodeList = node.getChildNodes();
        for (int i=0; i<nodeList.getLength(); i++) {
            printCurrentNode(nodeList.item(i), spaceCount+1);
        }
    }
}
