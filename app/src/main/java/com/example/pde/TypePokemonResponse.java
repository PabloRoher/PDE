package com.example.pde;

import java.util.List;

//Clase para manejar la respuesta de la API
public class TypePokemonResponse {
    private List<TypePokemon> pokemon;

    public List<TypePokemon> getPokemon() {
        return pokemon;
    }

    public void setPokemon(List<TypePokemon> pokemon) {
        this.pokemon = pokemon;
    }
}
