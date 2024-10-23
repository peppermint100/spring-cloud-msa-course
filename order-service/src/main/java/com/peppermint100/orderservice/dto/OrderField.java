package com.peppermint100.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderField {
    private String type;
    private boolean optional;
    private String field;
}
