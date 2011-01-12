<%-- 
    Document   : crawl
    Created on : Jan 12, 2011, 6:04:24 PM
    Author     : murat
--%>

<%@page import="java.util.Set" %>
<%@page import="java.util.List" %>

<%@page import="automatedwebwrapper.cluster.MultidepthUrlClusterer" %>
<%@page import="automatedwebwrapper.cluster.ClusterAnalyzer" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Website Page Clusterer</title>
    </head>
    <body>
        <p>
            <a href="index.jsp">[Template Extractor]</a> -
            <a href="crawl.jsp">[Web Site Crawler and Clusterer]</a>
        </p>
        <h1>Crawl and Cluster a Web Site</h1>
        <form action="crawl.jsp" method="get">
            <p>URL:
                <input type="text" name="url" value="<%
                if (request.getParameter("url") == null) {
                    out.print("http://www.");
                } else {
                    out.print(request.getParameter("url"));
                }
                %>" /><br />
                Crawl at most
                <select name="pagesToCrawl">
                    <option value="10">10</option>
                    <option value="50">50</option>
                    <option value="100">100</option>
                    <option value="200">200</option>
                    <option value="500">500</option>
                    <option value="1000">1000</option>
                    <option value="-1">unlimited</option>
                </select> pages <br />
                <input type="submit" name="submit" value="Submit" />
            </p>
        </form>
        <%
        if (request.getParameter("url") != null) {
            automatedwebwrapper.WebCrawler.Main exMain = new automatedwebwrapper.WebCrawler.Main();
            %><p>Crawling started...</p><%
            out.flush();
            Set<String> processedURLs = exMain.startCrawl(request.getParameter("url"), Integer.parseInt(request.getParameter("pagesToCrawl"))); //This is the set of all Processed URLS crawled by the crawler

            %><p>Crawling finised. Clustering...</p><%

            MultidepthUrlClusterer clusterer = new MultidepthUrlClusterer(processedURLs);
            List<List<String>> clusters = clusterer.getClusters();

            for (List<String> cluster : clusters) {
                %><h2>Cluster</h2><ul><%
                System.out.println("Cluster (" + cluster.size() + ")");
                for(String url : cluster) {
                    %><li><a href="<%=url%>"><%=url%></a> <a href="index.jsp?url=<%= java.net.URLEncoder.encode(url, "utf-8") %>">Extract Template</a></li><%
                }
                %></ul><%
                out.flush();

                ClusterAnalyzer analyzer = new ClusterAnalyzer(cluster);

                %><p><%= analyzer.analyzeCluster().replaceAll("\n", "<br />") %></p>
                <%
                out.flush();
            }
        }
        %>
    </body>
</html>
