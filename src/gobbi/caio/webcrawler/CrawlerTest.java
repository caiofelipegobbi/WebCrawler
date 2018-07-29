package gobbi.caio.webcrawler;

public class CrawlerTest {
	
	public static void main(String args[]) {
		Crawler c = new Crawler();
		//URL and the key to be searched
		c.search("https://olhardigital.com.br/", "waze");
	}

}
