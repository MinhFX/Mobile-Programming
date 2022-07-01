package funix.minhtk.techcrunchnews;

import java.util.ArrayList;
import java.util.List;

import funix.minhtk.techcrunchnews.model.Article;

public class NewsStore {
    private static List<Article> newsArticles = new ArrayList<>();

    public static List<Article> getNewsArticles() {
        return newsArticles;
    }

    public static void setNewsArticles(List<Article> newsArticles) {
        NewsStore.newsArticles = newsArticles;
    }
}
