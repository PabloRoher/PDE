package com.example.pde;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//Clase que proporciona una referencia a las vistas para cada elemento de datos.
public class PokemonViewHolder extends RecyclerView.ViewHolder {
    TextView pokemonName;
    ImageView pokemonImage;

    public PokemonViewHolder(@NonNull View itemView) {
        super(itemView);
        //Asociacion de las vistas del layout con los campos de la clase.
        pokemonName = itemView.findViewById(R.id.pokemonName);
        pokemonImage = itemView.findViewById(R.id.pokemonImage);

    }
}
