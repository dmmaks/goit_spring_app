package com.edu.goit.repository.interfaces;

import com.edu.goit.model.Manufacturer;

import java.util.Collection;

public interface ManufacturerRepository extends BaseCrudRepository<Manufacturer, Long> {
    Collection<Manufacturer> filterManufacturers(String name, int limit, int offset, boolean order);

    int countFilteredManufacturers(String name);
}
