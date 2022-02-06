package com.edu.goit.mapper;

import com.edu.goit.dto.IngredientDTO;
import com.edu.goit.model.Ingredient;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring")
public interface IngredientMapper {
    Ingredient ingredientDTOtoIngredient(IngredientDTO ingredientDTO);
}
