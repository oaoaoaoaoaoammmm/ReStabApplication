package danya.industries.restab.controllers;

import danya.industries.restab.dtos.PredictionDto;
import danya.industries.restab.dtos.WorkDayDto;
import danya.industries.restab.dtos.WorkOrderDto;
import danya.industries.restab.services.workorder.WorkOrderService;
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

import java.util.Collection;
import java.util.concurrent.CompletableFuture;


@Slf4j
@Validated
@RestController
@RequestMapping("/work-orders")
@Tag(
        name = "Work order controller",
        description = "All about work orders. You can get, post, delete work order here and the same actions with prediction and work day."
)
public class WorkOrderController {

    private final WorkOrderService workOrderService;

    public WorkOrderController(WorkOrderService workOrderService) {
        this.workOrderService = workOrderService;
    }

    @Operation(summary = "Find all work orders")
    @GetMapping
    public CompletableFuture<ResponseEntity<Collection<WorkOrderDto>>> getAllWorkOrders(

    ) {
        return workOrderService.getAllWorkOrders()
                .exceptionally(exception -> {
                    log.warn("Error find all work orders");
                    throw new IllegalArgumentException("Error find all work orders", exception);
                })
                .thenApply(collection -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(collection)
                );
    }

    @Operation(summary = "Find work order by id")
    @GetMapping("/{workOrderId}")
    public ResponseEntity<WorkOrderDto> getWorkOrderById(
            @PathVariable @NotNull @Parameter(name = "Work order id", required = true) Long workOrderId
    ) {
        WorkOrderDto workOrderDto = workOrderService.getWorkOrderById(workOrderId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(workOrderDto);
    }

    @Operation(summary = "Add work order")
    @PostMapping
    public ResponseEntity<WorkOrderDto> addWorkOrder(
            @RequestBody @NotNull @Valid @Parameter(description = "Work order dto", required = true) WorkOrderDto workOrderDto
    ) {
        workOrderDto = workOrderService.addWorkOrder(workOrderDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(workOrderDto);
    }

    @Operation(summary = "Delete work order")
    @DeleteMapping("/{workOrderId}")
    public ResponseEntity<?> deleteWorkOrder(
            @PathVariable @NotNull @Parameter(description = "Work order id", required = true) Long workOrderId
    ) {
        workOrderService.deleteWorkOrder(workOrderId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @Operation(summary = "Update information about work order")
    @PatchMapping("/{workOrderId}")
    public ResponseEntity<WorkOrderDto> updateWorkOrder(
            @PathVariable @NotNull @Parameter(description = "Work order id", required = true) Long workOrderId,
            @RequestBody @NotNull @Valid @Parameter(description = "Work order dto", required = true) WorkOrderDto workOrderDto
    ) {
        workOrderDto = workOrderService.updateWorkOrder(workOrderId, workOrderDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(workOrderDto);
    }

    @Operation(summary = "Find prediction")
    @GetMapping("/{workOrderId}/prediction")
    public ResponseEntity<PredictionDto> getPrediction(
            @PathVariable @NotNull @Parameter(description = "Work order id", required = true) Long workOrderId
    ) {
        PredictionDto predictionDto = workOrderService.getPrediction(workOrderId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(predictionDto);
    }

    @Operation(summary = "Add prediction")
    @PostMapping("/{workOrderId}/prediction")
    public ResponseEntity<PredictionDto> addPrediction(
            @PathVariable @NotNull @Parameter(description = "Work order id", required = true) Long workOrderId,
            @RequestBody @NotNull @Valid @Parameter(description = "Prediction dto", required = true) PredictionDto predictionDto
    ) {
        predictionDto = workOrderService.addPrediction(workOrderId, predictionDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(predictionDto);
    }

    @Operation(summary = "Delete prediction")
    @DeleteMapping("/{workOrderId}/prediction")
    public ResponseEntity<?> deletePrediction(
            @PathVariable @NotNull @Parameter(description = "Work order id", required = true) Long workOrderId
    ) {
        workOrderService.deletePrediction(workOrderId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @Operation(summary = "Get all work days")
    @GetMapping("/{workOrderId}/workdays")
    public ResponseEntity<Collection<WorkDayDto>> getAllWorkDays(
            @PathVariable @NotNull @Parameter(description = "Work order id", required = true) Long workOrderId
    ) {
        Collection<WorkDayDto> collection = workOrderService.getAllWorkDays(workOrderId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collection);
    }

    @Operation(summary = "Add work day")
    @PostMapping("/{workOrderId}/workdays")
    public ResponseEntity<WorkDayDto> addWorkDay(
            @PathVariable @NotNull @Parameter(description = "Work order id", required = true) Long workOrderId,
            @RequestBody @NotNull @Valid @Parameter(description = "Work day dto", required = true) WorkDayDto workDayDto
    ) {
        workDayDto = workOrderService.addWorkDay(workOrderId, workDayDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(workDayDto);
    }

    @Operation(summary = "Delete work day")
    @DeleteMapping("/{workOrderId}/workdays/{workDayId}")
    public ResponseEntity<?> deleteWorkDay(
            @PathVariable @NotNull @Parameter(description = "Work order id", required = true) Long workOrderId,
            @PathVariable @NotNull @Parameter(description = "Work day id", required = true) Long workDayId
    ) {
        workOrderService.deleteWorkDay(workOrderId, workDayId);

        return ResponseEntity
                .noContent()
                .build();
    }
}
