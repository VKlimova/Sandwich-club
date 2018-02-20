package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException{

        JSONObject jsonObj = new JSONObject(json);
        Log.i("SANDLOG", "jsonObj =" + jsonObj);

        JSONObject jsonNameObj = jsonObj.getJSONObject("name");
        Log.i("SANDLOG", "jsonNameObj =" + jsonNameObj);

        ArrayList aka = new ArrayList();
        JSONArray jsonAka = jsonNameObj.getJSONArray("alsoKnownAs");

        Log.i("SANDLOG", "jsonAka =" + jsonAka);
        for (int i=0; i<jsonAka.length(); i++){
            aka.add(jsonAka.get(i));
        }
/////
        ArrayList ingredients = new ArrayList();
        JSONArray jsonIngr = jsonObj.getJSONArray("ingredients");

        Log.i("SANDLOG", "jsonIngr =" + jsonIngr);

        for (int i=0; i<jsonIngr.length(); i++){
            Log.i("SANDLOG", "jsonIngr " + i + " =" + jsonIngr.get(i));
            ingredients.add(jsonIngr.get(i));
        }


    //Sandwich(String mainName, List<String> alsoKnownAs, String placeOfOrigin, String description, String image, List<String> ingredients)
        Sandwich thisSandwich = new Sandwich( jsonNameObj.getString("mainName"),
                aka ,
                jsonObj.getString("placeOfOrigin"),
                jsonObj.getString("description"),
                jsonObj.getString("image"),
                ingredients);

        return thisSandwich;
    }
}
