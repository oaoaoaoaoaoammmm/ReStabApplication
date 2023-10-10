package danya.industries.restab.controllers;


import danya.industries.restab.dtos.WorkDayDto;
import danya.industries.restab.services.workday.WorkDayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Slf4j
@Validated
@RestController
@RequestMapping("/work-days")
@Tag(
        name = "Work day controller",
        description = "It will be need, maybe."
)
public class WorkDayController {

    private final WorkDayService workDayService;

    public WorkDayController(
            WorkDayService workDayService
    ) {
        this.workDayService = workDayService;
    }

    @Operation(summary = "Get work day")
    @GetMapping
    public ResponseEntity<WorkDayDto> getWorkDay(
            @RequestBody @NotNull @Parameter(description = "Local date of work day") LocalDate date
    ) {
        WorkDayDto workDayDto = workDayService.getWorkDay(date);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(workDayDto);
    }
}
