package danya.industries.restab.services.workorder.impl;

import danya.industries.restab.dtos.PredictionDto;
import danya.industries.restab.dtos.WorkDayDto;
import danya.industries.restab.dtos.WorkOrderDto;
import danya.industries.restab.mappers.prediction.PredictionMapper;
import danya.industries.restab.mappers.workday.WorkDayMapper;
import danya.industries.restab.mappers.workorder.WorkOrderMapper;
import danya.industries.restab.models.Prediction;
import danya.industries.restab.models.WorkDay;
import danya.industries.restab.models.WorkOrder;
import danya.industries.restab.repositories.WorkDayRepository;
import danya.industries.restab.repositories.WorkOrderRepository;
import danya.industries.restab.services.workorder.WorkOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@Transactional(
        readOnly = true,
        propagation = Propagation.REQUIRED,
        isolation = Isolation.READ_COMMITTED
)
public class WorkOrderServiceImpl implements WorkOrderService {
    private final WorkOrderRepository workOrderRepository;
    private final WorkOrderMapper workOrderMapper;
    private final PredictionMapper predictionMapper;
    private final WorkDayMapper workDayMapper;
    private final WorkDayRepository workDayRepository;

    public WorkOrderServiceImpl(
            WorkOrderRepository workOrderRepository,
            WorkOrderMapper workOrderMapper,
            PredictionMapper predictionMapper,
            WorkDayMapper workDayMapper,
            WorkDayRepository workDayRepository
    ) {
        this.workOrderRepository = workOrderRepository;
        this.workOrderMapper = workOrderMapper;
        this.predictionMapper = predictionMapper;
        this.workDayMapper = workDayMapper;
        this.workDayRepository = workDayRepository;
    }

    @Override
    public CompletableFuture<Collection<WorkOrderDto>> getAllWorkOrders() {
        log.debug("Finding all work orders");

        return CompletableFuture.supplyAsync(workOrderRepository::findAll)
                .thenApply(
                        collection -> collection.stream()
                                .map(workOrderMapper::convertToWorkOrderDto)
                                .toList()
                );
    }

    @Override
    public WorkOrderDto getWorkOrderById(Long workOrderId) {
        log.debug("Finding work order by id - {}", workOrderId);

        WorkOrder workOrder = workOrderRepository
                .findById(workOrderId)
                .orElseThrow(() -> new NoSuchElementException("Cant find work order"));

        return workOrderMapper.convertToWorkOrderDto(workOrder);
    }

    @Override
    public Collection<WorkDayDto> getAllWorkDays(Long workOrderId) {
        log.debug("Finding all work days in work order its id - {}", workOrderId);

        WorkOrder workOrder = workOrderRepository
                .findById(workOrderId)
                .orElseThrow(() -> new NoSuchElementException("Can't find work order"));

        return workOrder.getWorkDays().stream()
                .map(workDayMapper::convertToWorkDayDto)
                .toList();
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public WorkOrderDto addWorkOrder(WorkOrderDto workOrderDto) {
        log.debug("Adding work order. It has name - {}", workOrderDto.getName());

        WorkOrder workOrder = workOrderMapper.convertToWorkOrder(workOrderDto);
        workOrder = workOrderRepository.save(workOrder);

        return workOrderMapper.convertToWorkOrderDto(workOrder);
    }

    @Override
    public PredictionDto getPrediction(Long workOrderId) {
        log.debug("Finding prediction by work order id - {}", workOrderId);

        WorkOrder workOrder = workOrderRepository
                .findById(workOrderId)
                .orElseThrow(() -> new NoSuchElementException("Can't find prediction"));

        return predictionMapper.convertToPredictionDto(workOrder.getPrediction());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public PredictionDto addPrediction(Long workOrderId, PredictionDto predictionDto) {
        log.debug("Adding prediction in work order by its id - {}", workOrderId);

        Prediction prediction = predictionMapper.convertToPrediction(predictionDto);
        WorkOrder workOrder = workOrderRepository
                .findById(workOrderId)
                .orElseThrow(() -> new NoSuchElementException("Can't find work order"));
        workOrder.setPrediction(prediction);
        workOrderRepository.flush();

        return predictionMapper.convertToPredictionDto(prediction);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public WorkDayDto addWorkDay(Long workOrderId, WorkDayDto workDayDto) {
        log.debug("Adding work day in work order by its id - {}", workOrderId);

        WorkDay workDay = workDayMapper.convertToWorkDay(workDayDto);
        WorkOrder workOrder = workOrderRepository
                .findById(workOrderId)
                .orElseThrow(() -> new NoSuchElementException("Can't find work order"));
        workOrder.addWorkDay(workDay);
        workOrderRepository.flush();

        return workDayMapper.convertToWorkDayDto(workDay);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public WorkOrderDto updateWorkOrder(Long workOrderId, WorkOrderDto workOrderDto) {
        log.debug("Update work order with id - {}", workOrderId);

        WorkOrder workOrder = workOrderRepository
                .findById(workOrderId)
                .orElseThrow(() -> new NoSuchElementException("Can't find work order"));
        workOrder.setName(workOrderDto.getName());
        workOrder.setDescription(workOrderDto.getDescription());
        workOrderRepository.save(workOrder);

        return workOrderMapper.convertToWorkOrderDto(workOrder);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void deleteWorkOrder(Long workOrderId) {
        log.debug("Deleting work order by that id - {}", workOrderId);

        workOrderRepository.deleteById(workOrderId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void deletePrediction(Long workOrderId) {
        log.debug("Deleting prediction by work order id - {}", workOrderId);

        WorkOrder workOrder = workOrderRepository
                .findById(workOrderId)
                .orElseThrow(() -> new NoSuchElementException("Can't find work order"));
        workOrder.setPrediction(null);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void deleteWorkDay(Long workOrderId, Long workDayId) {
        log.debug("Deleting work day with id - {} from work order its id - {}", workDayId, workOrderId);

        WorkOrder workOrder = workOrderRepository
                .findById(workOrderId)
                .orElseThrow(() -> new NoSuchElementException("Can't find work order"));
        WorkDay workDay = workDayRepository
                .findById(workDayId)
                .orElseThrow(() -> new NoSuchElementException("Can't find work day"));

        workOrder.removeWorkDay(workDay);
    }
}
