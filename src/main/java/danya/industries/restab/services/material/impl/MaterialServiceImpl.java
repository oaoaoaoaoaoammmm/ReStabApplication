package danya.industries.restab.services.material.impl;

import danya.industries.restab.dtos.MaterialDto;
import danya.industries.restab.mappers.material.MaterialMapper;
import danya.industries.restab.models.Material;
import danya.industries.restab.models.WorkDay;
import danya.industries.restab.props.MaterialsProps;
import danya.industries.restab.repositories.MaterialRepository;
import danya.industries.restab.repositories.WorkDayRepository;
import danya.industries.restab.services.material.MaterialService;
import danya.industries.restab.services.picture.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@Transactional(
        readOnly = true,
        propagation = Propagation.REQUIRED,
        isolation = Isolation.READ_COMMITTED
)
public class MaterialServiceImpl implements MaterialService {
    private final MaterialsProps materialsProps;
    private final MaterialMapper materialMapper;
    private final PictureService pictureService;
    private final MaterialRepository materialRepository;
    private final WorkDayRepository workDayRepository;

    public MaterialServiceImpl(
            MaterialsProps materialsProps,
            MaterialMapper materialMapper,
            PictureService pictureService,
            MaterialRepository materialRepository,
            WorkDayRepository workDayRepository
    ) {
        this.materialsProps = materialsProps;
        this.materialMapper = materialMapper;
        this.pictureService = pictureService;
        this.materialRepository = materialRepository;
        this.workDayRepository = workDayRepository;
    }

    @Override
    public Collection<MaterialDto> getAllMaterialsFromWorkDay(Long workDayId) {
        log.debug("Finding all materials from work day by its id - {}", workDayId);

        WorkDay workDay = workDayRepository
                .findById(workDayId)
                .orElseThrow(() -> new NoSuchElementException("Can't find work day"));

        List<Material> materials = workDay.getMaterials();

        return materials.stream()
                .map(materialMapper::convertToMaterialDto)
                .toList();
    }

    @Override
    public Collection<MaterialDto> getPageOfMaterials(Long workDayId, String materialType, Integer numPage) {
        log.debug("Get page of materials with material type - {} by work day id - {}", materialType, workDayId);
        Pageable pageable = PageRequest.of(numPage, materialsProps.getPageSize());

        WorkDay workDay = workDayRepository
                .findById(workDayId)
                .orElseThrow(() -> new NoSuchElementException("Can't find work day"));

        return materialRepository.findAllByWorkDayAndMaterialType(
                        workDay,
                        materialType,
                        pageable
                )
                .stream()
                .map(materialMapper::convertToMaterialDto)
                .toList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public MaterialDto addMaterial(Long workDayId, MaterialDto materialDto, MultipartFile valueFile, MultipartFile consignmentNoteFile) {
        log.debug("Adding material in work day by its id - {}", workDayId);

        Material material = materialMapper.convertToMaterial(materialDto);
        WorkDay workDay = workDayRepository
                .findById(workDayId)
                .orElseThrow(() -> new NoSuchElementException("Can't find work day"));
        workDay.addMaterial(material);

        //TODO Create PictureService and connect its with Yandex Object storage
        pictureService.savePicture(material.getValuePictureId(), valueFile);
        pictureService.savePicture(material.getConsignmentNotePictureId(), consignmentNoteFile);

        workDayRepository.flush();

        return materialMapper.convertToMaterialDto(material);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void deleteMaterial(Long workDayId, Long materialId) {
        log.debug("Deleting material in work day by its id - {}", workDayId);

        WorkDay workDay = workDayRepository
                .findById(workDayId)
                .orElseThrow(() -> new NoSuchElementException("Can't find work day"));
        Material material = materialRepository
                .findById(materialId)
                .orElseThrow(() -> new NoSuchElementException("Can't find material"));

        workDay.removeMaterial(material);
    }
}
