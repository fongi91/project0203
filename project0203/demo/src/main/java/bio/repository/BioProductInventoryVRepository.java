package bio.repository;

import bio.domain.BioProductInventoryV;
import bio.repository.search.BioProductInventoryVSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BioProductInventoryVRepository extends JpaRepository<BioProductInventoryV, Long>, BioProductInventoryVSearch {
    @Query("SELECT bpi.productCode, bpi.productName, " +
            "SUM(bpi.totalReceived) AS totalReceived, " +
            "SUM(bpi.totalShipped) AS totalShipped, " +
            "SUM(bpi.stockQuantity) AS stockQuantity " +
            "FROM BioProductInventoryV bpi " +
            "GROUP BY bpi.productCode, bpi.productName")
    List<Object[]> getBioProductInventoryVData();
}