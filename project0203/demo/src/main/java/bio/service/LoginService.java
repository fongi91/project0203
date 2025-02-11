package bio.service;

import bio.dto.LoginDTO;
import bio.domain.Employees;

import javax.servlet.http.HttpSession;

public interface LoginService {

    Employees login(LoginDTO loginDTO, HttpSession session);

    // 추가
    void logout(HttpSession session);
}
