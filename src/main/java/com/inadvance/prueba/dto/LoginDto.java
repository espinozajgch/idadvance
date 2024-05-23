package com.inadvance.prueba.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDto {
    @JsonIgnore
    private String user;

    @JsonIgnore
    private String pass;

    private String token;

}
