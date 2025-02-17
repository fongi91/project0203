package bio.repository.search;


import bio.domain.BioProductInventoryV;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class BioProductInventoryVSearchImpl implements BioProductInventoryVSearch {
    private final EntityManager entityManager;

    @Override
    public Page<BioProductInventoryV> searchAll(String[] types, String keyword, Pageable pageable, String startDate, String endDate) {
        StringBuilder jpql = new StringBuilder("SELECT b FROM BioProductInventoryV b WHERE b.no > 0");

        // types와 keyword가 유효할 때만 추가
        if (types != null && types.length > 0 && keyword != null && !keyword.isEmpty()) {
            jpql.append(" AND (");
            for (int i = 0; i < types.length; i++) {
                String type = types[i];
                switch (type) {
                    case "n":
                        jpql.append("b.productName LIKE :keyword");
                        break;
                    case "c":
                        jpql.append("b.currentCategory LIKE :keyword");
                        break;
                    case "t":
                        jpql.append("b.productionType LIKE :keyword");
                        break;
                }

                if (i < types.length - 1) {
                    jpql.append(" OR ");
                }
            }
            jpql.append(")");
        }

        // 날짜 범위 조건 추가
        if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
            if (jpql.indexOf("AND") != -1) {  // 이미 'AND'가 있으면 조건을 추가
                jpql.append(" AND b.date BETWEEN :startDate AND :endDate");
            } else {  // 첫 번째 조건이라면 'AND' 없이 추가
                jpql.append(" AND b.date BETWEEN :startDate AND :endDate");
            }
        }

        jpql.append(" ORDER BY b.no ASC");

        TypedQuery<BioProductInventoryV> query = entityManager.createQuery(jpql.toString(), BioProductInventoryV.class);
        TypedQuery<Long> countQuery = entityManager.createQuery(
                jpql.toString().replace("SELECT b", "SELECT COUNT(b)"), Long.class
        );

        // 파라미터 바인딩 (keyword 처리)
        if (types != null && types.length > 0 && keyword != null && !keyword.isEmpty()) {
            query.setParameter("keyword", "%" + keyword + "%");
            countQuery.setParameter("keyword", "%" + keyword + "%");
        }

        // 날짜 파라미터 바인딩
        if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate parsedStartDate = LocalDate.parse(startDate, formatter);
            LocalDate parsedEndDate = LocalDate.parse(endDate, formatter);

            query.setParameter("startDate", parsedStartDate);
            query.setParameter("endDate", parsedEndDate);
            countQuery.setParameter("startDate", parsedStartDate);
            countQuery.setParameter("endDate", parsedEndDate);
        }

        // 페이징 처리
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        // 결과 조회
        List<BioProductInventoryV> list = query.getResultList();
        long count = countQuery.getSingleResult();

        return new PageImpl<>(list, pageable, count);
    }
}