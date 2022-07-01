package funix.minhtk.techcrunchnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import funix.minhtk.techcrunchnews.model.GetArticlesResponse;
import funix.minhtk.techcrunchnews.networking.NewsAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView newsRecyclerView; // Create a RecyclerView
    private CoordinatorLayout coordinatorLayout; // Create a CoordinatorLayout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assign a LayoutManager to the RecyclerView
        newsRecyclerView = (RecyclerView) findViewById(R.id.activity_main_recyclerview);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Implement a CoordinatorLayout
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.activity_main);

        // Make a call to NewsAPI
        Call<GetArticlesResponse> call = NewsAPI.getApi().getArticles("techcrunch", "top");

        call.enqueue(new Callback<GetArticlesResponse>() {
            @Override
            public void onResponse(Call<GetArticlesResponse> call, Response<GetArticlesResponse> response) {
                showNewsApiSnack();
                GetArticlesResponse getArticlesResponse = response.body();
                NewsStore.setNewsArticles(getArticlesResponse.getArticles());
                Toast.makeText(MainActivity.this, "Response received", Toast.LENGTH_SHORT).show();
                // Assign the array adapter to the RecyclerView
                HomeNewsAdapter homeNewsAdapter = new HomeNewsAdapter(getArticlesResponse.getArticles());
                newsRecyclerView.setAdapter(homeNewsAdapter);
            }

            @Override
            public void onFailure(Call<GetArticlesResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error received", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Implement a Snackbar
    private void showNewsApiSnack() {
        Snackbar.make(coordinatorLayout, "Powered by NewsApi.org", Snackbar.LENGTH_LONG)
                .setAction("Visit", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadNewsApiWebsite();
                    }
                }).show();
    }

    // Add an attribution link in the app
    private void loadNewsApiWebsite() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://newsapi.org")));
    }
}