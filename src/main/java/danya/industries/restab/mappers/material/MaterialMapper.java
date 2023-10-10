package danya.industries.restab.mappers.material;

import danya.industries.restab.dtos.MaterialDto;
import danya.industries.restab.models.Material;

public interface MaterialMapper {
    MaterialDto convertToMaterialDto(Material material);

    Material convertToMaterial(MaterialDto materialDto);
}
