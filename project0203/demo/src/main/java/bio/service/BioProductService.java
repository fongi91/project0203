package bio.service;

import bio.dto.BioProductDTO;
import bio.dto.BioProdutPageRequestDTO;
import bio.dto.BioProductPageResponseDTO;

import java.util.List;

public interface BioProductService {
    //제품 등록 서비스 로직 메서드
    Long register(BioProductDTO bioProductDTO);

    BioProductDTO readOne(Long bioNo);

    void modify(BioProductDTO bioProductDTO);

    void remove(Long bioNo);

    //목록 검색기능 선언
    BioProductPageResponseDTO list(BioProdutPageRequestDTO bioProdutPageRequestDTO);

    List<Object[]> getEfficacyGroupDistribution();


}
