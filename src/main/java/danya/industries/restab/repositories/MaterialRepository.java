package danya.industries.restab.repositories;

import danya.industries.restab.models.Material;
import danya.industries.restab.models.WorkDay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    Page<Material> findAllByWorkDayAndMaterialType(WorkDay workDay, String materialType, Pageable pageable);
}
