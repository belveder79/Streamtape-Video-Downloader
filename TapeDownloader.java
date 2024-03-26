import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import java.lang.String;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Objects;

public class TapeDownloader {
  private static String SteamtapeGetDlLink(String link) {
    try {
      if (link.contains("/e/"))
        link = link.replace("/e/", "/v/");
      Document doc = Jsoup.connect(link).get();
      String htmlSource = doc.html();
      Pattern norobotLinkPattern = Pattern.compile("document\\.getElementById\\('norobotlink'\\)\\.innerHTML = (.+);");
      Matcher norobotLinkMatcher = norobotLinkPattern.matcher(htmlSource);
      if (norobotLinkMatcher.find()) {
        String norobotLinkContent = norobotLinkMatcher.group(1);
        Pattern tokenPattern = Pattern.compile("token=([^&']+)");
        Matcher tokenMatcher = tokenPattern.matcher(norobotLinkContent);
        if (tokenMatcher.find()) {
          String token = tokenMatcher.group(1);
          Elements divElements = doc.select("div#ideoooolink[style=display:none;]");
          if (!divElements.isEmpty()) {
            String streamtape = ((Element)Objects.<Element>requireNonNull(divElements.first())).text();
            String fullUrl = "https:/" + streamtape + "&token=" + token;
            return fullUrl + "&dl=1s";
          }
        }
      }
    } 
    catch (Exception exception) 
    {

    }
    return null;
  }

  public static void main(String args[]) {
    String val = SteamtapeGetDlLink("https://streamtape.com/e/3A78kddZgztdO0v");
    System.out.println("Link is: " + val);
  }
}
