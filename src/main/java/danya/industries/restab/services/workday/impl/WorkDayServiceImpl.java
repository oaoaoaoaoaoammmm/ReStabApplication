package danya.industries.restab.services.workday.impl;

import danya.industries.restab.dtos.WorkDayDto;
import danya.industries.restab.mappers.workday.WorkDayMapper;
import danya.industries.restab.models.WorkDay;
import danya.industries.restab.props.WorkDayProps;
import danya.industries.restab.repositories.WorkDayRepository;
import danya.industries.restab.services.workday.WorkDayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Slf4j
@Service
@Transactional(
        readOnly = true,
        propagation = Propagation.REQUIRED,
        isolation = Isolation.READ_COMMITTED
)
public class WorkDayServiceImpl implements WorkDayService {

    private final WorkDayRepository workDayRepository;
    private final WorkDayMapper workDayMapper;
    private final WorkDayProps workDayProps;

    public WorkDayServiceImpl(
            WorkDayRepository workDayRepository,
            WorkDayMapper workDayMapper,
            WorkDayProps workDayProps
    ) {
        this.workDayRepository = workDayRepository;
        this.workDayMapper = workDayMapper;
        this.workDayProps = workDayProps;
    }

    @Override
    public WorkDayDto getWorkDay(LocalDate date) {
        log.debug("Finding work day by date - {}", date.toString());

        WorkDay workDay = workDayRepository.findByDate(date).orElseThrow(() -> new NoSuchElementException("Can't find work day"));

        return workDayMapper.convertToWorkDayDto(workDay);
    }
}
