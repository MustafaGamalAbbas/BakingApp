package piso.android.bakingapp.Models;

import java.io.Serializable;

/**
 * Created by pisoo on 7/3/2017.
 */

public class Ingredient implements Serializable{
    private int quantity  ;
    private String maesure ;
    private String ingredient ;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMaesure() {
        return maesure;
    }

    public void setMaesure(String maesure) {
        this.maesure = maesure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
