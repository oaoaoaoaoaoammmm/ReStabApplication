package danya.industries.restab.controllers.exception.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DefaultException {
    private String message;
    private String description;
    private LocalDateTime time;
}
