package bio.repository;

import bio.domain.BioInvStock;
import bio.repository.search.BioInvStockSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BioInvStockRepository extends JpaRepository<BioInvStock, Long>, BioInvStockSearch {
}
