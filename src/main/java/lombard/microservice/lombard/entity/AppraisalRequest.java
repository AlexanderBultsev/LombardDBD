package lombard.microservice.lombard.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "appraisal_request")
public class AppraisalRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "appraiser_id", referencedColumnName = "id")
    private Appraiser appraiser;

    @OneToOne
    @JoinColumn(name = "property_id", referencedColumnName = "id")
    private Property property;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "status", length = 50)
    private String status;
}
