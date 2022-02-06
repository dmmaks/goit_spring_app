package com.edu.goit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishIngredient {
    private long id;
    private String name;
    private String imgUrl;
    private String ingredientCategory;
    private boolean active;
    private int amount;
}
