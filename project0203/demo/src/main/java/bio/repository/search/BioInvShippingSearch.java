package bio.repository.search;

import bio.domain.BioInvShipping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BioInvShippingSearch {
    Page<BioInvShipping> searchAll(String[] types, String keyword, String dateKeyword, Pageable pageable);
}
