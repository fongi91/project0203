package bio.service;

import bio.domain.BioProduct;
import bio.domain.BioInvReceiving;
import bio.dto.BioInvReceivingDTO;
import bio.dto.BioInvReceivingPageRequestDTO;
import bio.dto.BioInvReceivingPageResponseDTO;
import bio.repository.BioInvReceivingRepository;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BioInvReceivingServiceImpl implements BioInvReceivingService{
    private final ModelMapper modelMapper;
    private final BioInvReceivingRepository bioInvReceivingRepository;

    @Autowired
    public BioInvReceivingServiceImpl(ModelMapper modelMapper, BioInvReceivingRepository bioInvReceivingRepository, BioInvReceivingRepository bioInvReceivingRepository1) {
        this.modelMapper = modelMapper;
        this.bioInvReceivingRepository = bioInvReceivingRepository1;

        modelMapper.addMappings(new PropertyMap<BioInvReceivingDTO, BioInvReceiving>() {
            @Override
            protected void configure() {
                skip(destination.getBioProduct().getBioNo()); // bioNo는 매핑하지 않도록 처리
            }
        });

        modelMapper.addMappings(new PropertyMap<BioInvReceivingDTO, BioProduct>() {
            @Override
            protected void configure() {
                map(source.getProductCode(), destination.getProductCode()); // productCode 매핑
                map(source.getProductName(), destination.getProductName()); // productName 매핑
            }
        });
    }

    @Override
    public Long register(BioInvReceivingDTO bioInvReceivingDTO){
        BioInvReceiving bioInvReceiving = modelMapper.map(bioInvReceivingDTO, BioInvReceiving.class);
        Long receivingId = bioInvReceivingRepository.save(bioInvReceiving).getReceivingId();
        return receivingId;
    }

    @Override
    public void modify(BioInvReceivingDTO bioInvReceivingDTO){
        Optional<BioInvReceiving> result = bioInvReceivingRepository.findById(bioInvReceivingDTO.getReceivingId());
        BioInvReceiving bioInvReceiving = result.orElseThrow();
        bioInvReceiving.change(bioInvReceivingDTO.getProductCode(), bioInvReceivingDTO.getQuantity(), bioInvReceivingDTO.getShelfLife(), bioInvReceivingDTO.getSupplier(),bioInvReceivingDTO.getWarehouseLocation(), bioInvReceivingDTO.getReceivingDate(),bioInvReceivingDTO.getRegisteredBy(), bioInvReceivingDTO.getIsReceived());
        bioInvReceivingRepository.save(bioInvReceiving);
    }

    @Override
    public void remove(Long receivingId){
        bioInvReceivingRepository.deleteById(receivingId);
    }

    @Override
    public BioInvReceivingDTO readOne(Long receivingId){
        Optional<BioInvReceiving> result = bioInvReceivingRepository.findById(receivingId);
        BioInvReceiving bioInvReceiving = result.orElseThrow();

        BioInvReceivingDTO bioInvReceivingDTO = modelMapper.map(bioInvReceiving, BioInvReceivingDTO.class);
        bioInvReceivingDTO.setProductName(bioInvReceiving.getProductName());
        return bioInvReceivingDTO;
    }

    @Override
    public BioInvReceivingPageResponseDTO<BioInvReceivingDTO> list(BioInvReceivingPageRequestDTO bioInvReceivingPageRequestDTO){
        String[] types = bioInvReceivingPageRequestDTO.getTypes();
        String keyword = bioInvReceivingPageRequestDTO.getKeyword();

        String dateKeyword = bioInvReceivingPageRequestDTO.getDateKeyword();
        Pageable pageable = bioInvReceivingPageRequestDTO.getPageable("receivingId");

        Page<BioInvReceiving> result = bioInvReceivingRepository.searchAll(types, keyword, dateKeyword, pageable);

        List<BioInvReceivingDTO> dtoList = result.getContent().stream()
                .map(bioInvReceiving -> {BioInvReceivingDTO dto = modelMapper.map(bioInvReceiving, BioInvReceivingDTO.class);
                                dto.setProductName(bioInvReceiving.getProductName());
                                return dto;})
                .collect(Collectors.toList());

        return BioInvReceivingPageResponseDTO.<BioInvReceivingDTO>withAll()
                .bioInvReceivingPageRequestDTO(bioInvReceivingPageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }
}
