package bio.controller;

import bio.domain.EmployeeRole;
import bio.domain.Employees;
import bio.service.BioProductInventoryVService;
import bio.service.BioProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/bio")
public class DashboardController {
    private final BioProductService bioProductService;
    private final BioProductInventoryVService bioProductInventoryVService;
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        // 세션에서 사용자 정보 가져오기
        Employees employees = (Employees) session.getAttribute("employees");

        if (employees != null) {
            model.addAttribute("employee", employees);
            model.addAttribute("employeeName", employees.getEmployeename());



            List<Object[]> efficacyGroupData = bioProductService.getEfficacyGroupDistribution();
            model.addAttribute("efficacyGroupData", efficacyGroupData);  // 모델에 데이터 담기

            List<Object[]> bioProductInventoryVData = bioProductInventoryVService.getBioProductInventoryVData();
            model.addAttribute("bioProductInventoryVData", bioProductInventoryVData);

            log.info("Employee in session: " + employees);
            log.info("EmployeeRole in session: " + employees.getRole());

            return "bio/dashboard"; // 대시보드 페이지로 이동
        } else {
            return "redirect:/bio/login"; // 로그인하지 않으면 로그인 페이지로 이동
        }
    }

}