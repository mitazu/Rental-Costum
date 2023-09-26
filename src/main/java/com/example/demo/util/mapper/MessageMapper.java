package com.example.demo.util.mapper;

import com.example.demo.configuration.Message;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class MessageMapper implements RowMapper<Message> {

    public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
        Message mapper = new Message();
        mapper.setMessage(rs.getString("message"));
        return mapper;
    }

}
