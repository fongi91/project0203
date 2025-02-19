package bio.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Getter
@Builder
public class Employees extends EmployeesEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eno;
    @Column(length = 50, nullable = false, unique = true)
    private String employeeid;
    @Column(length = 100, nullable = false)
    private String employeename;
    @Column(length = 255, nullable = false)
    private String password;

    private LocalDate hiredate;

    private LocalDate terminationdate;
    @Column(length = 50, nullable = false)
    private String department;
    @Column(length = 50, nullable = false)
    private String position;
    @Column(length = 50, nullable = false)
    private String contactnumber;
    @Column(length = 50, nullable = false)
    private String registeredby;

    @Enumerated(EnumType.ORDINAL)
    private EmployeeRole role;

    public void change(String employeeid,String employeename, String password, LocalDate hiredate, LocalDate terminationdate, String department, String position, String contactnumber){
        this.employeeid = employeeid;
        this.employeename = employeename;
        this.password = password;
        this.hiredate = hiredate;
        this.terminationdate = terminationdate;
        this.department = department;
        this.position = position;
        this.contactnumber = contactnumber;
    }
}
