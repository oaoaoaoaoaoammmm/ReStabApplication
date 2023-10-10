package danya.industries.restab.mappers.material.impl;

import danya.industries.restab.dtos.MaterialDto;
import danya.industries.restab.mappers.material.MaterialMapper;
import danya.industries.restab.models.Material;
import org.springframework.stereotype.Component;

@Component
public class MaterialMapperImpl implements MaterialMapper {

    @Override
    public MaterialDto convertToMaterialDto(Material material) {
        if (material == null) {
            return null;
        }

        MaterialDto.MaterialDtoBuilder dto = MaterialDto.builder();

        return dto.id(material.getId())
                .localTime(material.getLocalTime())
                .materialType(material.getMaterialType())
                .value(material.getValue())
                .valuePictureId(material.getValuePictureId())
                .consignmentNotePictureId(material.getConsignmentNotePictureId())
                .build();
    }

    @Override
    public Material convertToMaterial(MaterialDto materialDto) {
        if (materialDto == null) {
            return null;
        }

        Material.MaterialBuilder material = Material.builder();

        return material.id(materialDto.getId())
                .localTime(materialDto.getLocalTime())
                .materialType(materialDto.getMaterialType())
                .value(materialDto.getValue())
                .valuePictureId(materialDto.getValuePictureId())
                .consignmentNotePictureId(materialDto.getConsignmentNotePictureId())
                .build();
    }
}
