package danya.industries.restab.services.workorder;

import danya.industries.restab.dtos.PredictionDto;
import danya.industries.restab.dtos.WorkDayDto;
import danya.industries.restab.dtos.WorkOrderDto;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public interface WorkOrderService {
    CompletableFuture<Collection<WorkOrderDto>> getAllWorkOrders();

    WorkOrderDto getWorkOrderById(Long workOrderId);

    Collection<WorkDayDto> getAllWorkDays(Long workOrderId);

    WorkOrderDto addWorkOrder(WorkOrderDto workOrderDto);

    PredictionDto getPrediction(Long workOrderId);

    PredictionDto addPrediction(Long workOrderId, PredictionDto predictionDto);

    WorkDayDto addWorkDay(Long workOrderId, WorkDayDto workDayDto);

    WorkOrderDto updateWorkOrder(Long workOrderId, WorkOrderDto workOrderDto);

    void deleteWorkOrder(Long workOrderId);

    void deletePrediction(Long predictionId);

    void deleteWorkDay(Long workOrderId, Long wordDayId);
}
