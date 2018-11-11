package com.moro.dao.implementation;

import com.moro.dao.interfaces.OrderDao;
import com.moro.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

public class OrderDaoImpl implements OrderDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDaoImpl.class);

    @Value("${order.select}")
    private String selectSql;

    @Value("${order.selectById}")
    private String selectByIdSql;

    @Value("${order.insert}")
    private String insertSql;

    @Value("${order.delete}")
    private String deleteSql;

    @Value("${order.selectByDate}")
    private String selectByDateSql;


    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public OrderDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Collection<Order> getAllOrders() {
        LOGGER.debug("getAllOrders()");

        Collection<Order> orders =
                namedParameterJdbcTemplate.getJdbcOperations()
                        .query(selectSql,
                                BeanPropertyRowMapper.newInstance(Order.class));

        return orders;
    }

    @Override
    public Collection<Order> getOrdersByDate(String fromDate, String toDate) {
        LOGGER.debug("getOrdersByDate({},{})", fromDate, toDate);

        SqlParameterSource namedParameters =
                new MapSqlParameterSource("fromDate", fromDate)
                    .addValue("toDate", toDate);

        Collection<Order> orders =
                namedParameterJdbcTemplate
                        .query(selectByDateSql,
                                namedParameters,
                                    BeanPropertyRowMapper.newInstance(Order.class));

        return orders;
    }

    @Override
    public Order getOrderById(Integer orderId) {
        LOGGER.debug("getOrderById({})", orderId);

        SqlParameterSource namedParameter =
                new MapSqlParameterSource("orderId", orderId);

        Order order =
                namedParameterJdbcTemplate.queryForObject(selectByIdSql,
                        namedParameter,
                            BeanPropertyRowMapper.newInstance(Order.class));

        return order;
    }

    @Override
    public Order addOrder(Order order) {
        LOGGER.debug("addOrder({})", order);

        SqlParameterSource namedParameters =
                new BeanPropertySqlParameterSource(order);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        if(order.getOrderDate() == null) {
            order.setOrderDate(LocalDate.now().toString());
        } else if(!dateFormatIsValid(order.getOrderDate())) {
            throw new IllegalArgumentException("Date format is invalid. Required: yyyy-MM-dd");
        }

        namedParameterJdbcTemplate
                .update(insertSql, namedParameters, keyHolder);

        order.setOrderId(keyHolder.getKey().intValue());

        return order;
    }

    @Override
    public void deleteOrderById(Integer orderId) {
        LOGGER.debug("deleteOrderById({})", orderId);

        SqlParameterSource namedParameter =
                new MapSqlParameterSource("orderId", orderId);

        namedParameterJdbcTemplate.update(deleteSql, namedParameter);

    }

    private boolean dateFormatIsValid(String date) {
        String datePattern = "\\d{4}-\\d{2}-\\d{2}";
        return date.matches(datePattern);
    }
}
