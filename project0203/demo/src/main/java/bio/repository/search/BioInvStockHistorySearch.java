package bio.repository.search;

import bio.domain.BioInvStockHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BioInvStockHistorySearch {
    Page<BioInvStockHistory> searchAll(String[] types, String keyword, String dateKeyword, Pageable pageable);
}
