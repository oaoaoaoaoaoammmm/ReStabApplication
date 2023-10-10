package danya.industries.restab.services.prediction.impl;

import danya.industries.restab.dtos.PredictionDto;
import danya.industries.restab.mappers.prediction.PredictionMapper;
import danya.industries.restab.models.Prediction;
import danya.industries.restab.repositories.PredictionRepository;
import danya.industries.restab.services.prediction.PredictionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Slf4j
@Service
@Transactional(
        readOnly = true,
        propagation = Propagation.REQUIRED,
        isolation = Isolation.READ_COMMITTED
)
public class PredictionServiceImpl implements PredictionService {

    private final PredictionRepository predictionRepository;
    private final PredictionMapper predictionMapper;

    public PredictionServiceImpl(
            PredictionRepository predictionRepository,
            PredictionMapper predictionMapper
    ) {
        this.predictionRepository = predictionRepository;
        this.predictionMapper = predictionMapper;
    }

    @Override
    public PredictionDto updatePrediction(Long predictionId, PredictionDto predictionDto) {
        log.debug("Finding prediction by id - {}", predictionId);

        Prediction prediction = predictionRepository
                .findById(predictionId)
                .orElseThrow(() -> new NoSuchElementException("Can't find prediction"));
        prediction.setStartDate(predictionDto.getStartDate());
        prediction.setFinishDate(predictionDto.getFinishDate());
        predictionRepository.save(prediction);

        return predictionMapper.convertToPredictionDto(prediction);
    }
}
