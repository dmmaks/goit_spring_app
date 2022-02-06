package com.edu.goit.repository;
import com.edu.goit.model.Manufacturer;
import com.edu.goit.repository.interfaces.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.Collection;

@Repository
public class ManufacturerRepositoryImpl extends BaseJdbcRepository implements ManufacturerRepository {

    @Value("${sql.manufacturers.create}")
    private String createRequest;

    @Value("${sql.manufacturers.filter}")
    private String filterRequest;

    @Value("${sql.manufacturers.getCount}")
    private String getCountRequest;

    @Value("${sql.manufacturers.update}")
    private String updateRequest;

    @Value("${sql.manufacturers.delete}")
    private String deleteRequest;

    @Value("${sql.manufacturers.findById}")
    private String findByIdRequest;

    public ManufacturerRepositoryImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public long create(Manufacturer item) {
        return jdbcTemplate.queryForObject(createRequest, Long.class, item.getName());
    }

    @Override
    public boolean update(Manufacturer item) {
        return jdbcTemplate.update(updateRequest, item.getName(), item.getId()) != 0;
    }

    @Override
    public boolean deleteById(Long id) {
        return jdbcTemplate.update(deleteRequest, id) != 0;
    }

    @Override
    public Manufacturer findById(Long id) {
        return jdbcTemplate.queryForObject(
                findByIdRequest, new BeanPropertyRowMapper<>(Manufacturer.class), id);
    }

    @Override
    public Collection<Manufacturer> filterManufacturers (String name, int limit, int offset, boolean order) {
        String request = order ? String.format(filterRequest, "ASC") : String.format(filterRequest, "DESC");
        return this.jdbcTemplate.query(
                request,
                new BeanPropertyRowMapper<>(Manufacturer.class), name, limit, offset
        );
    }

    @Override
    public int countFilteredManufacturers (String name) {
        Integer count = jdbcTemplate.queryForObject(getCountRequest, Integer.class, name);
        return count == null ? 0 : count;
    }
}
