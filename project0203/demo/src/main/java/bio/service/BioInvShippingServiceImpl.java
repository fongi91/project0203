package bio.service;

import bio.domain.BioInvReceiving;
import bio.domain.BioProduct;
import bio.domain.BioInvShipping;
import bio.dto.BioInvReceivingDTO;
import bio.dto.BioInvShippingDTO;
import bio.dto.BioInvShippingPageRequestDTO;
import bio.dto.BioInvShippingPageResponseDTO;
import bio.repository.BioInvShippingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BioInvShippingServiceImpl implements BioInvShippingService{
    private final ModelMapper modelMapper;
    private final BioInvShippingRepository bioInvShippingRepository;

    @Autowired
    public BioInvShippingServiceImpl(ModelMapper modelMapper, BioInvShippingRepository bioInvShippingRepository, BioInvShippingRepository bioInvShippingRepository1){
        this.modelMapper = modelMapper;
        this.bioInvShippingRepository = bioInvShippingRepository1;

        modelMapper.addMappings(new PropertyMap<BioInvShippingDTO, BioInvShipping>() {
            @Override
            protected void configure() {
                skip(destination.getBioProduct().getBioNo()); // bioNo는 매핑하지 않도록 처리
            }
        });

        modelMapper.addMappings(new PropertyMap<BioInvShippingDTO, BioProduct>() {
            @Override
            protected void configure() {
                map(source.getProductCode(), destination.getProductCode()); // productCode 매핑
                map(source.getProductName(), destination.getProductName()); // productName 매핑
            }
        });
    }

    @Override
    public Long register(BioInvShippingDTO bioInvShippingDTO){
        BioInvShipping bioInvShipping = modelMapper.map(bioInvShippingDTO, BioInvShipping.class);
        Long shippingId = bioInvShippingRepository.save(bioInvShipping).getShippingId();
        return shippingId;
    }

    @Override
    public BioInvShippingDTO readOne(Long shippingId){
        Optional<BioInvShipping> result = bioInvShippingRepository.findById(shippingId);
        BioInvShipping bioInvShipping = result.orElseThrow();
        BioInvShippingDTO bioInvShippingDTO = modelMapper.map(bioInvShipping, BioInvShippingDTO.class);
        bioInvShippingDTO.setProductName(bioInvShipping.getProductName());
        return bioInvShippingDTO;
    }

    @Override
    public void modify(BioInvShippingDTO bioInvShippingDTO){
        Optional<BioInvShipping> result = bioInvShippingRepository.findById(bioInvShippingDTO.getShippingId());
        BioInvShipping bioInvShipping = result.orElseThrow();
        bioInvShipping.change(bioInvShippingDTO.getProductCode(), bioInvShippingDTO.getQuantity(), bioInvShippingDTO.getCustomer(),bioInvShippingDTO.getWarehouseLocation(), bioInvShippingDTO.getShippingDate(), bioInvShippingDTO.getShelfLife(), bioInvShippingDTO.getRegisteredBy(), bioInvShippingDTO.getIsShipped());
        bioInvShippingRepository.save(bioInvShipping);
    }

    @Override
    public void remove(Long shippingId){
        bioInvShippingRepository.deleteById(shippingId);
    }

    @Override
    public BioInvShippingPageResponseDTO<BioInvShippingDTO> list(BioInvShippingPageRequestDTO bioInvShippingPageRequestDTO){
        String[] types = bioInvShippingPageRequestDTO.getTypes();
        String keyword = bioInvShippingPageRequestDTO.getKeyword();

        String dateKeyword = bioInvShippingPageRequestDTO.getDateKeyword();

        String shelfLifeKeyword = bioInvShippingPageRequestDTO.getShelfLifeKeyword();

        Pageable pageable = bioInvShippingPageRequestDTO.getPageable("shippingId");

        Page<BioInvShipping> result = bioInvShippingRepository.searchAll(types, keyword, dateKeyword, shelfLifeKeyword, pageable);

        List<BioInvShippingDTO> dtoList = result.getContent().stream()
                .map(bioInvShipping -> {BioInvShippingDTO dto = modelMapper.map(bioInvShipping, BioInvShippingDTO.class);
                    dto.setProductName(bioInvShipping.getProductName());
                    return  dto;})
                .collect(Collectors.toList());

        return BioInvShippingPageResponseDTO.<BioInvShippingDTO>withAll()
                .bioInvShippingPageRequestDTO(bioInvShippingPageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }
}
