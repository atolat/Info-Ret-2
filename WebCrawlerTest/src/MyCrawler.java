import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class MyCrawler extends WebCrawler {

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
                                                           + "|png|mp3|mp3|zip|gz))$");
    public static int i=0;
    
    ArrayList<String> urlList=new ArrayList<>();
    

    /**
     * This method receives two parameters. The first parameter is the page
     * in which we have discovered this new url and the second parameter is
     * the new url. You should implement this function to specify whether
     * the given url should be crawled or not (based on your crawling logic).
     * In this example, we are instructing the crawler to ignore urls that
     * have css, js, git, ... extensions and to only accept urls that start
     * with "http://www.ics.uci.edu/". In this case, we didn't need the
     * referringPage parameter to make the decision.
     */
     @Override
     public boolean shouldVisit(Page referringPage, WebURL url) {
         String href = url.getURL().toLowerCase();
         return !FILTERS.matcher(href).matches()
                && href.startsWith("http://www.ics.uci.edu/");
     }

     /**
      * This function is called when a page is fetched and ready
      * to be processed by your program.
      * 
      * 
		writer.println("The first line");
		
      * 
      * 
      * 
      */
     @Override
     public void visit(Page page) {
         String url = page.getWebURL().getURL();
         urlList.add(url);
         System.out.println("URL: " + url);
         
         
			
		
         if (page.getParseData() instanceof HtmlParseData) {
        	 PrintWriter writer = null;
 			try {
 				writer = new PrintWriter("D:\\crawlerData\\"+i+".txt", "UTF-8");
 			} catch (FileNotFoundException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			} catch (UnsupportedEncodingException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 			
   	 
             HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
             //writer.println(url+"\n");
             String text = htmlParseData.getText();
             writer.println(htmlParseData.getText());
             String html = htmlParseData.getHtml();
             Set<WebURL> links = htmlParseData.getOutgoingUrls();
             System.out.println("Text length: " + text.length());
             System.out.println("Html length: " + html.length());
             System.out.println("Number of outgoing links: " + links.size());
        	 writer.close();
        	 
        	 
             
         }
         try {
			Tokenize t = new Tokenize("D:\\crawlerData\\"+i+".txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
         i++;
         try {
			printURL(urlList);
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
     
     public void printURL(ArrayList<String> urlList2) throws FileNotFoundException, UnsupportedEncodingException{
			PrintWriter writerURL = new PrintWriter("D:\\crawlerDataTokenized\\url.txt", "UTF-8");
			for(String s: urlList2){
				writerURL.println(s);
			}
			writerURL.close();

    	 
     }
     
     
     
}