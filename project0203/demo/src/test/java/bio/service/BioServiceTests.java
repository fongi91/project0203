package bio.service;

import bio.dto.*;
import bio.dto.BioProductInventoryVPageRequestDTO;
import bio.dto.BioProductInventoryVPageResponseDTO;

import bio.repository.search.BioProductInventoryVSearch;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class BioServiceTests {
    @Autowired
    private BioProductService bioProductService;

    @Autowired
    private BioProductInventoryVService bioProductInventoryVService;

    @Test
    public void testRegister(){
        log.info(bioProductService.getClass().getName());
        BioProductDTO bioProductDTO = BioProductDTO.builder()
                .currentCategory("sample test")
                .efficacyGroup("sample test")
                .packagingUnit("sample test")
                .productCode("sample test")
                .productName("sample test")
                .productionType("sample test")
                .registeredBy("sample test")
                .build();
        Long bioNO = bioProductService.register(bioProductDTO);
        log.info("bioNo: " + bioNO);
    }

    @Test
    public void testModify(){
        BioProductDTO bioProductDTO = BioProductDTO.builder()
                .bioNo(6L)
                .productionType("modify_test")
                .productName("modify_test")
                .build();
        bioProductService.modify(bioProductDTO);
    }

//    @Test
//    public void testList(){
//        BioProdutPageRequestDTO bioProdutPageRequestDTO = BioProdutPageRequestDTO.builder()
//                .type("n")
//                .keyword("test")
//                .page(1)
//                .size(10)
//                .build();
//        BioProductPageResponseDTO responseDTO = bioProductService.list(bioProdutPageRequestDTO);
//        log.info(responseDTO);
//
//    }

    @Test
    public void testList() {
        // BioProductInventoryVPageRequestDTO 객체로 수정
        BioProductInventoryVPageRequestDTO bioProductInventoryVPageRequestDTO = BioProductInventoryVPageRequestDTO.builder()
                .type("")  // 필터링 조건을 비워둠
                .keyword("")  // 필터링 조건을 비워둠
                .page(1)  // 첫 번째 페이지
                .size(Integer.MAX_VALUE)  // 최대 크기
                .build();

        // bioProductInventoryVService의 list 메소드를 호출하여 데이터 조회
        BioProductInventoryVPageResponseDTO responseDTO = bioProductInventoryVService.list(bioProductInventoryVPageRequestDTO);

        // 결과 로깅
        log.info(responseDTO);
    }
}
