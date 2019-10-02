package com.homework.homework.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessType {
    @JsonProperty("isAvailable")
    private boolean isAvailable;
    private String acsTokenLink;
}
