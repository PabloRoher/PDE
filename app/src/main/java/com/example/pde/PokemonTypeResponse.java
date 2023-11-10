package com.example.pde;

import java.util.List;

    //Clase para manejar la respuesta de la API.
    public class PokemonTypeResponse {
        private List<Type> results;

        public List<Type> getResults() {
            return results;
        }

        public void setResults(List<Type> results) {
            this.results = results;
        }
    }