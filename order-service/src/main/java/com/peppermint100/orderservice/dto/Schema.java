package com.peppermint100.orderservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Schema {
    private String type;
    private List<OrderField> fields;
    private boolean optional;
    private String name;
}
