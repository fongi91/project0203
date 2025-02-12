package bio.service;

import bio.domain.BioProduct;
import bio.dto.*;
//import bio.mapper.BioProductMapper;
import bio.repository.BioProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class BioProductServiceImpl implements BioProductService {
    private final ModelMapper modelMapper;
    private final BioProductRepository bioProductRepository;
    //private final BioProductMapper bioProductMapper;


    @Override
    public Long
    register(BioProductDTO bioProductDTO){
        BioProduct bioProduct = modelMapper.map(bioProductDTO, BioProduct.class);
        Long bioNo = bioProductRepository.save(bioProduct).getBioNo();
        return bioNo;
    }

    @Override
    public BioProductDTO readOne(Long bioNo){
        Optional<BioProduct> result = bioProductRepository.findById(bioNo);
        BioProduct bioProduct = result.orElseThrow();
        BioProductDTO bioProductDTO = modelMapper.map(bioProduct, BioProductDTO.class);
        return bioProductDTO;
    }

    @Override
    public void modify(BioProductDTO bioProductDTO){
        Optional<BioProduct> result = bioProductRepository.findById(bioProductDTO.getBioNo());
        BioProduct bioProduct = result.orElseThrow();
        bioProduct.change(bioProductDTO.getProductName(), bioProductDTO.getCurrentCategory(), bioProductDTO.getPackagingUnit(), bioProductDTO.getEfficacyGroup(), bioProductDTO.getProductionType(), bioProductDTO.getDescription());
        bioProductRepository.save(bioProduct);
    }

    @Override
    public void remove(Long bioNo){
        bioProductRepository.deleteById(bioNo);
    }

    @Override
    public BioProductPageResponseDTO list(BioProductPageRequestDTO bioProductPageRequestDTO){
        //브라우저에서 요청한 파라미터 값 세팅
        String[] types = bioProductPageRequestDTO.getTypes();
        String keyword = bioProductPageRequestDTO.getKeyword();
        Pageable pageable = bioProductPageRequestDTO.getPageable("bioNo");

        Page<BioProduct> result = bioProductRepository.searchAll(types, keyword, pageable);

        List<BioProductDTO> dtoList = result.getContent().stream()
                .map(bioProduct -> modelMapper.map(bioProduct, BioProductDTO.class)).collect(Collectors.toList());

        return BioProductPageResponseDTO.builder()
                .bioProductPageRequestDTO(bioProductPageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public List<Object[]> getEfficacyGroupDistribution(){
        //효능군별 개수 구하기
        return bioProductRepository.countByEfficacyGroup();
    }

}