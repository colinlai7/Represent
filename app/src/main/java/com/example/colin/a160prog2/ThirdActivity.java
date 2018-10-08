package com.example.colin.a160prog2;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        // get intent extras
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String type = intent.getStringExtra("type");
        String party = intent.getStringExtra("party");
        String url = intent.getStringExtra("url");
        String email = intent.getStringExtra("email");
        String website = intent.getStringExtra("website");
        String phone = intent.getStringExtra("phone");
        if (email.equals("null")) {
            email = "None";
        }
        if (website.equals("null")){
            website = "None";
        }
        if (phone.equals("null")){
            phone = "None";
        }

        // check code is correct
        TextView header2 = findViewById(R.id.headerview2);
        header2.setText("Showing details for " + type + " " + name);
//        TextView dType = findViewById(R.id.dType);
//        dType.setText(type);
        TextView dParty = findViewById(R.id.dParty);
        dParty.setText(party);
        if (party.equals("Democrat")){
            dParty.setTextColor(Color.BLUE);
        } else if (party.equals("Republican")){
            dParty.setTextColor(Color.RED);
        } else {
            dParty.setTextColor(Color.GREEN);
        }
        ImageView dUrl = findViewById(R.id.img);
        Picasso.get().load(url).into(dUrl);
        TextView dEmail = findViewById(R.id.dEmail);
        dEmail.setText("Email: " + email);
        TextView dWebsite = findViewById(R.id.dWebsite);
        dWebsite.setText("Website: " + website);
        TextView dPhone = findViewById(R.id.dPhone);
        dPhone.setText("Phone: " + phone);

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

}
