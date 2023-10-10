package danya.industries.restab.integrationtests;

import danya.industries.restab.controllers.WorkOrderController;
import danya.industries.restab.dtos.PredictionDto;
import danya.industries.restab.dtos.WorkDayDto;
import danya.industries.restab.dtos.WorkOrderDto;
import danya.industries.restab.models.WorkOrder;
import danya.industries.restab.repositories.PredictionRepository;
import danya.industries.restab.repositories.WorkDayRepository;
import danya.industries.restab.repositories.WorkOrderRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WorkOrderControllerIT {

    @Autowired
    WorkOrderRepository workOrderRepository;
    @Autowired
    PredictionRepository predictionRepository;
    @Autowired
    WorkDayRepository workDayRepository;
    @Autowired
    WorkOrderController workOrderController;

    @Test
    @Order(1)
    @SneakyThrows
    public void addWorkOrderTest() {
        WorkOrderDto dto = WorkOrderDto.builder()
                .name("London - Berlin")
                .description("Asphalt")
                .percentProgress(10F)
                .build();

        WorkOrderDto created = workOrderController.addWorkOrder(dto).getBody();

        assertNotNull(created);
        List<WorkOrderDto> collection = (List<WorkOrderDto>) workOrderController.getAllWorkOrders().get().getBody();
        assertNotNull(collection);
        dto = collection.get(0);
        assertNotNull(created.getId());
        assertEquals(dto.getName(), created.getName());
        assertEquals(dto.getDescription(), created.getDescription());
        assertEquals(dto.getPercentProgress(), created.getPercentProgress());

    }

    @Test
    @Order(2)
    @SneakyThrows
    public void getAllWorkOrderTest() {
        WorkOrderDto workOrder = WorkOrderDto.builder()
                .name("Berlin - Berlin")
                .description("Asphalt8")
                .percentProgress(50F)
                .build();


        workOrder = workOrderController.addWorkOrder(workOrder).getBody();
        assertNotNull(workOrder);

        Collection<WorkOrderDto> list = workOrderController.getAllWorkOrders().get().getBody();
        assertNotNull(list);
        assertEquals(2, list.size());
    }

    @Test
    @Order(3)
    @SneakyThrows
    public void deleteWorkOrderTest() {
        workOrderController.deleteWorkOrder(2L);

        List<WorkOrderDto> list = (List<WorkOrderDto>) workOrderController.getAllWorkOrders().get().getBody();
        assertNotNull(list);
        assertEquals(1, list.size());

        WorkOrderDto workOrderDto = list.get(0);
        assertEquals("London - Berlin", workOrderDto.getName());
        assertEquals("Asphalt", workOrderDto.getDescription());
        assertEquals(10F, workOrderDto.getPercentProgress());
    }

    @Test
    @Order(4)
    @SneakyThrows
    public void updateWorkOrderTest() {
        WorkOrderDto dto = WorkOrderDto.builder()
                .name("Agar - Pek")
                .description("Asphalt100")
                .percentProgress(10F)
                .build();

        WorkOrderDto workOrderDto = workOrderController.updateWorkOrder(1L, dto).getBody();
        assertNotNull(workOrderDto);

        List<WorkOrderDto> list = (List<WorkOrderDto>) workOrderController.getAllWorkOrders().get().getBody();
        assertNotNull(list);
        assertEquals(1, list.size());

        dto = list.get(0);

        assertEquals("Agar - Pek", dto.getName());
        assertEquals("Asphalt100", dto.getDescription());
        assertEquals(10F, dto.getPercentProgress());
    }

    @Test
    @Order(5)
    @SneakyThrows
    public void addPredictionTest() {
        PredictionDto dto = PredictionDto.builder()
                .startDate(LocalDate.now())
                .finishDate(LocalDate.of(2050, 10, 3))
                .build();

        dto = workOrderController.addPrediction(1L, dto).getBody();
        assertNotNull(dto);

        PredictionDto predictionDto = workOrderController.getPrediction(1L).getBody();
        assertNotNull(predictionDto);


        assertNotNull(dto.getId());
        assertEquals(dto.getStartDate(), predictionDto.getStartDate());
        assertEquals(dto.getFinishDate(), predictionDto.getFinishDate());
    }

    @Test
    @Order(6)
    @SneakyThrows
    public void deletePredictionTest() {

        workOrderController.deletePrediction(1L);

        WorkOrder workOrder = workOrderRepository.findById(1L).orElseThrow(NoSuchElementException::new);
        assertNotNull(workOrder);
        assertNull(workOrder.getPrediction());
    }

    @Test
    @Order(7)
    @SneakyThrows
    public void addWorkDayTest() {
        WorkDayDto dto = WorkDayDto.builder()
                .date(LocalDate.now())
                .build();

        dto = workOrderController.addWorkDay(1L, dto).getBody();

        assertNotNull(dto);
        List<WorkDayDto> list = (List<WorkDayDto>) workOrderController.getAllWorkDays(1L).getBody();
        assertNotNull(list);
        WorkDayDto added = list.get(0);
        assertNotNull(dto.getId());
        assertEquals(dto.getDate(), added.getDate());
    }


    @Test
    @Order(8)
    @SneakyThrows
    public void deleteWorkDayTest() {

        workOrderController.deleteWorkDay(1L, 1L);

        Collection<WorkDayDto> collection = workOrderController.getAllWorkDays(1L).getBody();
        assertNotNull(collection);
        assertEquals(0, collection.size());
    }

    @Test
    @Order(9)
    @SneakyThrows
    public void addWorkOrderTestNull() {
        assertThrows(Exception.class, () -> workOrderController.addWorkOrder(null).getBody());
    }

    @Test
    @Order(10)
    @SneakyThrows
    public void addWorkOrderTestInvalid() {
        WorkOrderDto workOrderDto = WorkOrderDto.builder()
                .description("sss")
                .percentProgress(0F)
                .build();

        assertThrows(Exception.class, () -> workOrderController.addWorkOrder(workOrderDto).getBody());
    }
}
