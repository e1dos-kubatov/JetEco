package comsep_23.JetEco.config;

import lombok.Data;

@Data
public class JwtRequest {
    private String phone;
    private String password;
}
