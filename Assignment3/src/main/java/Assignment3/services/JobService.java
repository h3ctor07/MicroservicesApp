package Assignment3.services;

import Assignment3.model.Job;
import Assignment3.model.JobEndpoint;
import Assignment3.model.SkillRequest;
import Assignment3.respository.JobRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class JobService {
    @Autowired
    private JobRepository jobRepo;

    public Mono<JobEndpoint> insertJob(Job job) {
        if (!hasNullValues(job)) {
            System.out.println("Valid Request!");
            return this.jobRepo
                    .findById(job.getJobId())
                    .map(e -> toEndpoint(e, "Already exists"))
                    .switchIfEmpty(this.jobRepo.save(job).map(e -> toEndpoint(e, "Created")));
        } else return Mono.just(toEndpoint(job, "Has Null Values"));
    }

    private Boolean hasNullValues(Job job) {
        return job.getJobId() == null ||
                job.getJobName() == null ||
                job.getJavaExp() == null ||
                job.getSpringExp() == null;
    }

    private JobEndpoint toEndpoint(Job job, String status) {
        JobEndpoint endpoint = new JobEndpoint();
        BeanUtils.copyProperties(job, endpoint);
        endpoint.setStatus(status);
        return endpoint;

    }

    public Mono<SkillRequest> skillsFromJobID(String ID) {
        return this.jobRepo.findById(ID)
                .map(this::jobToSkillRequest);
    }

    private SkillRequest jobToSkillRequest(Job job) {
        SkillRequest request = new SkillRequest();
        request.setJava_exp(job.getJavaExp());
        request.setSpring_exp(job.getSpringExp());
        return request;
    }


    public Mono<Job> getJobProfile(String id) {
        return this.jobRepo.findById(id);
    }
}
