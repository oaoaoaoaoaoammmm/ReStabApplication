package danya.industries.restab.repositories;

import danya.industries.restab.models.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PredictionRepository extends JpaRepository<Prediction, Long> {

}
