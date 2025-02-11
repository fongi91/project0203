package bio.repository.search;

import bio.domain.Employees;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeesSearch {
    Page<Employees> searchAll(String[] types, String keyword, Pageable pageable);
}
