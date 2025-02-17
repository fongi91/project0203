package bio.service;

import bio.domain.BioProductInventoryV;
import bio.dto.*;
import bio.repository.BioProductInventoryVRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class BioProductInventoryVServiceImpl implements BioProductInventoryVService{
    private final ModelMapper modelMapper;
    private final BioProductInventoryVRepository bioProductInventoryVRepository;

    //조회하는 메서드
    public List<BioProductInventoryV> list(){
        return bioProductInventoryVRepository.findAll();
    }

    public BioProductInventoryVPageResponseDTO list(BioProductInventoryVPageRequestDTO bioProductInventoryVPageRequestDTO){
        //브라우저에서 요청한 파라미터 값 세팅
        String[] types = bioProductInventoryVPageRequestDTO.getTypes();
        String keyword = bioProductInventoryVPageRequestDTO.getKeyword();
        String startDate = bioProductInventoryVPageRequestDTO.getStartDate();
        String endDate = bioProductInventoryVPageRequestDTO.getEndDate();

        Pageable pageable = bioProductInventoryVPageRequestDTO.getPageable("no");

        Page<BioProductInventoryV> result = bioProductInventoryVRepository.searchAll(types, keyword, pageable, startDate, endDate);

        List<BioProductInventoryVDTO> dtoList = result.getContent().stream()
                .map(bioProductInventoryV -> modelMapper.map(bioProductInventoryV, BioProductInventoryVDTO.class)).collect(Collectors.toList());

        return BioProductInventoryVPageResponseDTO.builder()
                .bioProductInventoryVPageRequestDTO(bioProductInventoryVPageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();

    }
    @Override
    public List<Object[]> getBioProductInventoryVData() {
        return bioProductInventoryVRepository.getBioProductInventoryVData();
    }

}
