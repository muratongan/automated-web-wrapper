<%-- 
    Document   : index
    Created on : Jan 12, 2011, 3:18:46 PM
    Author     : murat
--%>

<%@page import="automatedwebwrapper.tree.TemplateExtractor" %>
<%@page import="automatedwebwrapper.tree.TreeBuilder" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Template Extractor</title>
    </head>
    <body>
        <p>
            <a href="index.jsp">[Template Extractor]</a> -
            <a href="crawl.jsp">[Web Site Crawler and Clusterer]</a>
        </p>
        <h1>Extract the main part of a web page</h1>
        <form action="index.jsp" method="get">
            <p>URL:
                <input type="text" name="url" value="<%
                if (request.getParameter("url") == null) {
                    out.print("http://www.");
                } else {
                    out.print(request.getParameter("url"));
                }
                %>" />
                <input type="submit" name="submit" value="Submit" />
            </p>
        </form>
        <%
        if (request.getParameter("url") != null) {
            String url = request.getParameter("url");
            try {
                TemplateExtractor te = new TemplateExtractor(url);
                String fileName = application.getRealPath("/output.html");
                te.writeHtmlToFile(fileName);
                %>
                <p>Page Type: <%= (te.getTree().isNavigationPage()?"NAVIGATION PAGE":"CONTENT PAGE")%></p>
                <iframe src="output.html" width="100%" height="500">No iframe support</iframe>
                <%
                if (! te.getTree().isNavigationPage()) {
                    %><h2>Main Content</h2><div><textarea cols="80" rows="10"><% out.println(te.getMainContent()); %></textarea></div><%
                }
            } catch (Exception ex) {
                %><p>There is an error handling your request. Please make sure
                    that you give a valid web page URL.</p>
                <p><%= ex.getMessage() %></p>
                <p><%
                        ex.printStackTrace(new java.io.PrintWriter(out));
                %></p><%
            }
        }
        %>
    </body>
</html>
