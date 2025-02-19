package bio.repository;

import bio.domain.BioProduct;
import bio.repository.search.BioProductSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BioProductRepository extends JpaRepository<BioProduct, String>, BioProductSearch{
    //효능군별 개수를 반환하는 쿼리를 추가.
//    @Query("SELECT b.efficacyGroup, COUNT(b) FROM BioProduct b GROUP BY b.efficacyGroup")
//    List<Object[]> countByEfficacyGroup();
}








