package bio.repository.search;

import bio.domain.BioInvReceiving;
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
public class BioInvReceivingSearchImpl implements BioInvReceivingSearch {
    private final EntityManager entityManager;

    @Override
    public Page<BioInvReceiving> searchAll(String[] types, String keyword, String dateKeyword, Pageable pageable) {
        String baseQuery = "SELECT b FROM BioInvReceiving b WHERE b.receivingId > 0";
        String countQueryBase = "SELECT COUNT(b) FROM BioInvReceiving b WHERE b.receivingId > 0";

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
                    case "q": typeConditions.add("CAST(b.quantity AS string) LIKE :keyword"); break;
                    case "c": typeConditions.add("b.customer LIKE :keyword"); break;
                    case "l": typeConditions.add("b.warehouseLocation LIKE :keyword"); break;
                    case "r": typeConditions.add("b.registeredBy LIKE :keyword"); break;
                }
            }
            if (!typeConditions.isEmpty()) {
                conditions.add("(" + String.join(" OR ", typeConditions) + ")");
            }
        }

        //  출고일자 검색 조건 추가
        if (hasDateKeyword) {
            conditions.add("b.receivingDate = :receivingDate");
        }

        //  WHERE 조건을 동적으로 추가
        String conditionString = conditions.isEmpty() ? "" : " AND " + String.join(" AND ", conditions);
        String finalQuery = baseQuery + conditionString + " ORDER BY b.receivingId DESC";
        String countQueryFinal = countQueryBase + conditionString;

        //  JPQL 쿼리 실행
        TypedQuery<BioInvReceiving> query = entityManager.createQuery(finalQuery, BioInvReceiving.class);
        TypedQuery<Long> countQuery = entityManager.createQuery(countQueryFinal, Long.class);

        //  검색어 파라미터 설정 (존재할 때만)
        if (hasKeyword) {
            query.setParameter("keyword", "%" + keyword + "%");
            countQuery.setParameter("keyword", "%" + keyword + "%");
        }

        if (hasDateKeyword) {
            LocalDate date = LocalDate.parse(dateKeyword);
            query.setParameter("receivingDate", date);
            countQuery.setParameter("receivingDate", date);
        }

        //  페이징 처리
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<BioInvReceiving> list = query.getResultList();
        long count = countQuery.getSingleResult();

        return new PageImpl<>(list, pageable, count);
    }

}



