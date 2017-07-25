package piso.android.bakingapp.Screens.Detail;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import piso.android.bakingapp.Adapters.AdapterDetail;
import piso.android.bakingapp.Models.Recipe;
import piso.android.bakingapp.R;
import piso.android.bakingapp.Screens.Ingredient.IngredientsActivityFragment;

public class RecipeDetail extends AppCompatActivity {
    private Recipe mRecipe ;
    public static boolean twoPane = false ;
    public static String  ingres ;
    RecyclerView mSteps;
    List<String> steps ;
    AdapterDetail adapterDetail ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        if(getIntent()!=null)
        {
            mRecipe = (Recipe) getIntent().getSerializableExtra("recipe");
            Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();
            getSupportActionBar().setTitle(mRecipe.getName());
        }

        mSteps = (RecyclerView)findViewById(R.id.rv_Steps);
        steps = new ArrayList<>();
        steps =  getSteplist() ;
        Log.v("size " , String.valueOf(steps.size()));
        steps.add(0,mRecipe.getName() + " Ingredients") ;
        steps.add(1, " Steps ") ;

        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        mSteps.setLayoutManager(layoutManager);
        mSteps.setItemAnimator(new DefaultItemAnimator());
        adapterDetail = new AdapterDetail(steps, this,mRecipe);
        mSteps.setAdapter(adapterDetail);
        adapterDetail.notifyDataSetChanged();
        if(findViewById(R.id.Frangment_detail_container)!=null)
        {
            twoPane = true ;
            FragmentManager fm = getFragmentManager();

            FragmentTransaction ft = fm.beginTransaction();
            IngredientsActivityFragment fragment = new IngredientsActivityFragment() ;
            Bundle bundle = new Bundle();
            bundle.putSerializable("recipe",mRecipe);
            fragment.setArguments(bundle);
            ft.replace(R.id.Frangment_detail_container, fragment);
            ft.commit();
        }
        else
        {
            twoPane = false ;
            Toast.makeText(this, "onepane", Toast.LENGTH_SHORT).show();
        }


        // save ingredients
        SharedPreferences sharedpreferences = getSharedPreferences("ingre", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("recipeIngre", getIngredientsfromRecipe());
        ingres = getIngredientsfromRecipe();
        editor.apply();

    }
    private List<String> getSteplist() {
        List<String> steps_  = new ArrayList<>();
        for( int i=0; i< mRecipe.getSteps().size() ; i++)
        {
            steps_.add((i+ 1)+ "- " +mRecipe.getSteps().get(i).getShortDescription());
        }
        return steps_ ;
    }
    private String getIngredientsfromRecipe() {
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
