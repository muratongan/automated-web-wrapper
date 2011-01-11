/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automatedwebwrapper.tree;

import java.util.LinkedList;
import java.util.List;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author murat
 */
public class NodeInfo {
    private NodeInfo parent;
    private Node node;
    private int textWeight;
    private int linkWeight;
    private int linkCount;
    private List<NodeInfo> children;

    public NodeInfo(NodeInfo parent, Node node) {
        this.node = node;
        if (node.getNodeType() == Node.TEXT_NODE) {
            this.textWeight = node.getNodeValue().length();
        } else {
            this.textWeight = 0;
        }
        if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("a")) {
            this.linkCount = 1;
        } else {
            this.linkCount = 0;
        }
        this.linkWeight = 0;
        this.parent = parent;
        this.children = new LinkedList<NodeInfo>();
        if (this.parent != null) {
            this.parent.addChild(this);
            if (this.linkCount == 1) {
                this.parent.incrementLinkCount();
            }
        }
    }

    public Node getNode() {
        return node;
    }

    public int getTextWeight() {
        return textWeight;
    }

    public int getLinkWeight() {
        return linkWeight;
    }

    public int getLinkCount() {
        return linkCount;
    }

    public void setTextWeight(int textWeight) {
        // exclude certain elements
        if (this.node.getNodeType() == Node.ELEMENT_NODE) {
            if (this.node.getNodeName().equals("head") || 
                this.node.getNodeName().equals("script") ||
                this.node.getNodeName().equals("style")) {
                return;
            }
        }

        // for other elements
        if (this.parent != null) {
            int difference = textWeight-this.textWeight;
            int parentOld = parent.getTextWeight();
            parent.setTextWeight(parentOld+difference);
        }
        if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("a")) {
            this.setLinkWeight(textWeight);
        }
        this.textWeight = textWeight;
    }

    public void incrementLinkCount() {
        if (this.parent != null) {
            this.parent.incrementLinkCount();
        }
        this.linkCount++;
    }

    public void setLinkWeight(int weight) {
        if (this.parent != null) {
            int difference = weight-this.linkWeight;
            int parentOld = parent.linkWeight;
            parent.setLinkWeight(parentOld+difference);
        }
        this.linkWeight = weight;
    }

    public void addChild(NodeInfo child) {
        this.children.add(child);
        this.setTextWeight(textWeight + child.getTextWeight());
    }

    public String getNodeTitle() {
        String name = this.node.getNodeName();
        if (this.node.getNodeType() == Node.ELEMENT_NODE) {
            if (this.node.hasAttributes()) {
                Node idNode = this.node.getAttributes().getNamedItem("id");
                if (idNode != null) {
                    name += " id=\"" + idNode.getNodeValue() + "\"";
                } else {
                    Node classNode = this.node.getAttributes().getNamedItem("class");
                    if (classNode!= null) {
                        name += " class=\"" + classNode.getNodeValue() + "\"";
                    }
                }
            }
        }
        return name;
    }

    public static NodeInfo buildTreeWithRoot(NodeInfo parent, Node node) {
        NodeInfo root = new NodeInfo(parent, node);
        NodeList children = node.getChildNodes();
        for (int i=0; i<children.getLength(); i++) {
            buildTreeWithRoot(root, children.item(i));
        }
        return root;
    }

    public void printTree() {
        printTree(0);
    }

    public void printTree(int spaceCount) {
        if (this.getTextWeight() > 0) {
            String spaces = "";
            for (int i=0; i<spaceCount; i++) {
                spaces += "|  ";
            }
            System.out.println(spaces + this.getNodeTitle() + 
                    " (" + this.getTextWeight() + " - " + this.getLinkWeight() + " - " + this.getLinkCount() + ")");
            for (NodeInfo child : children) {
                child.printTree(spaceCount + 1);
            }
        }
    }

    public NodeInfo findContentNode() {
        if (this.textWeight > 0) {
            for (NodeInfo child : children) {
                if (child.getTextWeight()*2 > this.getTextWeight()) {
                    return child.findContentNode();
                }
            }
        }
        return this;
    }

    public String getPath() {
        String path = this.getNodeTitle();
        if (this.parent != null) {
            path = this.parent.getPath() + " > " + path;
        }
        return path;
    }

    public String getTextContent() {
        String content = "";
        if (node.getNodeType() == Node.TEXT_NODE) {
            content = node.getNodeValue();
        } if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("img")) {
            if (node.hasAttributes() && node.getAttributes().getNamedItem("alt") != null) {
                content = node.getAttributes().getNamedItem("alt").getNodeValue();
            }
        } else {
            for (NodeInfo child : children) {
                content += " " + child.getTextContent();
            }
        }
        return content;
    }

    public boolean isNavigationNode() {
        if (this.linkCount > 3 && this.linkWeight > 0 && this.linkWeight*2 > this.textWeight) {
            return true;
        } else {
            return false;
        }
    }

    public List<NodeInfo> getNavigationNodes() {
        List<NodeInfo> navigationDivs = new LinkedList<NodeInfo>();
        if (this.linkCount > 3) {
            if (this.isNavigationNode()) {
                navigationDivs.add(this);
            } else {
                for (NodeInfo child : children) {
                    navigationDivs.addAll(child.getNavigationNodes());
                }
            }
        }
        return navigationDivs;
    }
}
