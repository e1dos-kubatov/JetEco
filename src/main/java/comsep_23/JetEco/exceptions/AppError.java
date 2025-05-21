package comsep_23.JetEco.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppError extends RuntimeException {
    private final int statusCode;

    public AppError(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public AppError(HttpStatus status, String message) {
        super(message);
        this.statusCode = status.value();
    }
}
