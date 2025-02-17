package bio.repository.search;


import bio.domain.BioProductInventoryV;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface BioProductInventoryVSearch {
    Page<BioProductInventoryV> searchAll(String[] types, String keyword, Pageable pageable, String startDate, String endDate);
}
