package group.fishtech.webcrawler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Crawler {
	//Constant to define the max amount of pages to search
	private static final int MAX_PAGES_TO_SEARCH = 100;
	
	/*pagesVisited is a Set because a Set does not contain duplicates, meaning that at 
	 * least all urls visited will be unique.*/
	private Set<String> pagesVisited = new HashSet<String>();
	
	/*Just a list to store all the pages already visited.*/
	private List<String> pagesToVisit = new LinkedList<String>();
	
	public void search(String url, String searchWord) {
		while(this.pagesVisited.size() < MAX_PAGES_TO_SEARCH) {
			
			String currentUrl;
			CrawlerLeg leg = new CrawlerLeg();
			if(this.pagesToVisit.isEmpty()) {
				currentUrl = url;
				this.pagesVisited.add(url);
			}else {
				currentUrl = this.nextUrl();
			}

			//Take a look inside of Crawler leg for more details
			try {
				leg.crawl(currentUrl);
			} catch (Exception e) {
				e.printStackTrace();
			}
			

			boolean success = leg.searchForKey(searchWord);
			if(success) {
				System.out.println(String.format("**Success!!!** key '%s' found at: \n%s", searchWord, currentUrl));
				break;
			}

			this.pagesToVisit.addAll(leg.getLinks());
			System.out.println("Total pages visited: " + pagesVisited.size());
			System.out.println("Total amount of links remaining to visit: " + this.pagesToVisit.size());
			System.out.println("\n ========= // ========= // ========= // =========");
		}
		
		System.out.println(String.format("\nTotal amount of visited %s web page(s)", this.pagesVisited.size()));
		showLinksToVisit();
	}
	
	public void showLinksToVisit() {
		System.out.println("\n\n" + this.pagesToVisit.size() + " pages haven't been visited.\n");
		
		/*for(String link : this.pagesToVisit) {
			System.out.println(link);
		}*/
	}

	
	/**
	 * This method will remove the first item of the pagesToVisit list
	 * and set it as the next url to visit, and will continue to do that until
	 * a link that hasnt been visited is found, then this link will be added to
	 * our list of pages visited. 
	 * 
	 * @return
	 */
	private String nextUrl() {
		String nextUrl;
		do {
			nextUrl = this.pagesToVisit.remove(0);
		}while(this.pagesVisited.contains(nextUrl));
		this.pagesVisited.add(nextUrl);
		return nextUrl;
	}

}
