package org.demo.repositories;

import org.demo.models.EmpSkill;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SkillRepo extends ReactiveCassandraRepository<EmpSkill, Integer> {
    @AllowFiltering
    Mono<EmpSkill> findByEmpId(int id);

    @AllowFiltering
    Flux<EmpSkill> findByJavaExpGreaterThanEqualAndSpringExpGreaterThanEqual(Double javaExp, Double springExp);
}
