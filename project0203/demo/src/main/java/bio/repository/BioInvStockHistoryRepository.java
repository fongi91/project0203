package bio.repository;

import bio.domain.BioInvStockHistory;
import bio.repository.search.BioInvStockHistorySearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BioInvStockHistoryRepository extends JpaRepository<BioInvStockHistory, Long>, BioInvStockHistorySearch {
}
