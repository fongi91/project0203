package bio.repository;

import bio.domain.BioProduct;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BioProductRepositoryTests {
    @Autowired
    private BioProductRepository bioProductRepository;

    @Test
    public void testInsert() {
        IntStream.rangeClosed(1,10).forEach(i ->{
        BioProduct bioProduct = BioProduct.builder()
                .productCode("test_"+i)
                .productName("test_"+i)
                .productionType("type_"+i)
                .currentCategory("test_"+i)
                .packagingUnit("test_"+i)
                .efficacyGroup("test_"+i)
                .registeredBy("test_"+i)
                .build();
        BioProduct result = bioProductRepository.save(bioProduct);
        log.info("bioNo:"+result.getBioNo());
        });
    }
}

