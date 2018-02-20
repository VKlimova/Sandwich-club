package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

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
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        try {
            Sandwich sandwich = JsonUtils.parseSandwichJson(json);
            if (sandwich == null) {
                // Sandwich data unavailable
                closeOnError();
                return;
            }

            populateUI(sandwich);
            Picasso.with(this)
                    .load(sandwich.getImage())
                    .into(ingredientsIv);

            setTitle(sandwich.getMainName());
        }
        catch (Exception e){
            Log.i("SANDLOG",  "Exception = " + e.toString());
        }

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        TextView originTv = findViewById(R.id.origin_tv);
        TextView originLbl = findViewById(R.id.originLbl);

        originTv.setText(sandwich.getPlaceOfOrigin()+"\n");
        if (originTv.length()<=1)
        {
            originTv.setVisibility(View.GONE);
            originLbl.setVisibility(View.GONE);

        }


        TextView akaTv = findViewById(R.id.also_known_tv);
        TextView akaLbl = findViewById(R.id.akaLbl);
        for (String aka : sandwich.getAlsoKnownAs()) {
            akaTv.append(aka + "\n");
        }
        if (akaTv.length()==0){
            akaTv.setVisibility(View.GONE);
            akaLbl.setVisibility(View.GONE);
        }


        TextView ingrTv = findViewById(R.id.ingredients_tv);
        for (String ingr : sandwich.getIngredients()) {
            ingrTv.append(ingr + "\n");
        }

        TextView descrTv = findViewById(R.id.description_tv);
        descrTv.setText(sandwich.getDescription());

    }
}
