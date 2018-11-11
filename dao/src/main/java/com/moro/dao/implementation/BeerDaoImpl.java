package com.moro.dao.implementation;

import com.moro.dao.interfaces.BeerDao;
import com.moro.model.Beer;
import com.moro.model.BeerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class BeerDaoImpl implements BeerDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeerDaoImpl.class);

    private static final String BEER_ID = "beer_id";
    private static final String BEER_TITLE = "beer_title";
    private static final String BEER_ABV = "beer_abv";
    private static final String DESCRIPTION = "description";
    private static final String BEER_PRICE = "beer_price";
    private static final String BEER_RATING = "avgRating";
    private static final String BEER_IMAGE = "beer_image";

    @Value("${beer.select}")
    private String selectSql;

    @Value("${beer.selectById}")
    private String selectByIdSql;

    @Value("${beer.insert}")
    private String insertSql;

    @Value("${beer.update}")
    private String updateSql;

    @Value("${beer.delete}")
    private String deleteSql;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private RowMapper<Beer> rowMapper;

    public BeerDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, RowMapper<Beer> rowMapper) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.rowMapper = rowMapper;
    }


    @Override
    public Collection<BeerDto> getAllBeers() {
        LOGGER.debug("getBeerDtos()");

        Collection<BeerDto> beerDtos =
                namedParameterJdbcTemplate.getJdbcOperations()
                .query(selectSql, new BeerDtoRowMapper());

        return beerDtos;
    }

    @Override
    public Beer getBeerById(Integer beerId) {
        SqlParameterSource namedParameter =
                new MapSqlParameterSource("beerId", beerId);

        Beer beer = namedParameterJdbcTemplate
                .queryForObject(selectByIdSql, namedParameter,
                        rowMapper);

        LOGGER.debug("getBeerById({})", beer);

        return beer;
    }

    @Override
    public Beer addBeer(Beer beer) {
        LOGGER.debug("addBeer({})", beer);

        if(beer.getBeerImage() == null || beer.getBeerImage().isEmpty()
                || beer.getBeerImage().equals("?")) {
            beer.setBeerImage("default");
        }

        SqlParameterSource namedParameters =
                new BeanPropertySqlParameterSource(beer);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate
                .update(insertSql,
                        namedParameters,
                        keyHolder);

        if(keyHolder.getKey().intValue() == 0) {
            throw new DuplicateKeyException("beer with that name already exists.");
        } else {
            beer.setBeerId(keyHolder.getKey().intValue());
        }

        LOGGER.debug("beer is added: {}", beer);

        return beer;
    }

    @Override
    public void updateBeer(Beer beer) {
        LOGGER.debug("updateBeer({})", beer);

        SqlParameterSource namedParameters =
                new BeanPropertySqlParameterSource(beer);

        namedParameterJdbcTemplate.update(updateSql, namedParameters);
    }

    @Override
    public void deleteBeerById(Integer beerId) {
        LOGGER.debug("deleteBeerById({})", beerId);

        SqlParameterSource namedParameter =
                new MapSqlParameterSource("beerId", beerId);

        namedParameterJdbcTemplate.update(deleteSql, namedParameter);

    }

    private static class BeerRowMapper implements RowMapper<Beer> {

        @Override
        public Beer mapRow(ResultSet resultSet, int i) throws SQLException {
            Beer beer = new Beer();

            beer.setBeerId(resultSet.getInt(BEER_ID));
            beer.setBeerTitle(resultSet.getString(BEER_TITLE));
            beer.setBeerAbv(resultSet.getDouble(BEER_ABV));
            beer.setDescription(resultSet.getString(DESCRIPTION));
            beer.setPrice(resultSet.getInt(BEER_PRICE));
            beer.setBeerImage(resultSet.getString(BEER_IMAGE));

            return beer;
        }
    }

    private class BeerDtoRowMapper implements RowMapper<BeerDto> {

        @Override
        public BeerDto mapRow(ResultSet resultSet, int i) throws SQLException {
            BeerDto beerDto = new BeerDto();

            beerDto.setBeerId(resultSet.getInt(BEER_ID));
            beerDto.setBeerTitle(resultSet.getString(BEER_TITLE));
            beerDto.setBeerAbv(resultSet.getDouble(BEER_ABV));
            beerDto.setDescription(resultSet.getString(DESCRIPTION));
            beerDto.setBeerPrice(resultSet.getInt(BEER_PRICE));
            beerDto.setBeerImage(resultSet.getString(BEER_IMAGE));
            beerDto.setRating(resultSet.getDouble(BEER_RATING));

            return beerDto;
        }
    }
}
