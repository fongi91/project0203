//package bio.domain;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//import java.util.Date;
//
//@Entity
//@Builder
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long eno;       // 사원 번호
//
//    @Column(length = 50, name = "employeeid")
//    private String employeeId;      // 사원 id
//
//    @Column(length = 255, nullable = false)
//    private String password;        // 비밀번호
//
//    @Column(length = 100, nullable = false, name = "employeename")
//    private String employeeName;    // 사원 이름
//
//    private EmployeeRole role;
//
//    @Column(name = "hiredate")
//    private Date hireDate;          // 입사년월일
//
//    @Column(name = "terminationdate")
//    private Date terminationDate;   // 퇴사년월일
//
//    @Column(length = 50)
//    private String department;      // 부서
//
//    @Column(length = 50)
//    private String position;        // 직급
//
//    @Column(length = 50, name = "contactnumber")
//    private String contactNumber;   // 연락처
//
//    @Column(length = 50, name = "registeredby")
//    private String registeredBy;        // 등록자
//
//    @Column(name = "registereddate")
//    private LocalDateTime registeredDate; // 등록일자
//}