package bio.service;

import bio.domain.Employees;
import bio.dto.LoginDTO;
import bio.repository.EmployeesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

    private final EmployeesRepository employeesRepository;

    // 로그인 기능
    @Override
    public Employees login(LoginDTO loginDTO, HttpSession session) {
        Optional<Employees> optionalEmployees = employeesRepository.findByEmployeeid(loginDTO.getEmployeeid());

        // employeeId와 일치하는 사원 아이디가 없으면 null return
        if(optionalEmployees.isEmpty()) {
            return null;
        }

        Employees employees = optionalEmployees.get();

        // 저장된 사원 테이블의 password와 입력된 password가 다르면 null return
        if(!employees.getPassword().equals(loginDTO.getPassword())) {
            return null;
        }

        // 추가
        // 세션에 사용자 정보 저장
        session.setAttribute("employees", employees);

        return employees;
    }

    @Override
    public void logout(HttpSession session) {
        session.invalidate(); // 세션 삭제
    }

//    @Override
//    public User getLoginUserById(Long eno) {
//        // eno가 없으면(로그아웃 상태 혹은 eno를 찾지 못한 경우) null return
//        if(eno == null) return null;
//
//        Optional<User> optionalUser = userRepository.findById(eno);
//
//        if(optionalUser.isEmpty()) return null;
//
//        // eno로 가져온 User가 존재하면 User return
//        return optionalUser.get();
//    }

}
