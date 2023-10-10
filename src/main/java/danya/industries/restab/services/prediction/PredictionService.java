package danya.industries.restab.services.prediction;

import danya.industries.restab.dtos.PredictionDto;

public interface PredictionService {
    PredictionDto updatePrediction(Long predictionId, PredictionDto predictionDto);
}
