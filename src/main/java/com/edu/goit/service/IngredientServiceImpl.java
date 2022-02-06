package com.edu.goit.service;

import com.edu.goit.exception.BadRequestParamException;
import com.edu.goit.exception.CustomException;
import com.edu.goit.dto.IngredientDTO;
import com.edu.goit.dto.PaginationDTO;
import com.edu.goit.mapper.IngredientMapper;
import com.edu.goit.model.Ingredient;
import com.edu.goit.model.form.SearchIngredientModel;
import com.edu.goit.repository.interfaces.IngredientRepository;
import com.edu.goit.service.interfaces.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;

    @Transactional
    @Override
    public void createIngredient(IngredientDTO ingredientDto) {
        try {
            Ingredient ingredient = ingredientMapper.ingredientDTOtoIngredient(ingredientDto);
            ingredientRepository.create(ingredient);
        } catch (DataIntegrityViolationException ex) {
            throw new BadRequestParamException("ingredientCategory", String.format("Category '%s' not found", ingredientDto.getIngredientCategory()));
        }
    }

    @Override
    public Ingredient getIngredientById(Long id) {
        try {
            return ingredientRepository.findById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new CustomException(HttpStatus.NOT_FOUND, String.format("Ingredient with id %s not found.", id));
        }
    }

    @Override
    public PaginationDTO<Ingredient> getAllIngredients(SearchIngredientModel searchIngredientModel) {
        long numPage = searchIngredientModel.getNumPage();
        int sizePage = searchIngredientModel.getSizePage();

        searchIngredientModel.setNumPage(searchIngredientModel.getNumPage() > 0 ? (numPage * sizePage) : 0);
        int totalElements = ingredientRepository.count(searchIngredientModel);
        Collection<Ingredient> ingredients = ingredientRepository.findAll(searchIngredientModel);

        return new PaginationDTO<>(ingredients, totalElements);
    }

    @Transactional
    @Override
    public void updateIngredient(IngredientDTO ingredientDto, Long id) {
        try {
            Ingredient ingredient = ingredientMapper.ingredientDTOtoIngredient(ingredientDto);
            ingredient.setId(id);
            ingredientRepository.update(ingredient);
        } catch (DataIntegrityViolationException ex) {
            throw new BadRequestParamException("ingredientCategory", String.format("Category '%s' not found", ingredientDto.getIngredientCategory()));
        }
    }

    @Transactional
    @Override
    public void updateStatus(Long id, boolean status) {
        if (!ingredientRepository.updateStatus(id, status))
            throw new CustomException(HttpStatus.NOT_FOUND, String.format("Ingredient with id %s not found", id));
    }
}
