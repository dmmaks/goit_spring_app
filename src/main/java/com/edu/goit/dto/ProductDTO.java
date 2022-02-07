package com.edu.goit.dto;

import com.edu.goit.model.Manufacturer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    @Pattern(regexp = "[0-9]+", message = "id should be numeric")
    String id;
    @NotNull(message = "name is mandatory")
    @NotBlank(message = "name is mandatory")
    String name;
    @NotNull(message = "price is mandatory")
    @NotBlank(message = "price is mandatory")
    @Pattern(regexp = "[+-]?([0-9]*[.])?[0-9]+", message = "price should be numeric")
    String price;
    @NotNull(message = "manufacturer is mandatory")
    @Pattern(regexp = "[+-]?([0-9]*[.])?[0-9]+", message = "price should be numeric")
    String manufacturerId;
}
