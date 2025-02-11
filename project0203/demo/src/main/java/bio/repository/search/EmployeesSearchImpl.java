package bio.repository.search;

import bio.domain.Employees;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeesSearchImpl implements EmployeesSearch{
    private final EntityManager entityManager;

    @Override
    public Page<Employees> searchAll(String[] types, String keyword, Pageable pageable){
        StringBuilder jpql = new StringBuilder("SELECT b FROM Employees b WHERE b.eno > 0");

        if ((types != null && types.length > 0) && keyword != null){
            jpql.append(" AND (");

            for (int i = 0; i < types.length; i++){
                String type = types[i];
                switch (type){
                    case "i":
                        jpql.append("b.employeeid LIKE :keyword");
                        break;
                    case "n":
                        jpql.append("b.employeename LIKE :keyword");
                        break;
                    case "d":
                        jpql.append("b.department LIKE :keyword");
                        break;
                    case "p":
                        jpql.append("b.position LIKE :keyword");
                        break;
                    case "c":
                        jpql.append("b.contactnumber LIKE :keyword");
                        break;
                }
                if (i < types.length - 1) {
                    jpql.append(" OR ");
                }
            }
            jpql.append(")");
        }
        jpql.append(" ORDER BY eno DESC");

        TypedQuery<Employees> query = entityManager.createQuery(jpql.toString(), Employees.class);
        TypedQuery<Long> countQuery = entityManager.createQuery(
                jpql.toString().replace("SELECT b", "SELECT COUNT(b)"), Long.class
        );

        if ((types != null && types.length > 0) && keyword != null){
            query.setParameter("keyword", "%" + keyword + "%");
            countQuery.setParameter("keyword", "%" + keyword + "%");
        }

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Employees> list = query.getResultList();
        long count = countQuery.getSingleResult();

        return new PageImpl<>(list, pageable, count);
    }
}
