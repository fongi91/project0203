package bio.repository.search;


import bio.domain.BioProduct;
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
public class BioProductSearchImpl implements BioProductSearch{
    private final EntityManager entityManager;

    @Override
    public Page<BioProduct> searchAll(String[] types, String keyword, Pageable pageable){
        StringBuilder jpql = new StringBuilder("SELECT b FROM BioProduct b WHERE b.bioNo > 0");

        if((types != null && types.length>0) && keyword != null){
            jpql.append(" AND (");

            for(int i=0; i<types.length; i++){
                String type = types[i];
                switch(type){
                    case "c":
                        jpql.append("b.currentCategory Like :keyword");
                        break;
                    case "g":
                        jpql.append("b.efficacyGroup Like :keyword");
                        break;
                    case "u":
                        jpql.append("b.packagingUnit Like :keyword");
                        break;
                    case "n":
                        jpql.append("b.productName Like :keyword");
                        break;
                    case "t":
                        jpql.append("b.productionType Like :keyword");
                        break;
                }
                if(i < types.length -1){
                    jpql.append(" OR ");
                }
            }
            jpql.append(")");
        }
        jpql.append(" ORDER BY b.productCode ASC");
        // JPQL로 쿼리 생성
        TypedQuery<BioProduct> query = entityManager.createQuery(jpql.toString(), BioProduct.class);
        TypedQuery<Long> countQuery = entityManager.createQuery(
                jpql.toString().replace("SELECT b", "SELECT COUNT(b)"), Long.class
        );

        // 파라미터 바인딩
        if ((types != null && types.length > 0) && keyword != null) {
            query.setParameter("keyword", "%" + keyword + "%");
            countQuery.setParameter("keyword", "%" + keyword + "%");
        }

        // 페이징 처리
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        // 결과 조회
        List<BioProduct> list = query.getResultList();
        long count = countQuery.getSingleResult();

        return new PageImpl<>(list, pageable, count);
    }
}