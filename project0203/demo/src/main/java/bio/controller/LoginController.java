package bio.controller;

import bio.service.BioProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import bio.domain.EmployeeRole;
import bio.dto.LoginDTO;
import bio.domain.Employees;
import bio.service.LoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/bio")
public class LoginController {

    private final LoginService loginService;
    private final BioProductService bioProductService;

    public static Hashtable<String, HttpSession> sessionList = new Hashtable<>();

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        // 세션에서 사용자 정보 가져오기
        Employees employees = (Employees) session.getAttribute("employees");

        if (employees != null) {
            log.info("대시보드 접근 - 직원 정보: " + employees);
            log.info("대시보드 접근 - 직원 Role: " + employees.getRole());

            model.addAttribute("employee", employees);
            model.addAttribute("employeeName", employees.getEmployeename());
//            model.addAttribute("isAdmin", employees.getRole().equals(EmployeeRole.ADMIN));  // 관리자 여부 추가
//            model.addAttribute("isHr", employees.getRole().equals(EmployeeRole.HR));        // 인사팀
//            model.addAttribute("isLogistics", employees.getRole().equals(EmployeeRole.LOGISTICS));  // 물류팀
            List<Object[]> efficacyGroupData = bioProductService.getEfficacyGroupDistribution();
            model.addAttribute("efficacyGroupData", efficacyGroupData);  // 모델에 데이터 담기

            log.info("Employee in session: " + employees);

            return "bio/dashboard"; // 대시보드 페이지로 이동
        } else {
            return "redirect:/bio/login"; // 로그인하지 않으면 로그인 페이지로 이동
        }

    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        return "bio/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginDTO loginDTO,
                        HttpSession session,
                        Model model,
                        BindingResult bindingResult,
                        HttpServletRequest httpServletRequest) {
        Employees employees = loginService.login(loginDTO, session);

        log.info("login employees: " + employees);

        // 로그인 아이디나 비밀번호가 틀린 경우 error return
        if(employees == null) {
            model.addAttribute("error", "아이디 또는 비밀번호가 틀렸습니다.");
            return "bio/login";
        }

        if(bindingResult.hasErrors()) {
            log.info("has errors...........");
            return "bio/login";
        }

        // 로그인 성공 -> 세션 생성
        // 세션을 생성하기 전에 기존의 세션 파기
        if (session != null) {
            session.invalidate();
        }

        HttpSession newSession = httpServletRequest.getSession(true);   // 세션 생성
        newSession.setAttribute("employees", employees);          // 세션에 userId를 넣어줌
        newSession.setMaxInactiveInterval(1800);           // 세션 30분 동안 유지

        sessionList.put(newSession.getId(), newSession);

        log.info("로그인 성공 - 세션 저장됨: " + newSession.getAttribute("employees"));
        log.info("로그인 성공 - 직원 Role: " + employees.getRole());
        return "redirect:/bio/dashboard";


    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);  // Session이 없으면 null return
        if(session != null) {
            sessionList.remove(session.getId());
            session.invalidate();
        }

        return "redirect:/bio/login";
    }

}
