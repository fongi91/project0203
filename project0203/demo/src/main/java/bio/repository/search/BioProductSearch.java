package bio.repository.search;

import bio.domain.BioProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BioProductSearch {
    Page<BioProduct> searchAll(String[] types, String keyword, Pageable pageable);
}
