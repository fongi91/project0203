package bio.service;

import bio.domain.BioProductInventoryV;
import bio.dto.*;
import bio.repository.BioProductInventoryVRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


public interface BioProductInventoryVService {
    BioProductInventoryVPageResponseDTO list(BioProductInventoryVPageRequestDTO bioProductInventoryVPageRequestDTO);
}
