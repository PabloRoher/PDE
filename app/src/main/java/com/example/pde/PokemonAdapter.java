package com.example.pde;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;

//El adaptador personalizado para manejar la visualizacion de los Pokemons en un RecyclerView.
public class PokemonAdapter extends RecyclerView.Adapter<PokemonViewHolder>{

    private List<Pokemon> pokemonList;
    private Context context;

    //Constructor del adaptador.
    public PokemonAdapter(Context context, List<Pokemon> pokemonList) {
        this.context = context;
        this.pokemonList = pokemonList;
    }

    //Crea nuevas vistas (invocado por el layout manager del RecyclerView).
    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inicia la vista desde el XML y crea el ViewHolder.
        View view = LayoutInflater.from(context).inflate(R.layout.pokemon_item, parent, false);
        return new PokemonViewHolder(view);
    }

    //Reemplaza el contenido de una vista.
    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        Pokemon pokemon = pokemonList.get(position);
        //Asigna los valores del Pokemon a las vistas del ViewHolder.
        holder.pokemonName.setText(pokemon.getName());

        //Obtiene la URL de la imagen del sprite del Pokémon.
        String imageUrl = pokemon.getSprites().getFront_default();

        //Usa la biblioteca Glide para cargar la imagen desde la URL y mostrarla en la vista de imagen.
        Glide.with(context)
                .load(imageUrl)
                .into(holder.pokemonImage);

        //Establece un onClickListener para la vista del item, que inicia una nueva actividad con detalles del Pokemon.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PokemonDetailsActivity.class);
                intent.putExtra("pokemon_data", new Gson().toJson(pokemon));
                context.startActivity(intent);
            }
        });

    }

    //Metodo para actualizar la lista de Pokémon y notificar al adaptador que los datos han cambiado.
    public void updatePokemonList(List<Pokemon> newPokemonList) {
        pokemonList.clear();
        pokemonList.addAll(newPokemonList);
        notifyDataSetChanged();
    }

    //Metodo que devuelve el tamaño de la lista de Pokémon
    @Override
    public int getItemCount() {
        return pokemonList.size();
    }
}
