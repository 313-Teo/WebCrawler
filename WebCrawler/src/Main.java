import java.util.ArrayList;
import java.util.Scanner;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;

public class Main{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserisci il seed URL: ");
        String url = scanner.nextLine();
        scanner.close();

        ArrayList<String> viewed = new ArrayList<>();
        int maxlevel = 3;

        crawl(1, maxlevel, url, viewed);
    }

    public static void crawl(int level, int maxlevel, String url, ArrayList<String> viewed){
        if (level <= maxlevel){
            try{
                Connection connection = Jsoup.connect(url);
                Document document = connection.get();

                if(connection.response().statusCode() == 200){
                    System.out.println("Link: " + url);
                    System.out.println("Title: " + document.title());
                    viewed.add(url);
                }

                if(document != null){
                    for(Element link : document.select("a[href]")) {
                        String nextlink = link.absUrl("href");
                        if(!viewed.contains(nextlink)){
                            crawl(level++, maxlevel, nextlink, viewed);
                        }
                    }
                }
            }
            catch(IOException e){
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}