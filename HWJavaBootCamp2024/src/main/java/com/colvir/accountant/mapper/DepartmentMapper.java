package com.colvir.accountant.mapper;

<<<<<<< HEAD
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

=======
>>>>>>> master
import com.colvir.accountant.dto.DepartmentResponse;
import com.colvir.accountant.dto.DeptPageResponse;
import com.colvir.accountant.dto.GenerateDeptResponse;
import com.colvir.accountant.dto.UpdateDeptRequest;
import com.colvir.accountant.model.Department;
<<<<<<< HEAD
=======
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
>>>>>>> master

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface DepartmentMapper {

    GenerateDeptResponse deptToGenerateDeptResponse(Department department);

    DepartmentResponse deptToDeptResponse(Department department);

    List<DepartmentResponse> departmentsToDeptResponse(List<Department> departments);

<<<<<<< HEAD
//    Department updateDeptRequestToDept(UpdateDeptRequest request);

    default Department updateDeptRequestToDept(@MappingTarget Department deptForUpdate, UpdateDeptRequest request)
    {
        String name = request.getName();
        if (name != null) {
            deptForUpdate.setName(name);
        }

        String code = request.getCode();
        if (code != null) {
            deptForUpdate.setCode(code);
        }

        return deptForUpdate;

    }
=======
    Department updateDeptRequestToDept(UpdateDeptRequest request);
>>>>>>> master

    default DeptPageResponse departmentsToDeptPageResponse(List<Department> departments) {
        List<DepartmentResponse> departmentResponses = departmentsToDeptResponse(departments);
        return  new DeptPageResponse(departmentResponses);
    }
}
