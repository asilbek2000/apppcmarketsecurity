package com.example.apelsintask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Integer catId;
    private String name;
    private String description;
    private double price;
    private String photoURl;
}
