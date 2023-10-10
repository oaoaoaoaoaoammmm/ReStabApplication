package danya.industries.restab.repositories;

import danya.industries.restab.models.WorkDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface WorkDayRepository extends JpaRepository<WorkDay, Long> {
    Optional<WorkDay> findByDate(LocalDate date);
}
