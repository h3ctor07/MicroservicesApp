package Assignment3.respository;

import Assignment3.model.Job;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends ReactiveCassandraRepository<Job, String> {

}
