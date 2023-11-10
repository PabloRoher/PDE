package com.example.pde;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

//Adaptador personalizado para mostrar objetos PokemonType en un Spinner.
public class PokemonTypeAdapter extends ArrayAdapter<PokemonType> {

    //Constructor que inicializa el adaptador
    public PokemonTypeAdapter(Context context, List<PokemonType> pokemonTypes) {
        super(context, android.R.layout.simple_spinner_item, pokemonTypes);
    }

    //Metodo para personalizar la vista utilizada para mostrar los elementos en el Spinner
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_item, parent, false);
        }
        //Encuentra el TextView en la vista y establece el texto del tipo de Pokemon cuando el spinner no esta desplegado.
        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        PokemonType item = getItem(position);
        if (item != null && item.getType() != null) {
            textView.setText(item.getType().getName());
            Log.d("PokemonTypeAdapter", "Tipo en getView: " + item.getType().getName());
        }
        return convertView;
    }

    //Metodo para personalizar la vista utilizada en el menu desplegable del Spinner.
    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }
        //Encuentra el TextView en la vista desplegable y establece el texto del tipo de Pokemon.
        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        PokemonType item = getItem(position);
        if (item != null && item.getType() != null) {
            textView.setText(item.getType().getName());
        }
        return convertView;
    }
}
