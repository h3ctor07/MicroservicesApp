package Assignment3.model;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Table("job")
public class Job {
    @PrimaryKey("job_id")
    private String jobId;
    @Column("job_name")
    private String jobName;
    @Column("java_exp")
    private Double javaExp;
    @Column("spring_exp")
    private Double springExp;
}
