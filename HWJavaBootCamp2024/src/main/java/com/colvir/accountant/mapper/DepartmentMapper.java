package com.colvir.accountant.mapper;

import com.colvir.accountant.dto.DepartmentResponse;
import com.colvir.accountant.dto.DeptPageResponse;
import com.colvir.accountant.dto.GenerateDeptResponse;
import com.colvir.accountant.dto.UpdateDeptRequest;
import com.colvir.accountant.model.Department;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface DepartmentMapper {

    GenerateDeptResponse deptToGenerateDeptResponse(Department department);

    DepartmentResponse deptToDeptResponse(Department department);

    List<DepartmentResponse> departmentsToDeptResponse(List<Department> departments);

    Department updateDeptRequestToDept(UpdateDeptRequest request);

    default DeptPageResponse departmentsToDeptPageResponse(List<Department> departments) {
        List<DepartmentResponse> departmentResponses = departmentsToDeptResponse(departments);
        return  new DeptPageResponse(departmentResponses);
    }
}
