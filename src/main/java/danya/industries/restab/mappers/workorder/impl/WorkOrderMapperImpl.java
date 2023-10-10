package danya.industries.restab.mappers.workorder.impl;

import danya.industries.restab.dtos.WorkOrderDto;
import danya.industries.restab.mappers.workorder.WorkOrderMapper;
import danya.industries.restab.models.WorkOrder;
import org.springframework.stereotype.Component;

@Component
public class WorkOrderMapperImpl implements WorkOrderMapper {

    @Override
    public WorkOrderDto convertToWorkOrderDto(WorkOrder workOrder) {
        if (workOrder == null) {
            return null;
        }
        WorkOrderDto.WorkOrderDtoBuilder dto = WorkOrderDto.builder();

        return dto.id(workOrder.getId())
                .name(workOrder.getName())
                .description(workOrder.getDescription())
                .percentProgress(workOrder.getPercentProgress())
                .build();
    }

    @Override
    public WorkOrder convertToWorkOrder(WorkOrderDto workOrderDto) {
        if (workOrderDto == null) {
            return null;
        }

        WorkOrder.WorkOrderBuilder workOrder = WorkOrder.builder();

        return workOrder.name(workOrderDto.getName())
                .description(workOrderDto.getDescription())
                .percentProgress(workOrderDto.getPercentProgress())
                .build();
    }
}
