package danya.industries.restab.mappers.workday;

import danya.industries.restab.dtos.WorkDayDto;
import danya.industries.restab.models.WorkDay;

public interface WorkDayMapper {
    WorkDayDto convertToWorkDayDto(WorkDay workDay);
    WorkDay convertToWorkDay(WorkDayDto workDayDto);
}
