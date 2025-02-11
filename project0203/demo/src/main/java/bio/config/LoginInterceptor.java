package bio.config;

import bio.domain.Employees;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Employees employees = (Employees) session.getAttribute("employees");

        // 로그인되지 않은 경우, 로그인 페이지로 리디렉션
        if (employees == null) {
            response.sendRedirect("/bio/error");  // 로그인 페이지로 리디렉션
            return false;  // 요청 처리 중단
        }

        return true;  // 로그인 된 경우, 요청을 계속 처리
    }
}

