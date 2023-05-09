package org.demo.controller;


import org.demo.models.EmpEndpoint;
import org.demo.models.EmpRequest;
import org.demo.models.EmpSkill;
import org.demo.services.EmpService;
import org.demo.services.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
public class EmployeeController {
    @Autowired
    private EmpService empService;

    private final Producer producer;

    @PostMapping("/createEmployee")
    public Mono<EmpEndpoint> createEmployee(@RequestBody EmpRequest req) {
        producer.sendMessage(req.getEmpName(), empService.objectToJSONstring(req));
        return this.empService.insertEmployee(req);
    }

    @GetMapping("/findEmpSkillSet")
    public Flux<EmpRequest> findEmpSkillSet(@RequestBody Map<String, Double> skillSet) {
        EmpSkill empSkill = new EmpSkill();
        empSkill.setJavaExp(skillSet.getOrDefault("java_exp", 0.0));
        empSkill.setSpringExp(skillSet.getOrDefault("spring_exp", 0.0));

        return empService.findBySkill(empSkill);
    }

    @Autowired
    EmployeeController(Producer producer) {
        this.producer = producer;
    }

}
