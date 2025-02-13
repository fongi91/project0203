package bio.repository;

import bio.domain.BioProductInventoryV;
import bio.repository.search.BioProductInventoryVSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BioProductInventoryVRepository extends JpaRepository<BioProductInventoryV, Long>, BioProductInventoryVSearch {
}
