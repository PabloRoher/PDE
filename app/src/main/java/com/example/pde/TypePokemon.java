package com.example.pde;

//Clase para representar los tipos de pokemons cuando se seleccionen en el spinner.
public class TypePokemon {
    private Pokemon pokemon;
    private int slot;

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }
}
