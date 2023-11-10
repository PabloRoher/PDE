package com.example.pde;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//MainActivity es la actividad principal que se lanza cuando se inicia la aplicación.
public class MainActivity extends AppCompatActivity {

    // Variables para manejar el recyclerView y el adapter.
    private RecyclerView recyclerView;
    private PokemonAdapter adapter;

    //Un spinner que permitira al usuario filtrar los pokemons por tipos.
    private Spinner typeSpinner;

    //Listas para mantener los tipos de Pokémon y la lista actual de Pokémon mostrados.
    private List<PokemonType> pokemonTypes = new ArrayList<>();
    private List<Pokemon> pokemonList;

    //Los botones para avanzar o retroceder en la lista de pokemons.
    private String nextUrl;
    private String prevUrl;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializa el RecyclerView y su adaptador.
        recyclerView = findViewById(R.id.pokemonRecyclerView);
        pokemonList = new ArrayList<>();
        adapter = new PokemonAdapter(this, pokemonList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Carga la lista inicial de Pokémon y los tipos de Pokémon disponibles.
        loadPokemon(null);
        loadPokemonTypes();

        // Establece el funcionamiento de boton para filtrar.
        Button filterButton = findViewById(R.id.filterButton);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Type selectedType = (Type) typeSpinner.getSelectedItem();
                if (selectedType != null) {
                    String url = selectedType.getUrl();
                    // Asumiendo que la URL es del tipo "https://pokeapi.co/api/v2/type/1/"
                    String[] urlParts = url.split("/");
                    String typeId = urlParts[urlParts.length - 1];
                    Log.d("TAG", "onClick: " + typeId);
                    loadPokemonByType(typeId);

                }
            }
        });

        //Configura los botones para avanzar o retroceder en la lista.
        Button nextButton = findViewById(R.id.nextButton);
        typeSpinner = findViewById(R.id.spinner);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nextUrl != null) {
                    loadPokemon(nextUrl);
                }
            }
        });

        Button previousButton = findViewById(R.id.previousButton);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prevUrl != null && !prevUrl.isEmpty()) {
                    loadPokemon(prevUrl);
                } else {
                    Toast.makeText(MainActivity.this, "Esta es la primera página", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Metodo para cargar la lista de Pokemon usando una URL especifica.
    private void loadPokemon(String url) {
        //Crea una instancia del servidor API y realiza una llamada para obtener la lista de pokemons.
        ApiService apiService = ApiClient.getClient();
        Call<PokemonResponse> call = (url == null) ?
                apiService.getPokemonList() :
                apiService.getPokemonListByUrl(url);

        call.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PokemonResponse pokemonResponse = response.body();
                    nextUrl = pokemonResponse.getNext();
                    prevUrl = pokemonResponse.getPrevious();
                    List<Pokemon> pokemonNamesList = response.body().getResults();

                    //Limpia la lista existente y carga la nueva.
                    pokemonList.clear();
                    adapter.notifyDataSetChanged();

                    //Para cada pokemon realiza una llamada adicional para obtener mas detalles.
                    for (Pokemon pokemon : pokemonNamesList) {
                        Call<Pokemon> pokemonCall = apiService.getPokemonByName(pokemon.getName());
                        pokemonCall.enqueue(new Callback<Pokemon>() {
                            @Override
                            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    Pokemon detailedPokemon = response.body();
                                    //Agrega el pokemon con los detalles a la lista y notifica al adaptador.
                                    pokemonList.add(detailedPokemon);
                                    adapter.notifyItemInserted(pokemonList.size() - 1);
                                }
                            }

                            @Override
                            public void onFailure(Call<Pokemon> call, Throwable t) {
                                Log.e("MainActivity", "Error al cargar los detalles del Pokémon", t);
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                Log.e("MainActivity", "Error al cargar la lista de Pokémon", t);
            }
        });
    }

    //Metodo para cargar los Pokemon de un tipo específico por medio de un ID.
    private void loadPokemonByType(String typeId) {
        ApiService apiService = ApiClient.getClient();
        apiService.getPokemonByType(typeId).enqueue(new Callback<TypePokemonResponse>() {
            @Override
            public void onResponse(Call<TypePokemonResponse> call, Response<TypePokemonResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<TypePokemon> typePokemons = response.body().getPokemon();
                    List<Pokemon> detailedPokemonList = new ArrayList<>();

                    for (TypePokemon typePokemon : typePokemons) {
                        //Aqui hace la solicitud para obtener los detalles completos de cada Pokémon
                        apiService.getPokemonByName(typePokemon.getPokemon().getName()).enqueue(new Callback<Pokemon>() {
                            @Override
                            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    detailedPokemonList.add(response.body());
                                    if (detailedPokemonList.size() == typePokemons.size()) {
                                        //Todos los detalles de Pokemon han sido obtenidos
                                        adapter.updatePokemonList(detailedPokemonList);
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Pokemon> call, Throwable t) {
                                Log.e("MainActivity", "Error al obtener detalles del Pokémon", t);
                            }
                        });
                    }
                } else {
                    Log.e("MainActivity", "Error al cargar Pokémon por tipo " + response.code());
                }
            }

            @Override
            public void onFailure(Call<TypePokemonResponse> call, Throwable t) {
                Log.e("MainActivity", "Error al cargar Pokémon por tipo", t);
            }
        });
    }


    //Metodo para cargar los tipos de Pokémon de la API y actualizar el spinner con los datos.
    private void loadPokemonTypes() {
        ApiService apiService = ApiClient.getClient();
        apiService.getPokemonTypes().enqueue(new Callback<PokemonTypeResponse>() {
            @Override
            public void onResponse(Call<PokemonTypeResponse> call, Response<PokemonTypeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    //Obtiene la lista de tipos de la respuesta.
                    List<Type> types = response.body().getResults();
                    Log.d("MainActivity", "Tipos cargados: " + types.size());
                    for (Type type : types) {
                        Log.d("MainActivity", "Tipo: " + type.getName());
                    }
                    // Crea un ArrayAdapter usando la lista de tipos y lo establece como adaptador del spinner.
                    ArrayAdapter<Type> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, types);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    typeSpinner.setAdapter(adapter);
                } else {
                    Log.e("MainActivity", "Error al cargar tipos");
                }
            }

            @Override
            public void onFailure(Call<PokemonTypeResponse> call, Throwable t) {
                Log.e("MainActivity", "Error al cargar los tipos de Pokémon", t);
            }
        });
    }


    //Metodo conectado al boton "Siguiente" en la UI, usado para cargar la siguiente pagina de Pokemons.
    public void onNextClicked(View view) {
        if (nextUrl != null && !nextUrl.isEmpty()) {
            loadPokemon(nextUrl);
        }
    }

    //Metodo conectado al boton "Anterior" en la UI, usado para regresar a la pagina anterior de Pokemons.
    public void onPreviousClicked(View view) {
        if (prevUrl != null && !prevUrl.isEmpty()) {
            loadPokemon(prevUrl);
        }
    }

    //Metodo para mostrar los detalles de un Pokemon específico.
    public void pokemonDetails(Pokemon pokemon) {
        Intent intent = new Intent(MainActivity.this, PokemonDetailsActivity.class);
        intent.putExtra("pokemon_url", "https://pokeapi.co/api/v2/pokemon/" + pokemon.getName());
        startActivity(intent);
    }

}