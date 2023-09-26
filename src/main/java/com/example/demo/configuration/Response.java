package com.example.demo.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 253376053394526830L;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer totalRow;

    private Date timestamp;

    private Integer status;

    public static <T> ResponseEntity<Response<Object>> response(T data, String message, Integer total, HttpStatus status) {
        return ResponseEntity.status(status.value()).body(
                Response.builder()
                        .data(data)
                        .message(message)
                        .totalRow(total)
                        .timestamp(new Date())
                        .status(status.value())
                        .build()
        );
    }

    public static <T> ResponseEntity<Response<Object>> response(T data, Integer total, HttpStatus status) {
        return ResponseEntity.status(status.value()).body(
                Response.builder()
                        .data(data)
                        .totalRow(total)
                        .timestamp(new Date())
                        .status(status.value())
                        .build()
        );
    }

    public static <T> ResponseEntity<Response<Object>> response(T data, String message, HttpStatus status) {
        return ResponseEntity.status(status.value()).body(
                Response.builder()
                        .data(data)
                        .message(message)
                        .timestamp(new Date())
                        .status(status.value())
                        .build()
        );
    }

    public static <T> ResponseEntity<Response<Object>> response(T data, HttpStatus status) {
        return ResponseEntity.status(status.value()).body(
                Response.builder()
                        .data(data)
                        .timestamp(new Date())
                        .status(status.value())
                        .build()
        );
    }

    public static <T> ResponseEntity<Response<Object>> response(String message, HttpStatus status) {
        return ResponseEntity.status(status.value()).body(
                Response.builder()
                        .message(message)
                        .timestamp(new Date())
                        .status(status.value())
                        .build()
        );
    }

}
