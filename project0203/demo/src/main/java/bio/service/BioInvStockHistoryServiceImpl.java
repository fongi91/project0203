package bio.service;

import bio.domain.BioInvStockHistory;
import bio.domain.BioProduct;
import bio.dto.BioInvStockHistoryDTO;
import bio.dto.BioInvStockHistoryPageRequestDTO;
import bio.dto.BioInvStockHistoryPageResponseDTO;
import bio.repository.BioInvStockHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BioInvStockHistoryServiceImpl implements BioInvStockHistoryService{
    private final ModelMapper modelMapper;
    private final BioInvStockHistoryRepository bioInvStockHistoryRepository;

    @Autowired
    public BioInvStockHistoryServiceImpl(ModelMapper modelMapper, BioInvStockHistoryRepository bioInvStockHistoryRepository, BioInvStockHistoryRepository bioInvStockHistoryRepository1) {
        this.modelMapper = modelMapper;
        this.bioInvStockHistoryRepository = bioInvStockHistoryRepository1;

        modelMapper.addMappings(new PropertyMap<BioInvStockHistoryDTO, BioInvStockHistory>() {
            @Override
            protected void configure() {
                skip(destination.getBioProduct().getBioNo()); // bioNo는 매핑하지 않도록 처리
            }
        });

        modelMapper.addMappings(new PropertyMap<BioInvStockHistoryDTO, BioProduct>() {
            @Override
            protected void configure() {
                map(source.getProductCode(), destination.getProductCode()); // productCode 매핑
                map(source.getProductName(), destination.getProductName()); // productName 매핑
            }
        });
    }

    @Override
    public BioInvStockHistoryPageResponseDTO<BioInvStockHistoryDTO> list(BioInvStockHistoryPageRequestDTO bioInvStockHistoryPageRequestDTO){
        String[] types = bioInvStockHistoryPageRequestDTO.getTypes();
        String keyword = bioInvStockHistoryPageRequestDTO.getKeyword();

        String dateKeyword = bioInvStockHistoryPageRequestDTO.getDateKeyword();
        Pageable pageable = bioInvStockHistoryPageRequestDTO.getPageable("StockId");

        Page<BioInvStockHistory> result = bioInvStockHistoryRepository.searchAll(types, keyword, dateKeyword, pageable);

        List<BioInvStockHistoryDTO> dtoList = result.getContent().stream()
                .map(bioInvStockHistory -> {BioInvStockHistoryDTO dto = modelMapper.map(bioInvStockHistory, BioInvStockHistoryDTO.class);
                                dto.setProductName(bioInvStockHistory.getProductName());
                                return dto;})
                .collect(Collectors.toList());

        return BioInvStockHistoryPageResponseDTO.<BioInvStockHistoryDTO>withAll()
                .bioInvStockHistoryPageRequestDTO(bioInvStockHistoryPageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }
}
