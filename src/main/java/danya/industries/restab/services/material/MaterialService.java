package danya.industries.restab.services.material;

import danya.industries.restab.dtos.MaterialDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

public interface MaterialService {

    Collection<MaterialDto> getAllMaterialsFromWorkDay(Long workDayId);
    Collection<MaterialDto> getPageOfMaterials(Long workDayId, String materialType, Integer numPage);

    MaterialDto addMaterial(Long workDayId, MaterialDto materialDto, MultipartFile valueFile, MultipartFile consignmentNoteFile);

    void deleteMaterial(Long workDayId, Long materialId);
}
