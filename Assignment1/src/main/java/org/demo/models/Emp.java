package org.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("emp")
public class Emp {
    @PrimaryKey("emp_id")
    private int empId;
    @Column("emp_name")
    private String empName;
    @Column("emp_city")
    private String empCity;
    @Column("emp_phone")
    private String empPhone;
}
