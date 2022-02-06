package com.edu.goit.repository;

import com.edu.goit.repository.interfaces.IngredientRepository;
import com.edu.goit.model.Ingredient;
import com.edu.goit.model.form.SearchIngredientModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class IngredientRepositoryImpl extends BaseJdbcRepository implements IngredientRepository {
    @Value("${sql.ingredient.create}")
    private String sqlQueryCreate;
    @Value("${sql.ingredient.update}")
    private String sqlQueryUpdate;
    @Value("${sql.ingredient.findById}")
    private String sqlQueryFindById;
    @Value("${sql.ingredient.rowCount}")
    private String sqlQueryRowCount;
    @Value("${sql.ingredient.findAllByFilter}")
    private String sqlQueryFindAllByFilter;
    @Value("${sql.ingredient.updateStatus}")
    private String sqlQueryUpdateStatus;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public IngredientRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate);
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public long create(Ingredient ingredient) {
        namedParameterJdbcTemplate.update(sqlQueryCreate, new BeanPropertySqlParameterSource(ingredient));
        return 0;
    }

    @Override
    public boolean update(Ingredient ingredient) {
        namedParameterJdbcTemplate.update(sqlQueryUpdate, new BeanPropertySqlParameterSource(ingredient));
        return true;
    }

    @Override
    public boolean deleteById(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Ingredient findById(Long id) {
        return jdbcTemplate.queryForObject(sqlQueryFindById, new BeanPropertyRowMapper<>(Ingredient.class), id);
    }

    @Override
    public Collection<Ingredient> findAll(SearchIngredientModel searchIngredientModel) {
        String query = String.format(sqlQueryFindAllByFilter, searchIngredientModel.getSortBy(), searchIngredientModel.isSortASC() ? "ASC" : "DESC");

        return namedParameterJdbcTemplate.query(query, new BeanPropertySqlParameterSource(searchIngredientModel),
                new BeanPropertyRowMapper<>(Ingredient.class));
    }

    @Override
    public int count(SearchIngredientModel searchIngredientModel) {
        return namedParameterJdbcTemplate.queryForObject(sqlQueryRowCount, new BeanPropertySqlParameterSource(searchIngredientModel), Integer.class);
    }

    @Override
    public boolean updateStatus(Long id, boolean status) {
        return jdbcTemplate.update(sqlQueryUpdateStatus, status, id) != 0;
    }
}
