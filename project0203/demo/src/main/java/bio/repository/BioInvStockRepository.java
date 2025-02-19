package bio.repository;

import bio.domain.BioInvStock;
import bio.repository.search.BioInvStockSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BioInvStockRepository extends JpaRepository<BioInvStock, Long>, BioInvStockSearch {
    @Query("SELECT b.productCode, SUM(b.stockQuantity) AS totalReceived, " +
            "SUM(CASE WHEN b.stockStatus = 'Shipped' THEN b.stockQuantity ELSE 0 END) AS totalShipped " +
            "FROM BioInvStock b GROUP BY b.productCode")
    List<Object[]> getReceivedAndShippedQuantities();

    // 입고량과 출고량(stockStatus가 shipped 인 경우)을 조회
}
