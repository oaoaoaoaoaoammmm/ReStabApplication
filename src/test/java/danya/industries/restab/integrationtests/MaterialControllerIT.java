package danya.industries.restab.integrationtests;

import danya.industries.restab.controllers.MaterialController;
import danya.industries.restab.controllers.WorkOrderController;
import danya.industries.restab.dtos.MaterialDto;
import danya.industries.restab.dtos.WorkDayDto;
import danya.industries.restab.dtos.WorkOrderDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MaterialControllerIT {

    @Autowired
    MaterialController materialController;
    @Autowired
    WorkOrderController workOrderController;

    @BeforeAll
    public void init() {
        WorkOrderDto workOrderDto = WorkOrderDto.builder()
                .name("SSS")
                .description("SSS")
                .percentProgress(0F)
                .build();

        workOrderDto = workOrderController.addWorkOrder(workOrderDto).getBody();
        assertNotNull(workOrderDto);

        WorkDayDto workDayDto = WorkDayDto.builder()
                .date(LocalDate.now())
                .build();

        workDayDto = workOrderController.addWorkDay(workOrderDto.getId(), workDayDto).getBody();
        assertNotNull(workDayDto);
    }

    @Test
    @Order(1)
    public void addMaterialTest() {
        MaterialDto materialDto = MaterialDto.builder()
                .localTime(LocalTime.now())
                .value(100F)
                .materialType("cement")
                .build();

        MaterialDto created = materialController.addMaterial(
                1L,
                materialDto,
                new MockMultipartFile("name", new byte[]{}),
                new MockMultipartFile("name", new byte[]{})
        ).getBody();

        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals(materialDto.getLocalTime(), created.getLocalTime());
        assertEquals(materialDto.getValue(), created.getValue());
        assertEquals(materialDto.getMaterialType(), created.getMaterialType());
    }

    @Test
    @Order(2)
    public void getPageOfMaterialsTest() {
        Collection<MaterialDto> collection = materialController.getPageOfMaterials(
                1L,
                0,
                "cement"
        ).getBody();

        assertNotNull(collection);
        assertEquals(1, collection.size());
    }

    @Test
    @Order(3)
    public void deleteMaterialTest() {
        materialController.deleteMaterial(1L, 1L);

        Collection<MaterialDto> collection = materialController.getPageOfMaterials(
                1L,
                0,
                "cement"
        ).getBody();

        assertNotNull(collection);
        assertEquals(0, collection.size());
    }
}
