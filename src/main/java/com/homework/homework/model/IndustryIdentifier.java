package com.homework.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//model of field IndustryIdentifier

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndustryIdentifier {
    private String type;
    private String identifier;
}
