package danya.industries.restab.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "Material dto")
public class MaterialDto implements Serializable {
    private Long id;
    @NotNull
    private LocalTime localTime;
    @NotNull
    @NotBlank
    private String materialType;
    @NotNull
    private Float value;
    private Long valuePictureId;
    private Long consignmentNotePictureId;
}
