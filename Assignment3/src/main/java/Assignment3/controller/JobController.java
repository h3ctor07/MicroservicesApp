package Assignment3.controller;

import Assignment3.model.Job;
import Assignment3.model.JobEndpoint;
import Assignment3.model.SkillRequest;
import Assignment3.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
public class JobController {
    @Autowired
    private JobService jobService;

    @PostMapping("/createJobProfile")
    public Mono<JobEndpoint> createJobProfile(@RequestBody Job job) {
        System.out.println(job.toString());
        return jobService.insertJob(job);
    }

    @GetMapping("/findEmpForJobID")
    public Flux<String> findEmpForJobID(@RequestBody Map<String, String> idMap) {
        String id = idMap.get("job_id");

        WebClient client = WebClient.create();
        return client.method(HttpMethod.GET)
                .uri("http://localhost:8080/findEmpSkillSet")
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.jobService.skillsFromJobID(id), SkillRequest.class)
                .retrieve()
                .bodyToFlux(String.class);
    }

    @GetMapping("/getJobProfileFromCache")
    public Mono<Job> getJobProfileFromCache(@RequestBody Map<String, String> idMap) {
        String id = idMap.get("job_id");
        return this.jobService.getJobProfile(id);
    }

}
