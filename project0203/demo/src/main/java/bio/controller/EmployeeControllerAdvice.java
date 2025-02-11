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

    @ModelAttribute("isAdmin")
    public boolean addisAdminToModel(HttpSession session) {
        // 세션에서 로그인한 직원 정보를 가져옴
        Employees employees = (Employees) session.getAttribute("employees");

        // employees가 null이 아니고, role이 ADMIN과 같으면 true
        boolean result = (employees != null) && (employees.getRole().equals(EmployeeRole.ADMIN));

        log.info("isAdmin result: " + result);
        return result;
    }

    @ModelAttribute("isHr")
    public boolean addisHrToModel(HttpSession session) {
        // 세션에서 로그인한 직원 정보를 가져옴
        Employees employees = (Employees) session.getAttribute("employees");

        // employees가 null이 아니고, role이 HR과 같으면 true
        boolean result = (employees != null) && (employees.getRole().equals(EmployeeRole.HR));

        log.info("isHr result: " + result);
        return result;

    }

    @ModelAttribute("isLogistics")
    public boolean addisLogisticsToModel(HttpSession session) {
        // 세션에서 로그인한 직원 정보를 가져옴
        Employees employees = (Employees) session.getAttribute("employees");

        // employees가 null이 아니고, role이 LOGISTICS과 같은지 확인
        boolean result = (employees != null) && (employees.getRole().equals(EmployeeRole.LOGISTICS));

        log.info("isLogistics result: " + result);
        return result;
    }
}
