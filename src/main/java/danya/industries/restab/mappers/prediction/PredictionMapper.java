package danya.industries.restab.mappers.prediction;

import danya.industries.restab.dtos.PredictionDto;
import danya.industries.restab.models.Prediction;

public interface PredictionMapper {
    Prediction convertToPrediction(PredictionDto predictionDto);
    PredictionDto convertToPredictionDto(Prediction prediction);
}
