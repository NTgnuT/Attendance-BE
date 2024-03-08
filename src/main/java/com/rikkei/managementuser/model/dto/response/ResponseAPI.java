package com.rikkei.managementuser.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseAPI implements Serializable {
    //    private transient String name = "HaHa";
    private Boolean success;
    private String message;

}
