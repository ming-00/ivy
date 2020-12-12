import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Web scraper class that extracts information on daily trending repositories on Github,
 * grouped by programming language.
 * For each repository, extract the following information:
 * 1. Github repo name
 * 2. Number of Stars
 * 3. Programming Language
 */
public class FetchTrendingRepos {

    public static void main (String[] args) {

        try {
            List<String[]> data = extractData();

            // Group data according to programming language by sorting ArrayList elements
            data.sort(Comparator.comparing(strArr -> strArr[2]));

            writeToCSV(data);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Extracts relevant data from trending page and stores them in an ArrayList.
     *
     * @return             an ArrayList of string arrays with each string array containing
     *                     a repository name and the corresponding number of stars and
     *                     programming language.
     * @throws IOException if an input or output exception occurred
     */
    public static List<String[]> extractData() throws IOException {
        List<String[]> data = new ArrayList<>();

        // fetch website using JSoup
        Document doc = Jsoup
                .connect("https://github.com/trending?since=weekly%5D")
                .userAgent("mozilla/17.0").get();
        Elements temp = doc.select("article.Box-row");

        for (Element repository:temp) {
            // Obtain string containing username, repository name, number of stars and forks
            String info = repository.getElementsByTag("a").text();
            String[] arrInfo = info.split(" ");
            int indexOfRepoName = Arrays.asList(arrInfo).indexOf("/") + 1;

            String repoName = info.split(" ")[indexOfRepoName];
            String numOfStars = info.split(" ")[indexOfRepoName + 1].replaceAll(",", "");
            String language = repository.select("span.ml-0").text();

            data.add(new String[]{repoName, numOfStars, language});
        }
        return data;
    }

    /**
     * Outputs extracted data into a CSV file.
     *
     * @throws IOException if an input or output exception occurred
     */
    public static void writeToCSV(List<String[]> data) throws IOException {
        File csvOutputFile = new File("TrendingRepos.csv");

        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            pw.println("Github repo name, Number of stars, Programming language");
            data.stream()
                    .map(str -> String.join(",", str))
                    .forEach(pw::println);
        }
        assert csvOutputFile.exists();
    }
}