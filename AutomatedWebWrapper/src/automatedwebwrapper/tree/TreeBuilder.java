/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automatedwebwrapper.tree;

import java.io.IOException;
import java.net.Proxy;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
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

    public TreeBuilder(String url) {
        this.url = url;
        try {
            HttpURLConnection conn = new HttpURLConnection(new URL(url), Proxy.NO_PROXY);
            conn.connect();
            Tidy tidy = new Tidy();
            document = tidy.parseDOM(conn.getInputStream(), null);
        } catch (IOException ex) {
            Logger.getLogger(TreeBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.flush();
        System.err.flush();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(TreeBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }

        // build info tree
        if (document != null) {
            info = NodeInfo.buildTreeWithRoot(null, document);
        }

//        info.printTree();
        System.out.println();
        System.out.println("PAGE:");
        System.out.println(url);
        NodeInfo contentNode = info.findContentNode();
        System.out.println();
        System.out.println("MAIN CONTENT NODE:");
        System.out.println(contentNode.getPath());
//        System.out.println("Content:");
//        System.out.println();
//        System.out.println(contentNode.getTextContent());
        System.out.println();
        System.out.println("NAVIGATION NODES:");
        List<NodeInfo> navNodes = info.getNavigationNodes();
        for (NodeInfo navNode : navNodes) {
            System.out.println(navNode.getPath());
        }
    }
}
