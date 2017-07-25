package piso.android.bakingapp.Models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pisoo on 7/3/2017.
 */

public class Recipe implements Serializable {
     private String name ;
     private String Image ;
     private  int servings ;
     private List<Ingredient> ingredients ;
     private List<Step> steps ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
}
