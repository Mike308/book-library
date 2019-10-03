package com.homework.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// model of field contain

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessInfo {
    private AccessType pdf;
    private AccessType epub;
}

