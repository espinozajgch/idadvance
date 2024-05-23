package com.inadvance.prueba.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PhoneDto {
    private Long id;
    private String number;
    private String citycode;
    private String contrycode;
}
