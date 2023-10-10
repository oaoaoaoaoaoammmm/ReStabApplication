package danya.industries.restab.controllers.exception.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidationException {
    private final String fieldName;
    private final String message;
}
