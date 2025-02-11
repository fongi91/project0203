package bio.service;

import bio.dto.BioProductDTO;
import bio.dto.BioProdutPageRequestDTO;
import bio.dto.BioProductPageResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class BioServiceTests {
    @Autowired
    private BioProductService bioProductService;

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

    @Test
    public void testList(){
        BioProdutPageRequestDTO bioProdutPageRequestDTO = BioProdutPageRequestDTO.builder()
                .type("n")
                .keyword("test")
                .page(1)
                .size(10)
                .build();
        BioProductPageResponseDTO responseDTO = bioProductService.list(bioProdutPageRequestDTO);
        log.info(responseDTO);

    }

}
