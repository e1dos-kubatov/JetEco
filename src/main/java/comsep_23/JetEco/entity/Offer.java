package comsep_23.JetEco.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "offers")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double originalPrice;

    @Column(nullable = false)
    private double discountPrice;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private LocalTime pickupStart;

    @Column(nullable = false)
    private LocalTime pickupEnd;

    private LocalDateTime createdAt;
    private boolean active;

}