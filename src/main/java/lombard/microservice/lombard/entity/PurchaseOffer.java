package lombard.microservice.lombard.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "purchase_offer")
public class PurchaseOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "purchaser_id", referencedColumnName = "id")
    private Purchaser purchaser;

    @OneToOne
    @JoinColumn(name = "property_id", referencedColumnName = "id")
    private Property property;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "status", length = 50)
    private String status;
}
