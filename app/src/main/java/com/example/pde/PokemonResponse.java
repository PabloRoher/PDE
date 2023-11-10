package com.example.pde;

import java.util.List;

public class PokemonResponse {

    //Metodos para manejar la respuesta de la API.
    private int count;
    private String next;
    private String previous;
    private List<Pokemon> results;

    //Metodos getters y setters para manejar la respuesta de la API.
    public List<Pokemon> getResults() {
        return results;
    }

    public void setResults(List<Pokemon> results) {
        this.results = results;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }
}
