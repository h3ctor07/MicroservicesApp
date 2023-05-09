package org.demo.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.demo.models.Emp;
import org.demo.models.EmpEndpoint;
import org.demo.models.EmpRequest;
import org.demo.models.EmpSkill;
import org.demo.repositories.EmpRepo;
import org.demo.repositories.SkillRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmpService {
    @Autowired
    private EmpRepo empRepo;
    @Autowired
    private SkillRepo skillRepo;

    public Mono<EmpEndpoint> insertEmployee (EmpRequest req) {
        Emp newEmp = new Emp(req.getEmpId(), req.getEmpName(), req.getEmpCity(), req.getEmpPhone());
        EmpSkill newEmpSkill = new EmpSkill(req.getEmpId(), req.getJavaExp(), req.getSpringExp());

        if (!hasNullValues(req)) {
            System.out.println("Valid Request");
            return this.empRepo
                    .existsById(req.getEmpId())
                    .flatMap(exists -> {
                        if (exists) {
                            return this.skillRepo
                                    .findByEmpId(req.getEmpId())
                                    .flatMap(this::employeeFromSkill)
                                    .doOnNext( endpoint -> {
                                        System.out.println("Already exists");
                                        endpoint.setStatus("Already Exists");
                                    });
                        } else {
                            return this.empRepo
                                    .save(newEmp)
                                    .flatMap(empSkill -> this.skillRepo
                                            .save(newEmpSkill))
                                    .flatMap(this::employeeFromSkill)
                                    .doOnNext(endpoint -> {
                                        System.out.println("Created!");
                                        endpoint.setStatus("Created");
                                    });
                        }
                    });
        }
        System.out.println("Request has null values");
        return Mono.just(req)
                .map(this::requestToEndpoint)
                .doOnNext(dto -> dto.setStatus("Has null values"));
    }

    private Mono<EmpEndpoint> employeeFromSkill (EmpSkill skill) {
        return this.empRepo
                .findByEmpId(skill.getEmpId())
                .map(this::skillToEndpoint)
                .doOnNext(endpoint -> {
                    endpoint.setJavaExp(skill.getJavaExp());
                    endpoint.setSpringExp(skill.getSpringExp());
                    endpoint.setStatus("Fetching Employee and SkillSet");
                    System.out.println("Fetching Employee and SkillSet");
                });
    }

    public String objectToJSONstring (Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private EmpEndpoint skillToEndpoint (Emp emp) {
        EmpEndpoint endpoint = new EmpEndpoint();
        BeanUtils.copyProperties(emp, endpoint);
        return endpoint;
    }

    private EmpEndpoint requestToEndpoint(EmpRequest req) {
        EmpEndpoint endpoint = new EmpEndpoint();
        BeanUtils.copyProperties(req, endpoint);
        return endpoint;
    }

    private Boolean hasNullValues(EmpRequest req) {
        return (req.getEmpId() < 1 ||
                req.getEmpName() == null ||
                req.getEmpCity() == null ||
                req.getEmpPhone() == null ||
                req.getJavaExp() == null ||
                req.getSpringExp() == null);
    }

    public Flux<EmpRequest> findBySkill(EmpSkill skills) {
        Double javaExp = skills.getJavaExp();
        Double springExp = skills.getSpringExp();

        return this.skillRepo
                .findByJavaExpGreaterThanEqualAndSpringExpGreaterThanEqual(javaExp, springExp)
                .flatMap(this::empFromSkill);

    }

    private Mono<EmpRequest> empFromSkill (EmpSkill empSkill) {
        return this.empRepo
                .findById(empSkill.getEmpId())
                .map(e -> {
                    EmpRequest empRequest = new EmpRequest();
                    BeanUtils.copyProperties(e, empRequest);
                    empRequest.setJavaExp(empSkill.getJavaExp());
                    empRequest.setSpringExp(empSkill.getSpringExp());
                    return empRequest;
                });
    }

}
