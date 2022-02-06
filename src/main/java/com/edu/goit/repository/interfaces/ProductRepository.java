package com.edu.goit.repository.interfaces;

import com.edu.goit.model.Manufacturer;
import com.edu.goit.model.Product;

import java.math.BigDecimal;
import java.util.Collection;

public interface ProductRepository extends BaseCrudRepository<Product, Long> {
    Collection<Product> filterProducts(String name, Collection<Long> manufacturers, int limit, int offset, boolean order);

    int countFilteredProducts(String name, Collection<Long> manufacturers);
}
