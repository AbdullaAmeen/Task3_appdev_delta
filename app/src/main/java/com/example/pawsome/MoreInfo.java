package com.example.pawsome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class MoreInfo extends AppCompatActivity {
    TextView tv_nameDog, tv_origin, tv_lifespan, tv_temperament,tv_height,tv_weight;
    ImageView iv_dog;
    Breeds breed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);

        tv_height = findViewById(R.id.tv_height);
        tv_lifespan = findViewById(R.id.tv_lifespan);
        tv_nameDog = findViewById(R.id.tv_nameDog);
        tv_origin = findViewById(R.id.tv_origin);
        tv_temperament = findViewById(R.id.tv_temperament);
        tv_weight = findViewById(R.id.tv_weight);
        iv_dog = findViewById(R.id.iv_dog);

        setTextAndImage();
    }

    private void setTextAndImage() {
        Intent intent = getIntent();
        breed = intent.getParcelableExtra("dogData");
        String url = intent.getStringExtra("dogImage");

        tv_weight.setText("Weight: Metric- "+ breed.getWeight().getMetric() + " Imperial- "+ breed.getWeight().getImperial() );
        tv_height.setText("Height: Metric- "+ breed.getHeight().getMetric() + " Imperial- "+ breed.getHeight().getImperial() );
        tv_temperament.setText("Temperament: "+ breed.getTemperament());
        tv_origin.setText("Bred For: "+breed.getBred_for());
        tv_nameDog.setText("Name: "+breed.getName());
        tv_lifespan.setText("Lifespan: "+breed.getLife_span());

        Picasso.get().load(url).into(iv_dog);
    }
}