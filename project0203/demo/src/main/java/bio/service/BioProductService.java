package bio.service;

import bio.dto.BioProductDTO;
import bio.dto.BioProductPageRequestDTO;
import bio.dto.BioProductPageResponseDTO;
import bio.repository.BioProductRepository;
import org.springframework.cglib.core.DuplicatesPredicate;

import java.util.List;
import java.util.Map;

public interface BioProductService {
    //제품 등록 서비스 로직 메서드
    public String register(BioProductDTO bioProductDTO);

    BioProductDTO readOne(String productCode);

    void modify(BioProductDTO bioProductDTO);

    void remove(String productCode);

    //목록 검색기능 선언
    BioProductPageResponseDTO list(BioProductPageRequestDTO bioProductPageRequestDTO);

    List<Map<String, Object>> getEfficacyGroupDistribution();


}
