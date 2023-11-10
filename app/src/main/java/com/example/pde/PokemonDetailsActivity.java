package com.example.pde;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PokemonDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_details);

        //Obtiene el JSON del Intent.
        String pokemonJson = getIntent().getStringExtra("pokemon_data");
        Pokemon pokemon = new Gson().fromJson(pokemonJson, Pokemon.class);

        //Configurar las vistas con la informacion del Pokémon
        setPokemonDataToViews(pokemon);
    }

    private void setPokemonDataToViews(Pokemon pokemon) {
        //Cargar el sprite normal
        ImageView normalImageView = findViewById(R.id.pokemonNormalImageView);
        Glide.with(this).load(pokemon.getSprites().getFront_default()).into(normalImageView);

        //Cargar el sprite shiny
        ImageView shinyImageView = findViewById(R.id.pokemonShinyImageView);
        Glide.with(this).load(pokemon.getSprites().getFront_shiny()).into(shinyImageView);

        Log.d("PokemonDetails", "Name: " + pokemon.getName());
        Log.d("PokemonDetails", "Height: " + pokemon.getHeight());
        Log.d("PokemonDetails", "Weight: " + pokemon.getWeight());


        //Establecer el nombre.
        TextView nameTextView = findViewById(R.id.pokemonNameTextView);
        nameTextView.setText(pokemon.getName());

        //Establecer la altura.
        TextView heightTextView = findViewById(R.id.pokemonHeightTextView);
        heightTextView.setText(String.format(Locale.getDefault(), "Height: %.2f m", pokemon.getHeight() / 10.0));

        //Establecer el peso.
        TextView weightTextView = findViewById(R.id.pokemonWeightTextView);
        weightTextView.setText(String.format(Locale.getDefault(), "Weight: %.2f kg", pokemon.getWeight() / 10.0));

        //Establecer los tipos.
        TextView typeTextView = findViewById(R.id.pokemonTypeTextView);
        List<String> typeNames = new ArrayList<>();
        for (PokemonType pokemonType : pokemon.getTypes()) {
            typeNames.add(pokemonType.getType().getName());
        }
        typeTextView.setText("Types: " + TextUtils.join(", ", typeNames));
    }
}

