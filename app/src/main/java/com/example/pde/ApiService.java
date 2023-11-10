package com.example.pde;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

// La interfaz ApiService define los métodos de llamada HTTP que Retrofit utilizará para comunicarse con la API de Pokémon.
public interface ApiService {
    @GET("pokemon")
    Call<PokemonResponse> getPokemonList();

    @GET("pokemon/{name}")
    Call<Pokemon> getPokemonByName(@Path("name") String name);

    @GET
    Call<PokemonResponse> getPokemonListByUrl(@Url String url);

    @GET("type")
    Call<PokemonTypeResponse> getPokemonTypes();

    @GET("type/{id}")
    Call<TypePokemonResponse> getPokemonByType(@Path("id") String typeId);
}
