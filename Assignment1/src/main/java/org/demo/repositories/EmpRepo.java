package org.demo.repositories;

import org.demo.models.Emp;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface EmpRepo extends ReactiveCassandraRepository<Emp, Integer> {
    @AllowFiltering
    Mono<Emp> findByEmpId(int id);
}
