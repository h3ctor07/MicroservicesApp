package Assignment2.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Employee {
    private int empId;
    private String empName;
    private String empCity;
    private String empPhone;
    private Double javaExp;
    private Double springExp;
}
