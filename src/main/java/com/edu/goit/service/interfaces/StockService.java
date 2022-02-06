package com.edu.goit.service.interfaces;


import com.edu.goit.dto.PaginationDTO;
import com.edu.goit.dto.StockIngredientDTO;
import com.edu.goit.model.Ingredient;

import java.util.List;

public interface StockService {
    void addToStock(String accountEmail, long ingredientId, int amount);
    void deleteFromStock(String accountEmail, long ingredientId);
    void updateIngredientFromStock(String accountEmail, long ingredientId, int amount);
    PaginationDTO<StockIngredientDTO> getIngredientsFromStock(int size, int currentPage, String search, boolean order, String sortBy,
                                                              List<String> ingredientCategory, String accountEmail);
    PaginationDTO<Ingredient> getIngredientsToAdd(int size, int currentPage, String search, boolean order, String sortBy,
                                                  List<String> ingredientCategory, String accountEmail);
}

