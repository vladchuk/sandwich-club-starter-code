package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError(getDefaultError());
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError(getDefaultError());
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (Exception e) {
            // handle error gracefully
            closeOnError(e.getMessage());
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError(String error) {
        finish();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    private String getDefaultError() {
        return getString(R.string.detail_error_message);
    }

    private void populateUI(Sandwich sandwich) {
        TextView aka = findViewById(R.id.also_known_tv);
        aka.setText(listToText(sandwich.getAlsoKnownAs()));

        TextView desc = findViewById(R.id.description_tv);
        desc.setText(sandwich.getDescription());

        TextView ingr = findViewById(R.id.ingredients_tv);
        ingr.setText(listToText(sandwich.getIngredients()));

        TextView origin = findViewById(R.id.origin_tv);
        origin.setText(sandwich.getPlaceOfOrigin() + "\n");
    }

    private String listToText(List<String> list) {
        StringBuilder buff = new StringBuilder();
        for (int i = 0; i < list.size(); i++)
            buff.append("\u2022 ").append(list.get(i)).append("\n");

        return buff.toString();
    }
}
