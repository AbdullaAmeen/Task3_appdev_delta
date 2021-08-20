package com.example.pawsome.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pawsome.ApiService;
import com.example.pawsome.Dogs;
import com.example.pawsome.MoreInfo;
import com.example.pawsome.R;
import com.example.pawsome.RecyclerAdapter;
import com.example.pawsome.databinding.FragmentHomeBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment implements RecyclerAdapter.OnCardListener {

    private FragmentHomeBinding binding;
    Activity context;

    RecyclerView rv_dogs;
    private ArrayList<Dogs> dogs;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        context = getActivity();

        Retrofit retrofit =  new Retrofit.Builder().baseUrl("https://api.thedogapi.com/").addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ArrayList<Dogs>> call = apiService.getDogs(10, 5);
        call.enqueue(new Callback<ArrayList<Dogs>>() {
            @Override
            public void onResponse(@NotNull Call<ArrayList<Dogs>> call, @NotNull Response<ArrayList<Dogs>> response) {
                dogs = response.body();
                init_recycler();

            }

            @Override
            public void onFailure(@NotNull Call<ArrayList<Dogs>> call, @NotNull Throwable t) {
                Log.v("Yes","no"+t.toString());
            }
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void init_recycler(){
        rv_dogs = (RecyclerView) context.findViewById(R.id.rv_dogs);
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(context, dogs, this);
        rv_dogs.setAdapter(recyclerAdapter);
        rv_dogs.setLayoutManager(new LinearLayoutManager(context));
    }


    @Override
    public void onCardClick(int position) {

        Dogs dog = dogs.get(position);
        if(dog.getBreeds().isEmpty())
            return;
        Intent itGoToMoreInfo = new Intent(context, MoreInfo.class);
        itGoToMoreInfo.putExtra("dogData", dog.getBreeds().get(0));
        itGoToMoreInfo.putExtra("dogImage", dog.getUrl());
        startActivity(itGoToMoreInfo);

    }
}