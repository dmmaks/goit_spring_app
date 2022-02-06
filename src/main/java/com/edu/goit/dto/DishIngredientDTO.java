package com.edu.goit.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class DishIngredientDTO {
    @Pattern(regexp = "^[0-9]+$", message = "Id should be numeric")
    private String id;
    private int amount;
}
