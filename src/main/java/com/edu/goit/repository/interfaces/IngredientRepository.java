package com.edu.goit.repository.interfaces;

import com.edu.goit.model.Ingredient;
import com.edu.goit.model.form.SearchIngredientModel;

import java.util.Collection;

public interface IngredientRepository extends BaseCrudRepository<Ingredient, Long> {
    Collection<Ingredient> findAll(SearchIngredientModel searchIngredientModel);
    int count(SearchIngredientModel searchIngredientModel);
    boolean updateStatus(Long id, boolean status);

}
