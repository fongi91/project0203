package bio.service;

import bio.domain.BioInvShipping;
import bio.dto.BioInvShippingDTO;
import bio.dto.BioInvShippingPageRequestDTO;
import bio.dto.BioInvShippingPageResponseDTO;
import bio.repository.BioInvShippingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
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
        return bioInvShippingDTO;
    }

    @Override
    public void modify(BioInvShippingDTO bioInvShippingDTO){
        Optional<BioInvShipping> result = bioInvShippingRepository.findById(bioInvShippingDTO.getShippingId());
        BioInvShipping bioInvShipping = result.orElseThrow();
        bioInvShipping.change(bioInvShippingDTO.getProductCode(),bioInvShippingDTO.getProductName(), bioInvShippingDTO.getQuantity(), bioInvShippingDTO.getCustomer(),bioInvShippingDTO.getWarehouseLocation(), bioInvShippingDTO.getShippingDate(),bioInvShippingDTO.getRegisteredBy(), bioInvShippingDTO.getIsShipped());
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
        //추가
        String dateKeyword = bioInvShippingPageRequestDTO.getDateKeyword();
        Pageable pageable = bioInvShippingPageRequestDTO.getPageable("shippingId");

        Page<BioInvShipping> result = bioInvShippingRepository.searchAll(types, keyword, dateKeyword, pageable);

        List<BioInvShippingDTO> dtoList = result.getContent().stream().map(bioInvShipping -> modelMapper.map(bioInvShipping, BioInvShippingDTO.class)).collect(Collectors.toList());

        return BioInvShippingPageResponseDTO.<BioInvShippingDTO>withAll()
                .bioInvShippingPageRequestDTO(bioInvShippingPageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }
}
