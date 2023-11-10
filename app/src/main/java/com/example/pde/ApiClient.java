package com.example.pde;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// La clase ApiClient es responsable de configurar y proporcionar una instancia de Retrofit.
public class ApiClient {
    // BASE_URL es la URL base de la API de Pokémon que se utilizará para todas las solicitudes.
    private static final String BASE_URL = "https://pokeapi.co/api/v2/";
    // retrofit es una instancia estática de Retrofit. Se utiliza para asegurar
    // que solo exista una instancia de Retrofit durante toda la ejecución de la aplicación.
    private static Retrofit retrofit = null;
    // getClient es un método estático público que otros componentes pueden llamar para obtener una instancia de ApiService.
    public static ApiService getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        // Creamos y devolvemos una implementación de la interfaz ApiService.
        return retrofit.create(ApiService.class);
    }
}
