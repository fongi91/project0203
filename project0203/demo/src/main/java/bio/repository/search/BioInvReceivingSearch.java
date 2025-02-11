package bio.repository.search;

import bio.domain.BioInvReceiving;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BioInvReceivingSearch {
    Page<BioInvReceiving> searchAll(String[] types, String keyword, String dateKeyword, Pageable pageable);
}
