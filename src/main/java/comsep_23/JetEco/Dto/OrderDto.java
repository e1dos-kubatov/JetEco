package comsep_23.JetEco.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDto {
    private Long id;
    private Long clientId;
    private Long offerId;
    private LocalDateTime orderedAt;
    private boolean paid;
    private double amount;
    private boolean active;
}
