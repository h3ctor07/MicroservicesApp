package Assignment2;

import Assignment2.model.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    private final Producer producer;

    @KafkaListener(topics = "app_updates", groupId = "group_id")
    public void consume(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Employee employee = mapper.readValue(message, Employee.class);

        if (!hasNullValues(employee)) {
            producer.sendMessage("employee_updates", String.valueOf(employee.getEmpId()), mapper.writeValueAsString(employee));
            System.out.println("\n\n\n\t\tMessage sent to employee_updates:" + employee.toString() + "\n\n\n");
        } else {
            producer.sendMessage("employee_DLQ", String.valueOf(employee.getEmpId()), mapper.writeValueAsString(employee));
            System.out.println("\n\n\n\t\tMessage sent to employee_DLQ:" + employee.toString() + "\n\n\n");
        }

    }

    private Boolean hasNullValues(Employee employee) {
        return employee.getEmpId() <= 0 ||
                employee.getEmpName() == null ||
                employee.getEmpCity() == null ||
                employee.getEmpPhone() == null ||
                employee.getJavaExp() == null ||
                employee.getSpringExp() == null;
    }

    @Autowired
    KafkaConsumer(Producer producer){
        this.producer = producer;
    }
}
