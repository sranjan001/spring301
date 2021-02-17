package com.vmware.training.spring301;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Book {

    private String name;
    private String author;
    private Double price;

}
