/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automatedwebwrapper.WebCrawler.UtilityClasses;

/**
 *
 * @author murat
 */
public class UrlTokenizer {
    String protocol = "";
    String domain = "";
    String path = "";
    String arguments = "";
    String anchor = "";

    public UrlTokenizer(String url) {
        String[] parts = url.split("://", 2);
        if (parts.length > 1) {
            protocol = parts[0];
            parts = parts[1].split("#", 2);
            if (parts.length > 1) {
                anchor = parts[1];
            }
            parts = parts[0].split("\\?", 2);
            if (parts.length > 1) {
                arguments = parts[1];
            }
            parts = parts[0].split("/", 2);
            if (parts.length > 1) {
                path = parts[1];
            }
            domain = parts[0];
        } else {
            domain = url;
        }
    }

    public void printInfo() {
        System.out.println("  Protocol  : " + protocol);
        System.out.println("  Domain    : " + domain);
        System.out.println("  Path      : " + path);
        System.out.println("  Arguments : " + arguments);
        System.out.println("  Anchor    : " + anchor);
    }

    public String getAnchor() {
        return anchor;
    }

    public String getArguments() {
        return arguments;
    }

    public String getDomain() {
        return domain;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }
}
