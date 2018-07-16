package com.example.sulekha.assignment_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.example.sulekha.assignment_2.network.ApIService;
import com.example.sulekha.assignment_2.network.ApiClient;
import com.example.sulekha.assignment_2.network.model.ArtistInfo;
import com.example.sulekha.assignment_2.network.model.Result;
import com.example.sulekha.assignment_2.view.ArtistItemAdapter;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import retrofit2.Callback;
import retrofit2.Call;
import retrofit2.Response;
import io.realm.Realm;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Call<ArtistInfo> artistRockCall;
    Call<ArtistInfo> artistClassicCall;
    Call<ArtistInfo> artistPopCall;

    ArtistInfo rockInfo;
    ArtistInfo classicInfo;
    ArtistInfo popInfo;

    private RecyclerView recyclerView;

    private Button rockButton;
    private Button classicButton;
    private Button popButton;
    private ImageView artistImage;

    private Realm realm;
    List<Result> listValues;


    private void loadFromRealm(){
        Realm realm = Realm.getDefaultInstance();
        List<Result> values = realm.where(Result.class).findAll();
        listValues = realm.copyFromRealm(values);
        realm.close();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(configuration);

        recyclerView = findViewById(R.id.recyclerView);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
       recyclerView.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));


        ApIService apIService = ApiClient.getClient(this).create(ApIService.class);

        recyclerView = findViewById(R.id.recyclerView);

        artistRockCall = apIService.getRock();
        artistClassicCall = apIService.getClassic();
        artistPopCall = apIService.getPop();

        rockButton = findViewById(R.id.button);
        classicButton = findViewById(R.id.button2);
        popButton = findViewById(R.id.button3);
        artistImage = findViewById(R.id.artistimg);

        rockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                artistRockCall.clone().enqueue(new Callback<ArtistInfo>() {
                    @Override
                    public void onResponse(Call<ArtistInfo> call, Response<ArtistInfo> response) {
                        rockInfo = response.body();


                        realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        realm.copyToRealmOrUpdate(rockInfo.getResults());
                        realm.commitTransaction();
                        realm.close();


                        ArtistItemAdapter artistItemAdapter = new ArtistItemAdapter(rockInfo.getResults(), getBaseContext());
                        recyclerView.setAdapter(artistItemAdapter);

                        Snackbar.make(rockButton, "Found 50 Results", Snackbar.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailure(Call<ArtistInfo> call, Throwable t) {
                        loadFromRealm();
                        ArtistItemAdapter artistItemAdapter = new ArtistItemAdapter(listValues, getBaseContext());
                        recyclerView.setAdapter(artistItemAdapter);
                        Log.e("assi2", "failed");
                        Snackbar.make(rockButton, "Loaded from Realm", Snackbar.LENGTH_LONG).show();

                    }
                });
            }
        });

        rockButton.performClick();

        classicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                artistClassicCall.clone().enqueue(new Callback<ArtistInfo>() {
                    @Override
                    public void onResponse(Call<ArtistInfo> call, Response<ArtistInfo> response) {
                        classicInfo = response.body();
                        ArtistItemAdapter artistItemAdapter = new ArtistItemAdapter(classicInfo.getResults(), getBaseContext());
                        recyclerView.setAdapter(artistItemAdapter);
                        Snackbar.make(rockButton, "Found 50 Results", Snackbar.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailure(Call<ArtistInfo> call, Throwable t) {

                    }
                });

            }
        });

        popButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                artistPopCall.clone().enqueue(new Callback<ArtistInfo>() {
                    @Override
                    public void onResponse(Call<ArtistInfo> call, Response<ArtistInfo> response) {
                        popInfo = response.body();
                        ArtistItemAdapter artistItemAdapter = new ArtistItemAdapter(popInfo.getResults(), getBaseContext());
                        recyclerView.setAdapter(artistItemAdapter);
                        Snackbar.make(rockButton, "Found 50 Results", Snackbar.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailure(Call<ArtistInfo> call, Throwable t) {

                    }
                });

            }
        });

    }
}
