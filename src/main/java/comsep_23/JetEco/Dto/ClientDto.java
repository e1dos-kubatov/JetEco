package comsep_23.JetEco.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClientDto {
    private Long id;
    private String phone;
    private String name;
    private LocalDateTime registeredAt;
    private boolean active;
}