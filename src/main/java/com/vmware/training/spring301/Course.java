package com.vmware.training.spring301;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Course {

    private Integer id;
    private String name;
    private int durationDays;

}
