package com.edu.goit.service;

import com.edu.goit.repository.interfaces.IngredientCategoryRepository;
import com.edu.goit.service.interfaces.IngredientCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class IngredientCategoryServiceImpl implements IngredientCategoryService {
    private final IngredientCategoryRepository ingredientCategoryRepository;

    @Override
    public Collection<String> getAllCategory() {
        Collection<String> test = ingredientCategoryRepository.findAll();
        return ingredientCategoryRepository.findAll();
    }
}
