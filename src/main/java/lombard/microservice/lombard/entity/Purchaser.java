package lombard.microservice.lombard.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "purchaser")
public class Purchaser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 256)
    private String name;

    @Column(name = "phone", unique = true, nullable = false, length = 20)
    private String phone;

    @Column(name = "bill", length = 20)
    private String bill;

    @OneToMany(mappedBy = "purchaser", fetch = FetchType.EAGER)
    private List<PurchaseOffer> purchaseOffers;
}
