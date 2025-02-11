package bio.controller;

import bio.domain.EmployeeRole;
import bio.domain.Employees;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;

@Log4j2
@ControllerAdvice
public class EmployeeControllerAdvice {

    @ModelAttribute("employee")
    public Employees addEmployeeToModel(HttpSession session) {
        Employees employees = (Employees) session.getAttribute("employees");
        log.info("EmployeeControllerAdvice - employee: " + employees);
        return employees;
    }

    @ModelAttribute("employeename")
    public String addEmployeeNameToModel(HttpSession session) {
        Employees employees = (Employees) session.getAttribute("employees");

        if (employees != null) {
            return employees.getEmployeename();
        } else { return null; }
    }

}
