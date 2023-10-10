package danya.industries.restab.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "Work order dto")
public class WorkOrderDto implements Serializable {
    private Long id;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String description;
    @Min(0)
    @Max(101)
    @NotNull
    private Float percentProgress;
}
