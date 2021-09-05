package com.hm.pruebanisum.app.models.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhoneDto {

    private String number;
    private String citycode;
    private String contrycode;

}
