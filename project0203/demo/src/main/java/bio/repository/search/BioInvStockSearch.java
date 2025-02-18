package bio.repository.search;

import bio.domain.BioInvStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BioInvStockSearch {
    Page<BioInvStock> searchAll(String[] types, String keyword, String dateKeyword, Pageable pageable);
}
