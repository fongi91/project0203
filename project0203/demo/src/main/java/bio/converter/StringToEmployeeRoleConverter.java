package bio.converter;

import bio.domain.EmployeeRole;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

@Component
public class StringToEmployeeRoleConverter implements Converter<String, EmployeeRole> {
    @Override
    public EmployeeRole convert(String source) {
        try {
            int ordinal = Integer.parseInt(source); // String을 int로 변환
            return EmployeeRole.values()[ordinal]; // int 값을 Enum으로 변환
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid EmployeeRole value: " + source);
        }
    }
}
