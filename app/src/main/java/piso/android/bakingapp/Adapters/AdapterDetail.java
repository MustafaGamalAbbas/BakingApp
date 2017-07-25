package piso.android.bakingapp.Adapters;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import piso.android.bakingapp.Screens.Detail.RecipeDetail;
import piso.android.bakingapp.Screens.Ingredient.IngredientsActivity;
import piso.android.bakingapp.Models.Recipe;
import piso.android.bakingapp.R;
import piso.android.bakingapp.Screens.Ingredient.IngredientsActivityFragment;
import piso.android.bakingapp.StepActivity;
import piso.android.bakingapp.StepFragment;

/**
 * Created by pisoo on 7/3/2017.
 */

public class AdapterDetail extends RecyclerView.Adapter<AdapterDetail.viewHolder>  {
    List<String> steps;
    AppCompatActivity appCompatActivity ;

    Recipe mRecipe  ;
    public AdapterDetail(List <String> steps , AppCompatActivity appCompatActivity ,Recipe recipe){
        this.steps = steps ;
        this.appCompatActivity = appCompatActivity ;
        this.mRecipe = recipe ;
    }
    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_cardview, null);
        return   new AdapterDetail.viewHolder(item);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, final int position) {
        if(position == 0||position ==1 )
        {
            holder.mTextView.setTextSize(32f);
            holder.mTextView.setTextColor(Color.BLACK);
            if(position ==1 )
            {
                holder.cardView.setBackgroundColor(Color.GRAY);
            }
        }
            holder.mTextView.setText(steps.get(position));
            Typeface tf = Typeface.createFromAsset(appCompatActivity.getAssets(), "Fonts/Gabriola.ttf");
            holder.mTextView.setTypeface(tf);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position==1)
                    return;
                if(position == 0 )
                {
                    if(RecipeDetail.twoPane){
                        FragmentManager fm =appCompatActivity.getFragmentManager();

                        FragmentTransaction ft = fm.beginTransaction();
                        IngredientsActivityFragment fragment = new IngredientsActivityFragment() ;
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("recipe",mRecipe);
                        fragment.setArguments(bundle);
                        ft.replace(R.id.Frangment_detail_container, fragment);
                        ft.commit();
                    }
                    else{
                        Intent intent = new Intent(appCompatActivity , IngredientsActivity.class);
                        intent.putExtra("recipe",mRecipe);
                        appCompatActivity.startActivity(intent);
                    }


                    return;
                }

                if(RecipeDetail.twoPane){
                    android.support.v4.app.FragmentManager fm =appCompatActivity.getSupportFragmentManager()   ;
                    android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();

                    StepFragment fragment = new StepFragment() ;
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("recipe",mRecipe);
                    bundle.putInt("position",position-2   );
                    fragment.setArguments(bundle);
                    ft.replace(R.id.Frangment_detail_container, fragment);
                    ft.commit();
                }
                else{
                    Intent intent = new Intent(appCompatActivity , StepActivity.class);
                    intent.putExtra("recipe",mRecipe);
                    intent.putExtra("size", mRecipe.getSteps().size());
                    intent.putExtra("position",position-2   );
                    Toast.makeText(appCompatActivity, String.valueOf(position), Toast.LENGTH_SHORT).show();
                    appCompatActivity.startActivity(intent);
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        CardView cardView ;
        TextView mTextView ;
        public viewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cv_stepItem);
            mTextView = (TextView) itemView.findViewById(R.id.tv_stepItem);
        }
    }
}
