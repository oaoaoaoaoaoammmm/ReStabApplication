package danya.industries.restab.mappers.workday.impl;

import danya.industries.restab.dtos.WorkDayDto;
import danya.industries.restab.mappers.workday.WorkDayMapper;
import danya.industries.restab.models.WorkDay;
import org.springframework.stereotype.Component;

@Component
public class WorkDayMapperImpl implements WorkDayMapper {

    @Override
    public WorkDayDto convertToWorkDayDto(WorkDay workDay) {
        if (workDay == null) {
            return null;
        }

        WorkDayDto.WorkDayDtoBuilder dto = WorkDayDto.builder();

        return dto.id(workDay.getId())
                .date(workDay.getDate())
                .build();
    }

    @Override
    public WorkDay convertToWorkDay(WorkDayDto workDayDto) {
        if (workDayDto == null) {
            return null;
        }

        WorkDay.WorkDayBuilder workday = WorkDay.builder();

        return workday.id(workDayDto.getId())
                .date(workDayDto.getDate())
                .build();
    }
}
