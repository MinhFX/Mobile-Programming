package funix.minhtk.techcrunchnews.networking;

import funix.minhtk.techcrunchnews.model.GetArticlesResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class NewsAPI {
    private static final String APIKEY = "59562c0d0a8140f4b4a7667f95b3ed1a";
    private static final String APIPATH = "https://newsapi.org/v1/";

    private static NewsService newsService = null;

    public static NewsService getApi() {
        if (newsService == null) {
            // Initialize NewsService
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(APIPATH)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            newsService = retrofit.create(NewsService.class);
        }

        return newsService;
    }

    public interface NewsService {
        @GET("articles?apiKey=" + APIKEY)
        Call<GetArticlesResponse> getArticles(@Query("source") String source, @Query("sortBy") String sortBy);
    }
}
