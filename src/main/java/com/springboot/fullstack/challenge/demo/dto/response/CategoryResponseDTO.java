package com.springboot.fullstack.challenge.demo.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDTO {
    private Integer id;
    private String name;
}
