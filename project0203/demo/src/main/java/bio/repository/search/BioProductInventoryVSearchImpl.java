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

@Service
@RequiredArgsConstructor
public class BioProductInventoryVSearchImpl implements BioProductInventoryVSearch {
    private final EntityManager entityManager;

    @Override
    public Page<BioProductInventoryV> searchAll(String[] types, String keyword, Pageable pageable) {
        StringBuilder jpql = new StringBuilder("SELECT b FROM BioProductInventoryV b WHERE b.no > 0");
        if ((types != null && types.length > 0) && keyword != null) {
            jpql.append(" AND (");
            for (int i = 0; i < types.length; i++) {
                String type = types[i];
                switch (type) {
                    case "n":
                        jpql.append("b.productName Like :keyword");
                        break;
                    case "c":
                        jpql.append("b.currentCategory Like :keyword");
                        break;
                    case "t":
                        jpql.append("b.productionType Like :keyword");
                        break;
                }
                if (i < types.length - 1) {
                    jpql.append(" OR ");
                }
                jpql.append(")");
            }
            jpql.append(" ORDER BY b.no ASC");
        }

        TypedQuery<BioProductInventoryV> query = entityManager.createQuery(jpql.toString(), BioProductInventoryV.class);
        TypedQuery<Long> countQuery = entityManager.createQuery(
                jpql.toString().replace("SELECT b", "SELECT COUNT(b)"), Long.class
        );

        //파라미터바인딩
        if ((types != null && types.length > 0) && keyword != null) {
            query.setParameter("keyword", "%" + keyword + "%");
            countQuery.setParameter("keyword", "%" + keyword + "%");
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

