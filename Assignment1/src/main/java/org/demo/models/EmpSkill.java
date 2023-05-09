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
@Table("emp_skill")
public class EmpSkill {
    @PrimaryKey("emp_id")
    private int empId;
    @Column("java_exp")
    private Double javaExp;
    @Column("spring_exp")
    private Double springExp;
}
