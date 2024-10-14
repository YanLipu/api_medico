package com.gerenciamento_medico.medico_api.controller;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class Pagination {
    static Sort.Order[] parseSortParams(String[] sort) {
        List<Sort.Order> orders = new ArrayList<>();
        for (String sortParam : sort) {
            String[] parts = sortParam.split(",");
            if (parts.length == 2) {
                orders.add(new Sort.Order(Sort.Direction.fromString(parts[1]), parts[0]));
            } else {
                orders.add(new Sort.Order(Sort.Direction.ASC, parts[0]));
            }
        }
        return orders.toArray(new Sort.Order[0]);
    }
}
