package lombard.microservice.lombard.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 256)
    private String name;

    @Column(name = "phone", unique = true, nullable = false, length = 20)
    private String phone;

    @Column(name = "bill", length = 20)
    private String bill;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<Loan> loans;
}
