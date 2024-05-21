package com.inadvance.prueba.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDto {
    private UUID id;
    private String number;
    private String citycode;
    private String contrycode;
    private UUID user_id;
}
