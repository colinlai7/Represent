package com.example.colin.a160prog2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


import com.squareup.picasso.Picasso;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {

    public Context context;

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;

        TextView pName;
        TextView pParty;
        TextView pType;
        ImageView pPhoto;
        Button pButton;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            pName = (TextView)itemView.findViewById(R.id.pName);
            pParty = (TextView)itemView.findViewById(R.id.pParty);
            pType = (TextView)itemView.findViewById(R.id.pType);
            pPhoto = (ImageView)itemView.findViewById(R.id.pPhoto);
            pButton = (Button)itemView.findViewById(R.id.pButton);
        }
    }

    List<Person> persons;

    RVAdapter(List<Person> persons, Context context){
        this.persons = persons;
        this.context = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }


    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        final String name = persons.get(i).name;
        final String type = persons.get(i).type;
        final String party = persons.get(i).party;
        final String url = persons.get(i).url;
        final String email = persons.get(i).email;
        final String website = persons.get(i).website;
        final String phone = persons.get(i).phone;
        personViewHolder.pName.setText(name);
        personViewHolder.pType.setText(type);
        personViewHolder.pParty.setText(party);
        if (persons.get(i).party.equals("Democrat")){
            personViewHolder.pParty.setTextColor(Color.BLUE);
        } else if (persons.get(i).party.equals("Republican")){
            personViewHolder.pParty.setTextColor(Color.RED);
        } else {
            personViewHolder.pParty.setTextColor(Color.GREEN);
        }
        // person variables

        personViewHolder.pButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(context, ThirdActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("type", type);
                intent.putExtra("party", party);
                intent.putExtra("url", url);
                intent.putExtra("email", email);
                intent.putExtra("website", website);
                intent.putExtra("phone", phone);
                context.startActivity(intent);
            }
        });
        Picasso.get().load(persons.get(i).url).into(personViewHolder.pPhoto);
//        personViewHolder.personPhoto.setImageResource(persons.get(i).photoId);
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }
}