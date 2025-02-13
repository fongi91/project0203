package bio.service;

import bio.dto.EmployeesDTO;
import bio.dto.EmployeesPageRequestDTO;
import bio.dto.EmployeesPageResponseDTO;

public interface EmployeesService {
    Long register(EmployeesDTO employeesDTO);

    EmployeesDTO readOne(Long eno);

    void modify(EmployeesDTO employeesDTO);

    void remove(Long eno);

    EmployeesPageResponseDTO<EmployeesDTO> list(EmployeesPageRequestDTO employeesPageRequestDTO);

}