package org.demo.models;

import lombok.Data;

@Data
public class EmpRequest {
    private int empId;
    private String empName;
    private String empCity;
    private String empPhone;
    private Double javaExp;
    private Double springExp;

}
