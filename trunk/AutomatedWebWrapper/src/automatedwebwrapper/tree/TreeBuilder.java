/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automatedwebwrapper.tree;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Proxy;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.tidy.Tidy;
import sun.net.www.protocol.http.HttpURLConnection;

/**
 *
 * @author murat
 */
public class TreeBuilder {
    private String url;
    Document document = null;
    NodeInfo info = null;
    List<NodeInfo> navNodes = null;
    NodeInfo contentNode = null;

    public TreeBuilder(String url) {
        this.url = url;
        try {
            HttpURLConnection conn = new HttpURLConnection(new URL(url), Proxy.NO_PROXY);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            conn.connect();
            String charset;
            try {
                charset = conn.getHeaderField("Content-Type").split("=")[1];
            } catch(Exception ex) {
                charset = "utf-8";
//                System.out.println("Header : " + conn.getHeaderField("Content-Type"));
            }
            Tidy tidy = new Tidy();
            tidy.setInputEncoding(charset);
            PrintWriter writer = new PrintWriter(new OutputStream() {
                @Override
                public void write(int b) throws IOException {
                }
            });
            tidy.setErrout(writer);
            document = tidy.parseDOM(conn.getInputStream(), null);
        } catch (IOException ex) {
            System.out.println("Error visiting the page");
//            Logger.getLogger(TreeBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }

        // build info tree
        if (document != null) {
            info = NodeInfo.buildTreeWithRoot(null, document);
        }

        navNodes = info.getNavigationNodes();
        for (NodeInfo navNode : navNodes) {
            navNode.setTextWeight(0);
        }

        contentNode = info.findContentNode();

        /*
        info.printTree();
        System.out.println();
        System.out.println("PAGE:");
        System.out.println(url);
        NodeInfo contentNode = info.findContentNode();
//        System.out.println("Content:");
//        System.out.println();
//        System.out.println(contentNode.getTextContent());
        System.out.println();
        System.out.println("NAVIGATION NODES:");
        List<NodeInfo> navNodes = info.getNavigationNodes();
        for (NodeInfo navNode : navNodes) {
            System.out.println(navNode.getPath());
            navNode.setTextWeight(0);
        }
        System.out.println();
        System.out.println("MAIN CONTENT NODE:");
        System.out.println(contentNode.getPath());
        System.out.println();
        if (contentNode.isNavigationNode()) {
            System.out.println("THIS IS A NAVIGATION PAGE");
        } else {
            System.out.println("THIS IS A CONTENT PAGE");
        }
         * */
    }

    public boolean isNavigationPage() {
        return contentNode.isNavigationNode();
    }

    public Node getMainNode() {
        return contentNode.getNode();
    }

    public NodeInfo getMainNodeInfo() {
        return contentNode;
    }

    public String getMainContentBlockPath() {
        return contentNode.getPath();
    }

    public List<NodeInfo> getNavigationNodes() {
        List<NodeInfo> navPaths = new LinkedList<NodeInfo>();
        for (NodeInfo info : navNodes) {
            navPaths.add(info);
        }
        return navPaths;
    }

    public List<String> getNavigationBlocks() {
        List<String> navPaths = new LinkedList<String>();
        for (NodeInfo info : navNodes) {
            navPaths.add(info.getPath());
        }
        return navPaths;
    }

    public Document getDocument() {
        return document;
    }
}
