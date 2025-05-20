package comsep_23.JetEco.Dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class OfferDto {
    private Long id;
    private Long partnerId;
    private String title;
    private String description;
    private double originalPrice;
    private double discountPrice;
    private int quantity;
    private LocalTime pickupStart;
    private LocalTime pickupEnd;
    private LocalDateTime createdAt;
    private boolean active;
}
