package com.qima.fullstack.interview.demo.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryResponseDTO {
    private Integer id;
    private String name;
}
