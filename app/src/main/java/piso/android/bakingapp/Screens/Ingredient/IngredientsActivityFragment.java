package piso.android.bakingapp.Screens.Ingredient;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import piso.android.bakingapp.Models.Recipe;
import piso.android.bakingapp.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * A placeholder fragment containing a simple view.
 */
public class IngredientsActivityFragment extends Fragment {
    TextView mIngredients;
    Recipe mRecipe;
    public IngredientsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.ingredients_fragment,container,false);
        if (getActivity().getIntent() != null) {
            mRecipe = (Recipe) getActivity().getIntent().getSerializableExtra("recipe");
        }
        if(getArguments() != null)
        {
            mRecipe = (Recipe) getArguments().getSerializable("recipe");
        }
        mIngredients = (TextView) view.findViewById(R.id.tv_ingredients);
        mIngredients.setText(getIngredientsfromRecipe());
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Gabriola.ttf");
        mIngredients.setTypeface(tf);

        return  view ;
    }
    private String getIngredientsfromRecipe() {
        if(mRecipe == null )
        {
            SharedPreferences preferences = getActivity().getSharedPreferences("ingre", MODE_PRIVATE);
            return preferences.getString("recipeIngre", "choose your Fav Recipe ");
        }
        String ingre = "";
        for (int i = 0; i < mRecipe.getIngredients().size(); i++) {
            String num = (i+ 1)+ "- ";
            String q = String.valueOf(mRecipe.getIngredients().get(i).getQuantity()) + " ";
            String m = String.valueOf(mRecipe.getIngredients().get(i).getMaesure()) + " ";
            String in = String.valueOf(mRecipe.getIngredients().get(i).getIngredient()) + " ";
            ingre += num + q + m + in+ "\n";
        }
        return ingre ;
    }
}
