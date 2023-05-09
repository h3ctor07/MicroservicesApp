package Assignment3.model;

import lombok.Data;

@Data
public class JobEndpoint {
    private String jobId;
    private String jobName;
    private Double javaExp;
    private Double springExp;
    private String status;
}
