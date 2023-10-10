package danya.industries.restab.controllers;

import danya.industries.restab.dtos.MaterialDto;
import danya.industries.restab.services.material.MaterialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@Slf4j
@Validated
@RestController
@RequestMapping("/materials")
@Tag(
        name = "Material controller",
        description = "All about materials. Any materials will get, post, delete here."
)
public class MaterialController {
    private final MaterialService materialService;

    public MaterialController(
            MaterialService materialService
    ) {
        this.materialService = materialService;
    }


    @Operation(summary = "Get all work day's materials")
    @GetMapping("/{workDayId}")
    public ResponseEntity<Collection<MaterialDto>> getAllMaterialsFromWorkDay(
            @PathVariable @NotNull @Parameter(description = "Work day id", required = true) Long workDayId
    ) {
        Collection<MaterialDto> collection = materialService.getAllMaterialsFromWorkDay(workDayId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collection);
    }

    @Operation(summary = "Get page of materials")
    @GetMapping("/{workDayId}/{numPage}")
    public ResponseEntity<Collection<MaterialDto>> getPageOfMaterials(
            @PathVariable @NotNull @Parameter(description = "Work day id", required = true) Long workDayId,
            @PathVariable @NotNull @Parameter(description = "Page number", required = true) Integer numPage,
            @RequestBody @NotNull @NotBlank @Parameter(description = "Material type", required = true) String materialType
    ) {
        Collection<MaterialDto> collection = materialService.getPageOfMaterials(workDayId, materialType, numPage);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collection);
    }

    @Operation(summary = "Add material")
    @PostMapping("/{workDayId}")
    public ResponseEntity<MaterialDto> addMaterial(
            @PathVariable @NotNull @Parameter(description = "Work day id", required = true) Long workDayId,
            @RequestParam("material") @NotNull @Valid @Parameter(description = "Material dto", required = true) MaterialDto materialDto,
            @RequestParam("value") @NotNull @Parameter(description = "Value photo") MultipartFile valueFile,
            @RequestParam("consignmentNote") @NotNull @Parameter(description = "Consignment note photo") MultipartFile consignmentNoteFile
    ) {
        materialDto = materialService.addMaterial(workDayId, materialDto, valueFile, consignmentNoteFile);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(materialDto);
    }

    @Operation(summary = "Delete material")
    @DeleteMapping("/{workdayId}/{materialId}")
    public ResponseEntity<?> deleteMaterial(
            @PathVariable @NotNull @Parameter(description = "Work day id", required = true) Long workdayId,
            @PathVariable @NotNull @Parameter(description = "Material id", required = true) Long materialId
    ) {
        materialService.deleteMaterial(workdayId, materialId);

        return ResponseEntity
                .noContent()
                .build();
    }
}
