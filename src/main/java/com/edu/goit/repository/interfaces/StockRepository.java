package com.edu.goit.repository.interfaces;

import com.edu.goit.dto.StockIngredientDTO;
import com.edu.goit.model.Ingredient;
import com.edu.goit.model.Stock;
import com.edu.goit.model.form.SearchStockIngredientModel;

import java.util.Collection;

public interface StockRepository extends BaseCrudRepository<Stock, Long> {
    Collection<StockIngredientDTO> findAllIngredientsInStock(SearchStockIngredientModel searchStockIngredient);
    int countAllIngredientsInStock(SearchStockIngredientModel searchStockIngredient);
    boolean deleteByAccountAndIngredient(long accountId, long ingredientId);
    boolean updateAmountByAccountAndIngredient(long accountId, long ingredientId, int amount);
    Collection<Ingredient> findViableIngredients(SearchStockIngredientModel searchStockIngredientModel);
    int countViableIngredients(SearchStockIngredientModel searchStockIngredientModel);
}
