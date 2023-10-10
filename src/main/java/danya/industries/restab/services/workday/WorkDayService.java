package danya.industries.restab.services.workday;

import danya.industries.restab.dtos.WorkDayDto;

import java.time.LocalDate;

public interface WorkDayService {
    WorkDayDto getWorkDay(LocalDate date);
}
