/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automatedwebwrapper.WebCrawler.UtilityClasses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicReference;

/**
 * M. H. Nassabi
 * @author s098828
 */
public class URLExtractor {

       public static boolean checkContentType(URL url){
            try{
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setFollowRedirects(true);
                urlConnection.setConnectTimeout( 10000 );
                urlConnection.setReadTimeout( 10000 );
                urlConnection.setInstanceFollowRedirects( true );
                urlConnection.setRequestProperty( "User-agent", "spider" );
                urlConnection.connect();

                urlConnection.getHeaderFields();
                String content = urlConnection.getContentType();

                if (content.contains("html") || content == null)
                    return true;
                else
                    return false;
                }

        catch (Exception ex){
            System.err.println("An error Occured in URL Extractor => " + ex.toString());
            return false;
        }
       }
        
       public static void  getHttpPage(URL url, Writer writer, AtomicReference<Object> hostName)
                { 

            try{
                 HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setFollowRedirects(true);
                urlConnection.setConnectTimeout( 10000 );
                urlConnection.setReadTimeout( 10000 );
                urlConnection.setInstanceFollowRedirects( true );
                urlConnection.setRequestProperty( "User-agent", "spider" );
                urlConnection.connect();

                urlConnection.getHeaderFields();
                String content = urlConnection.getContentType();



                if (content.contains("html") || content == null){


                URL respondedHostName = urlConnection.getURL();
                hostName.set(respondedHostName);

               BufferedReader in = new BufferedReader( new InputStreamReader( urlConnection.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    writer.write(inputLine);
                    in.close();

                }
                else{
                    hostName.set(null);
                    
                }
                    
        
           
           



           
            }
           catch (Exception ex){
               System.err.println("An error Occured => " + ex.toString());
            
           }

        }


        public static String getURL(URL url, AtomicReference<Object> hostName)
		throws IOException {
		StringWriter sw = new StringWriter();
		getHttpPage(url, sw, hostName);
                    
            	return sw.toString();
	}



    public static Vector extractLinks(String rawPage, String page) {
		int index = 0;
		Vector links = new Vector();
		while ((index = page.indexOf("<a ", index)) != -1)
		{
		    if ((index = page.indexOf("href", index)) == -1) break;
		    if ((index = page.indexOf("=", index)) == -1) break;
		    String remaining = rawPage.substring(++index);
		    StringTokenizer st = new StringTokenizer(remaining, "\t\n\r\"'>");
				//= new StringTokenizer(remaining, "\t\n\r\"'>#");
                            
		    String strLink = st.nextToken();
			if (strLink.startsWith("#")){
                            index++;
                            continue;
                        }

                            
                        if (! links.contains(strLink)) links.add(strLink);
		}
		return links;
    }




}
