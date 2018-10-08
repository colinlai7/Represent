package com.example.colin.a160prog2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.squareup.picasso.Picasso;

public class SecondActivity extends AppCompatActivity {

//    public TextView textresult;
    public List<Person> persons;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        persons = new ArrayList<>();
        rv=(RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        Intent intent = getIntent();
        String location = intent.getStringExtra("location");
        String mod = intent.getStringExtra("mod");
        //test header again
//        TextView first = findViewById(R.id.headerview);
//        first.setText("Header before");

        // zip to geocodio
//        textresult = findViewById(R.id.geocodioresult);
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.geocod.io/v1.3/" + mod + "?q=" + location + "&fields=cd&api_key=5637b775679337665b995cbbaeab707673a6b6b";
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                TextView apierror = findViewById(R.id.headerview);
                apierror.setText("API Call error");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    SecondActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            populate(myResponse);
                            initializeAdapter();
                        }
                    });
                }
            }

        });
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void populate(String response) {
        try {
            JSONObject reader = new JSONObject(response);

            JSONArray resultarray = reader.getJSONArray("results");
            JSONObject results = resultarray.getJSONObject(0);
            JSONObject address_components = results.getJSONObject("address_components");

            // city and state values
            String county = address_components.getString("county");
            String state = address_components.getString("state");
            TextView header = findViewById(R.id.headerview);
            header.setText("Showing results for " + county + ", " + state);

            JSONObject fields = results.getJSONObject("fields");
            JSONArray districts = fields.getJSONArray("congressional_districts");
//            // for each district
            int sencount = 0;
            for (int i = 0; i < districts.length(); i++) {
                // get the district
                JSONObject dist = districts.getJSONObject(i);
                // get each rep
                JSONArray legislators = dist.getJSONArray("current_legislators");
                for (int j = 0; j < legislators.length(); j++){
                    JSONObject rep = legislators.getJSONObject(j);
                    // get type
                    String type = rep.getString("type");
                    // check sen count to prevent repeat senators
                    if (type.equals("senator")){
                        if (sencount == 2){
                            continue;
                        }
                        sencount += 1;
                    }
                    String s1 = type.substring(0, 1).toUpperCase();
                    String typeCaps = s1 + type.substring(1);
                    // get name
                    JSONObject bio = rep.getJSONObject("bio");
                    String name = bio.getString("first_name") + " " + bio.getString("last_name");
                    // get party
                    String party = bio.getString("party");
                    JSONObject ref = rep.getJSONObject("references");
                    String id = ref.getString("bioguide_id");
                    String url = "https://theunitedstates.io/images/congress/original/" + id + ".jpg";
//                    String url = "https://theunitedstates.io/images/congress/225x275/" + id + ".jpg";

                    // get contact info
                    JSONObject contact = rep.getJSONObject("contact");
                    String email = contact.getString("contact_form");
                    String website = contact.getString("url");
                    String phone = contact.getString("phone");
                    persons.add(new Person(name, party, typeCaps, url, email, website, phone));

                }
//                persons.add(new Person())
            }

//            textresult.setText(response);

        } catch (org.json.JSONException e) {
            TextView textresult = findViewById(R.id.headerview);
            textresult.setText("Error occurred parsing JSON");
        }
    }


    public void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(persons, this);
        rv.setAdapter(adapter);
    }

}