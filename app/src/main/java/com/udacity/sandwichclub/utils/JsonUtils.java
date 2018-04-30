package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class JsonUtils {

    /**
     * Parses a JSON string into a {@code Sandwich} object
     *
     * @param json JSON string to parse
     * @return parsed out Sandwich object
     * @throws JSONException let the client know about parsing problems explicitly
     */
    public static Sandwich parseSandwichJson(String json) throws JSONException {
        Sandwich sandwich = new Sandwich();

        JSONObject object = new JSONObject(json);
        sandwich.setDescription(object.getString("description"));
        sandwich.setImage(object.getString("image"));
        // name is on object with two members
        JSONObject nameObject = object.getJSONObject("name");
        sandwich.setMainName(nameObject.getString("mainName"));
        sandwich.setAlsoKnownAs(arrayToList(nameObject.getJSONArray("alsoKnownAs")));

        sandwich.setPlaceOfOrigin(object.getString("placeOfOrigin"));
        sandwich.setIngredients(arrayToList(object.getJSONArray("ingredients")));

        return sandwich;
    }

    // convert JSON array to a list of strings
    private static List<String> arrayToList(JSONArray array) throws JSONException {
        List<String> list = new LinkedList<>();
        for (int i = 0; i < array.length(); i++)
            list.add(array.getString(i));

        return list;
    }
}
