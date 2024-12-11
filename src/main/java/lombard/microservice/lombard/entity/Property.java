package lombard.microservice.lombard.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 256)
    private String name;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "status", length = 50)
    private String status;

    @OneToOne(mappedBy = "property", fetch = FetchType.EAGER)
    private AppraisalRequest appraisalRequest;

    @OneToOne(mappedBy = "property", fetch = FetchType.EAGER)
    private Loan loan;

    @OneToOne(mappedBy = "property", fetch = FetchType.EAGER)
    private PurchaseRequest purchaseRequest;

    @OneToOne(mappedBy = "property", fetch = FetchType.EAGER)
    private PurchaseOffer purchaseOffer;
}
