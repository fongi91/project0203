package bio.service;

import bio.domain.Employees;
import bio.dto.EmployeesDTO;
import bio.dto.EmployeesPageRequestDTO;
import bio.dto.EmployeesPageResponseDTO;
import bio.repository.EmployeesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class EmployeesServiceImpl implements EmployeesService{
    private final ModelMapper modelMapper;
    private final EmployeesRepository employeesRepository;

    @Override
    public Long register(EmployeesDTO employeesDTO){
        Employees employees = modelMapper.map(employeesDTO, Employees.class);
        Long eno = employeesRepository.save(employees).getEno();
        return eno;
    }

    @Override
    public EmployeesDTO readOne(Long eno){
        Optional<Employees> result = employeesRepository.findById(eno);
        Employees employees = result.orElseThrow();
        EmployeesDTO employeesDTO = modelMapper.map(employees, EmployeesDTO.class);
        return employeesDTO;
    }

    @Override
    public void modify(EmployeesDTO employeesDTO){
        Optional<Employees> result = employeesRepository.findById(employeesDTO.getEno());
        Employees employees = result.orElseThrow();
        employees.change(employeesDTO.getEmployeeid(), employeesDTO.getEmployeename(), employeesDTO.getPassword(), employeesDTO.getHiredate(), employeesDTO.getTerminationdate(), employeesDTO.getDepartment(), employeesDTO.getPosition(), employeesDTO.getContactnumber());
        employeesRepository.save(employees);
    }

    @Override
    public void remove(Long eno){
        employeesRepository.deleteById(eno);
    }

    @Override
    public EmployeesPageResponseDTO<EmployeesDTO> list(EmployeesPageRequestDTO employeesPageRequestDTO){
        String[] types = employeesPageRequestDTO.getTypes();
        String keyword = employeesPageRequestDTO.getKeyword();
        Pageable pageable = employeesPageRequestDTO.getPageable("eno");

        Page<Employees> result = employeesRepository.searchAll(types, keyword, pageable);

        List<EmployeesDTO> dtoList = result.getContent().stream().map(employees -> modelMapper.map(employees, EmployeesDTO.class)).collect(Collectors.toList());

        return EmployeesPageResponseDTO.<EmployeesDTO>withAll()
                .employeesPageRequestDTO(employeesPageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

}
