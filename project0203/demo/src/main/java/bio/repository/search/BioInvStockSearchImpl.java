package bio.repository.search;

import bio.domain.BioInvStock;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BioInvStockSearchImpl implements BioInvStockSearch {
    private final EntityManager entityManager;

    @Override
    public Page<BioInvStock> searchAll(String[] types, String keyword, String dateKeyword, Pageable pageable) {
        // 기본 쿼리
        String baseQuery = "SELECT b FROM BioInvStock b";
        String countQueryBase = "SELECT COUNT(b) FROM BioInvStock b";

        List<String> conditions = new ArrayList<>();
        boolean hasKeyword = keyword != null && !keyword.isEmpty();
        boolean hasDateKeyword = dateKeyword != null && !dateKeyword.isEmpty();
        boolean hasTypes = types != null && types.length > 0;

        //  검색 타입이 있을 경우 해당 필드 검색
        if (hasKeyword && hasTypes) {
            List<String> typeConditions = new ArrayList<>();
            for (String type : types) {
                switch (type) {
                    case "p": typeConditions.add("b.productCode LIKE :keyword"); break;
                    case "q": typeConditions.add("CAST(b.stockQuantity AS string) LIKE :keyword"); break;
                    case "l": typeConditions.add("b.warehouseLocation LIKE :keyword"); break;
                    case "r": typeConditions.add("b.shelfLife LIKE :keyword"); break;
                }
            }
            if (!typeConditions.isEmpty()) {
                conditions.add("(" + String.join(" OR ", typeConditions) + ")");
            }
        }

        //  WHERE 조건을 동적으로 추가
        String conditionString = conditions.isEmpty() ? "" : " WHERE " + String.join(" AND ", conditions);
        String finalQuery = baseQuery + conditionString + " ORDER BY b.shelfLife DESC";
        String countQueryFinal = countQueryBase + conditionString;

        //  JPQL 쿼리 실행
        TypedQuery<BioInvStock> query = entityManager.createQuery(finalQuery, BioInvStock.class);
        TypedQuery<Long> countQuery = entityManager.createQuery(countQueryFinal, Long.class);

        //  검색어 파라미터 설정 (존재할 때만)
        if (hasKeyword) {
            query.setParameter("keyword", "%" + keyword + "%");
            countQuery.setParameter("keyword", "%" + keyword + "%");
        }

        //  페이징 처리
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<BioInvStock> list = query.getResultList();
        long count = countQuery.getSingleResult();

        return new PageImpl<>(list, pageable, count);
    }
}
