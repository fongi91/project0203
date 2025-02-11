package bio.repository;

import bio.domain.Employees;
import bio.repository.search.EmployeesSearch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeesRepository extends JpaRepository<Employees, Long>, EmployeesSearch {
    //Page<Employees> searchAll(String[] types, String keyword, Pageable pageable);
    Optional<Employees> findByEmployeeId(String employeeId);
}
