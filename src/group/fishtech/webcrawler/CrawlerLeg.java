package group.fishtech.webcrawler;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlerLeg {
	private List<String> links = new LinkedList<String>();
	private Document htmlDocument; //Webpage that will be visited
	// We'll use a fake USER_AGENT so the web server thinks the robot is a normal web browser.
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	
    public List<String> getLinks(){
    	return this.links;
    }
    
    
    /**
     * a[href] is used to specify all urls on the page, and 
     * we also use absUrl to store the absolute url to our list of urls.
     * @param url
     */
	public void crawl(String url) {
		try {
			Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
			Document htmlDocument = connection.get();
			this.htmlDocument = htmlDocument;
			
			//System.out.println("Received web page at " + url);
			
			Elements linksOnPage = htmlDocument.select("a[href]");
			//System.out.println("Found (" + linksOnPage.size() + ") links");
			
			System.out.println("Received web page at: " + url + " \nFound (" + linksOnPage.size() + ") links on this page.");
			for(Element link : linksOnPage) {
				this.links.add(link.absUrl("href"));
			}
			
		} catch (IOException ioe) {
			// HTTP Request was not successful
			System.out.println("There was an error in our HTTP Request: " + ioe);
		}
		
	}
	
	public boolean searchForKey(String searchKey) {
		try {
			System.out.println("Searching for key: " + searchKey);
			String bodyText = this.htmlDocument.body().text();
			return bodyText.toLowerCase().contains(searchKey.toLowerCase());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	

}
