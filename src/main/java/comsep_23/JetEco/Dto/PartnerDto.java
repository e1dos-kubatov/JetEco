package comsep_23.JetEco.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PartnerDto {
    private Long id;
    private String name;
    private String address;
    private String contact;
    private String phone;
    private LocalDateTime registeredAt;
    private boolean active;
}
