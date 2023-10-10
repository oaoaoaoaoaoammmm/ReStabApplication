package danya.industries.restab.mappers.prediction.impl;

import danya.industries.restab.dtos.PredictionDto;
import danya.industries.restab.mappers.prediction.PredictionMapper;
import danya.industries.restab.models.Prediction;
import org.springframework.stereotype.Component;

@Component
public class PredictionMapperImpl implements PredictionMapper {

    @Override
    public Prediction convertToPrediction(PredictionDto predictionDto) {
        if (predictionDto == null) {
            return null;
        }

        Prediction.PredictionBuilder prediction = Prediction.builder();

        return prediction.startDate(predictionDto.getStartDate())
                .finishDate(predictionDto.getFinishDate())
                .build();
    }

    @Override
    public PredictionDto convertToPredictionDto(Prediction prediction) {
        if (prediction == null) {
            return null;
        }

        PredictionDto.PredictionDtoBuilder dto = PredictionDto.builder();

        return dto.id(prediction.getId())
                .startDate(prediction.getStartDate())
                .finishDate(prediction.getFinishDate())
                .build();
    }
}
