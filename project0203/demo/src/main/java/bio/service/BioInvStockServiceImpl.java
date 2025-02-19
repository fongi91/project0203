package bio.service;

import bio.domain.BioInvStock;
import bio.domain.BioProduct;
import bio.dto.BioInvStockDTO;
import bio.dto.BioInvStockPageRequestDTO;
import bio.dto.BioInvStockPageResponseDTO;
import bio.repository.BioInvStockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BioInvStockServiceImpl implements BioInvStockService{
    private final ModelMapper modelMapper;
    private final BioInvStockRepository bioInvStockRepository;


    @Autowired
    public BioInvStockServiceImpl(ModelMapper modelMapper, BioInvStockRepository bioInvStockRepository, BioInvStockRepository bioInvStockRepository1) {
        this.modelMapper = modelMapper;
        this.bioInvStockRepository = bioInvStockRepository1;

        modelMapper.addMappings(new PropertyMap<BioInvStockDTO, BioInvStock>() {
            @Override
            protected void configure() {
                skip(destination.getBioProduct().getBioNo()); // bioNo는 매핑하지 않도록 처리
            }
        });

        modelMapper.addMappings(new PropertyMap<BioInvStockDTO, BioProduct>() {
            @Override
            protected void configure() {
                map(source.getProductCode(), destination.getProductCode()); // productCode 매핑
                map(source.getProductName(), destination.getProductName()); // productName 매핑
            }
        });
    }

    @Override
    public BioInvStockPageResponseDTO<BioInvStockDTO> list(BioInvStockPageRequestDTO bioInvStockPageRequestDTO){
        String[] types = bioInvStockPageRequestDTO.getTypes();
        String keyword = bioInvStockPageRequestDTO.getKeyword();

        String dateKeyword = bioInvStockPageRequestDTO.getDateKeyword();
        Pageable pageable = bioInvStockPageRequestDTO.getPageable("StockId");

        Page<BioInvStock> result = bioInvStockRepository.searchAll(types, keyword, dateKeyword, pageable);

        List<BioInvStockDTO> dtoList = result.getContent().stream()
                .map(bioInvStock -> {BioInvStockDTO dto = modelMapper.map(bioInvStock, BioInvStockDTO.class);
                                dto.setProductName(bioInvStock.getProductName());
                                return dto;})
                .collect(Collectors.toList());

        return BioInvStockPageResponseDTO.<BioInvStockDTO>withAll()
                .bioInvStockPageRequestDTO(bioInvStockPageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

    public List<Map<String, Object>> getReceivedAndShippedQuantities() {
        List<Object[]> results = bioInvStockRepository.getReceivedAndShippedQuantities();

        // 결과를 Map 형태로 변환하여 반환
        return results.stream().map(result -> {
            Map<String, Object> data = new HashMap<>();
            data.put("productName", result[0]);
            data.put("totalReceived", result[1]);
            data.put("totalShipped", result[2]);
            return data;
        }).collect(Collectors.toList());
    }
}
