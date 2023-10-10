package danya.industries.restab.mappers.workorder;

import danya.industries.restab.dtos.WorkOrderDto;
import danya.industries.restab.models.WorkOrder;

public interface WorkOrderMapper {
    WorkOrderDto convertToWorkOrderDto(WorkOrder workOrder);
    WorkOrder convertToWorkOrder(WorkOrderDto workOrderDto);

}
