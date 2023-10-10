package danya.industries.restab.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "Prediction dto")
public class PredictionDto implements Serializable {
    private Long id;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate finishDate;
}
