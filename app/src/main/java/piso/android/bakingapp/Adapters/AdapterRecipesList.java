package piso.android.bakingapp.Adapters;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

import piso.android.bakingapp.Models.Recipe;
import piso.android.bakingapp.R;
import piso.android.bakingapp.Screens.Detail.RecipeDetail;

/**
 * Created by pisoo on 7/3/2017.
 */

public class AdapterRecipesList extends RecyclerView.Adapter<AdapterRecipesList.viewHolder> {
    private List<Recipe> recipes;
    private AppCompatActivity appCompatActivity;

    public AdapterRecipesList(List<Recipe> recipes, AppCompatActivity appCompatActivity) {
        this.recipes = recipes;
        this.appCompatActivity = appCompatActivity;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_card, null);
        return new viewHolder(item);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, final int position) {


        if (recipes.get(position).getImage() == null || recipes.get(position).getImage().equals("")) {
            Random r = new Random();
            int rNmuber = r.nextInt(11 - 1) + 1;
            String name = "img" + rNmuber;
            int id = appCompatActivity.getResources().getIdentifier(name, "drawable", appCompatActivity.getPackageName());
            Drawable drawable = appCompatActivity.getResources().getDrawable(id);
            holder.mImageView.setImageDrawable(drawable);


        } else {
            // get the image from the internet
            Picasso.with(appCompatActivity)
                    .load(recipes.get(position).getImage())
                    .into(holder.mImageView);
        }
        holder.mTextView.setText(recipes.get(position).getName());

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show the details of this recipe
                Intent intent = new Intent(appCompatActivity, RecipeDetail.class);
                intent.putExtra("recipe", recipes.get(position));
                appCompatActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        ImageView mImageView;
        TextView mTextView;

        public viewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.cv_RecipeCard);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_recipeImage);
            mTextView = (TextView) itemView.findViewById(R.id.tv_nameRecipe);
        }
    }
}
