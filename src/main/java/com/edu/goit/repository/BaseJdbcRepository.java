package com.edu.goit.repository;

import org.springframework.jdbc.core.JdbcTemplate;

public class BaseJdbcRepository {
    protected JdbcTemplate jdbcTemplate;

    public BaseJdbcRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
}
