package bio.service;

import bio.domain.BioInvStock;
import bio.domain.BioProduct;
import bio.dto.BioInvStockDTO;
import bio.dto.BioInvStockPageRequestDTO;
import bio.dto.BioInvStockPageResponseDTO;
import bio.mapper.BioInvStockMapper;
import bio.repository.BioInvStockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor // final 필드에 대한 생성자 자동 생성
@Transactional
public class BioInvStockServiceImpl implements BioInvStockService {
    private final ModelMapper modelMapper;
    private final BioInvStockRepository bioInvStockRepository;
    private final BioInvStockMapper bioInvStockMapper;  // final로 선언하여 @RequiredArgsConstructor가 생성자 처리

    @Override
    public List<Map<String, Object>> getReceivedAndShippedQuantities() {
        // MyBatis Mapper 호출
        return bioInvStockMapper.getReceivedAndShippedQuantities();
    }

//    // 생성자 주입을 자동으로 처리하므로 @Autowired 제거
//    public BioInvStockServiceImpl(ModelMapper modelMapper, BioInvStockRepository bioInvStockRepository, BioInvStockMapper bioInvStockMapper) {
//        this.modelMapper = modelMapper;
//        this.bioInvStockRepository = bioInvStockRepository;
//        this.bioInvStockMapper = bioInvStockMapper;
//
//        // 모델 매핑 설정
//        modelMapper.addMappings(new PropertyMap<BioInvStockDTO, BioInvStock>() {
//            @Override
//            protected void configure() {
//                skip(destination.getBioProduct().getBioNo()); // bioNo는 매핑하지 않도록 처리
//            }
//        });
//
//        modelMapper.addMappings(new PropertyMap<BioInvStockDTO, BioProduct>() {
//            @Override
//            protected void configure() {
//                map(source.getProductCode(), destination.getProductCode()); // productCode 매핑
//                map(source.getProductName(), destination.getProductName()); // productName 매핑
//            }
//        });
//    }

    @Override
    public BioInvStockPageResponseDTO<BioInvStockDTO> list(BioInvStockPageRequestDTO bioInvStockPageRequestDTO) {
        String[] types = bioInvStockPageRequestDTO.getTypes();
        String keyword = bioInvStockPageRequestDTO.getKeyword();
        String dateKeyword = bioInvStockPageRequestDTO.getDateKeyword();
        Pageable pageable = bioInvStockPageRequestDTO.getPageable("StockId");

        Page<BioInvStock> result = bioInvStockRepository.searchAll(types, keyword, dateKeyword, pageable);

        List<BioInvStockDTO> dtoList = result.getContent().stream()
                .map(bioInvStock -> {
                    BioInvStockDTO dto = modelMapper.map(bioInvStock, BioInvStockDTO.class);
                    dto.setProductName(bioInvStock.getProductName());
                    return dto;
                })
                .collect(Collectors.toList());

        return BioInvStockPageResponseDTO.<BioInvStockDTO>withAll()
                .bioInvStockPageRequestDTO(bioInvStockPageRequestDTO)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();
    }
}