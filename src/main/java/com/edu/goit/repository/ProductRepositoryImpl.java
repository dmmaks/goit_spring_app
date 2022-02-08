package com.edu.goit.repository;
import com.edu.goit.model.Product;
import com.edu.goit.repository.interfaces.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ProductRepositoryImpl extends BaseJdbcRepository implements ProductRepository {

    @Value("${sql.products.create}")
    private String createRequest;

    @Value("${sql.products.filter}")
    private String filterRequest;

    @Value("${sql.products.getCount}")
    private String getCountRequest;

    @Value("${sql.products.update}")
    private String updateRequest;

    @Value("${sql.products.delete}")
    private String deleteRequest;

    @Value("${sql.products.findById}")
    private String findByIdRequest;

    private Map namedParameters;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ProductRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate);
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public long create(Product item) {
        return jdbcTemplate.queryForObject(createRequest, Long.class, item.getName(), item.getPrice(), item.getManufacturerId());
    }

    @Override
    public boolean update(Product item) {
        return jdbcTemplate.update(updateRequest, item.getName(), item.getPrice(), item.getManufacturerId(), item.getId()) != 0;
    }

    @Override
    public boolean deleteById(Long id) {
        return jdbcTemplate.update(deleteRequest, id) != 0;
    }

    @Override
    public Product findById(Long id) {
        return jdbcTemplate.queryForObject(
                findByIdRequest, new BeanPropertyRowMapper<>(Product.class), id);
    }

    @Override
    public Collection<Product> filterProducts (String name, Collection<Long> manufacturers, int limit, int offset, boolean order) {
        createFilterArgsList(name, manufacturers);
        String request = order ? String.format(filterRequest, "ASC") : String.format(filterRequest, "DESC");
        namedParameters.put("pageSize", limit);
        namedParameters.put("offset", offset);
        return namedParameterJdbcTemplate.query(
                request, namedParameters, new BeanPropertyRowMapper<>(Product.class)
        );
    }

    @Override
    public int countFilteredProducts (String name, Collection<Long> manufacturers) {
        createFilterArgsList(name, manufacturers);
        Integer count = namedParameterJdbcTemplate.queryForObject(
                getCountRequest, namedParameters, Integer.class);
        return count == null ? 0 : count;
    }

    private void createFilterArgsList (String name, Collection<Long> manufacturers) {
        if (manufacturers != null && manufacturers.size() == 0) {
            manufacturers.add(null);
        }
        namedParameters = new HashMap();
        namedParameters.put("manufacturers", manufacturers);
        namedParameters.put("searchText", name);
    }
}
