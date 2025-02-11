package bio.repository;

import bio.domain.BioInvReceiving;
import bio.repository.search.BioInvReceivingSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BioInvReceivingRepository extends JpaRepository<BioInvReceiving, Long>, BioInvReceivingSearch {
}
