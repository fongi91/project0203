package bio.repository;

import bio.domain.BioInvShipping;
import bio.repository.search.BioInvShippingSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BioInvShippingRepository extends JpaRepository<BioInvShipping, Long>, BioInvShippingSearch {
}
