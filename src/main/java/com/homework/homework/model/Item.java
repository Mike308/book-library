package com.homework.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//model of field containing item (data for book)

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private String id;
    private VolumeInfo volumeInfo;
    private AccessInfo accessInfo;
}
