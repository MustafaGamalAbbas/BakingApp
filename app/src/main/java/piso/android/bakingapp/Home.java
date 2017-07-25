package piso.android.bakingapp;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import piso.android.bakingapp.Adapters.AdapterRecipesList;
import piso.android.bakingapp.Models.Ingredient;
import piso.android.bakingapp.Models.Recipe;
import piso.android.bakingapp.Models.Step;

public class Home extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    RecyclerView mRecyclerView;
    public static List<Recipe> recipes;
    AdapterRecipesList adapterRecipesList;
    TextView net  ;
    AppCompatActivity appthis ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recipeList);
        net = (TextView) findViewById(R.id.tv_home_network);
        appthis =    this;
        recipes = new ArrayList<>();
        if( ConnectivityReceiver.isConnected())
            getRecipesFromJson();
        else
        {
            net.setText(R.string.network_message);
            Typeface tf = Typeface.createFromAsset(getAssets(), "Fonts/Gabriola.ttf");
            net.setTypeface(tf);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);
    }

    private void getRecipesFromJson() {
        Ion.with(this).load("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        for (int i = 0; i < result.size(); i++) {
                            JsonObject object = result.get(i).getAsJsonObject();
                            Recipe recipe = new Recipe();

                            recipe.setImage(object.get("image").getAsString());
                            recipe.setName(object.get("name").getAsString());
                            recipe.setServings(object.get("servings").getAsInt());
                            JsonArray ingredients = object.get("ingredients").getAsJsonArray();
                            List<Ingredient> ings = new ArrayList<>();
                            for (int j = 0; j < ingredients.size(); j++) {
                                JsonObject ingredient = ingredients.get(j).getAsJsonObject();
                                Ingredient in = new Ingredient();
                                in.setQuantity(ingredient.get("quantity").getAsInt());
                                in.setIngredient(ingredient.get("ingredient").getAsString());
                                in.setMaesure(ingredient.get("measure").getAsString());
                                ings.add(in);
                            }
                            recipe.setIngredients(ings);


                            JsonArray steps = object.get("steps").getAsJsonArray();
                            List<Step> stps = new ArrayList<>();
                            for (int k= 0; k < steps.size(); k++) {
                                JsonObject step = steps.get(k).getAsJsonObject();
                                Step in = new Step();
                                in.setShortDescription(step.get("shortDescription").getAsString());
                                in.setDescription(step.get("description").getAsString());
                                in.setVideoURL(step.get("videoURL").getAsString());
                                in.setThumbnailURL(step.get("thumbnailURL").getAsString());
                                stps.add(in);
                            }
                            recipe.setSteps(stps);
                            recipes.add(recipe);
                            Log.v("size" ,String.valueOf(recipe.getSteps().size()));
                        }
                        GridLayoutManager layoutManager = new GridLayoutManager(appthis, 1);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        adapterRecipesList = new AdapterRecipesList(recipes, appthis);
                        mRecyclerView.setAdapter(adapterRecipesList);
                        adapterRecipesList.notifyDataSetChanged();

                    }
                });
    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if( ConnectivityReceiver.isConnected())
            getRecipesFromJson();
        else
        {
            net.setText(R.string.network_message);
            Typeface tf = Typeface.createFromAsset(getAssets(), "Fonts/Gabriola.ttf");
            net.setTypeface(tf);
        }
    }
}
