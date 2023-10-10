package danya.industries.restab.controllers;


import danya.industries.restab.dtos.PredictionDto;
import danya.industries.restab.services.prediction.PredictionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequestMapping("/predictions")
@Tag(
        name = "Prediction controller",
        description = "All about prediction."
)
public class PredictionController {

    private final PredictionService predictionService;

    public PredictionController(
            PredictionService predictionService
    ) {
        this.predictionService = predictionService;
    }

    @Operation(summary = "Update prediction")
    @GetMapping("/{predictionId}")
    public ResponseEntity<PredictionDto> getAllWorkOrders(
            @PathVariable @NotNull @Parameter(name = "Prediction id", required = true) Long predictionId,
            @RequestBody @NotNull @Valid @Parameter(name = "Prediction dto", required = true) PredictionDto predictionDto
    ) {
        predictionDto = predictionService.updatePrediction(predictionId, predictionDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(predictionDto);
    }
}
