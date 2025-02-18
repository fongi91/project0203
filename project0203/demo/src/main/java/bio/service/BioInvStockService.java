package bio.service;

import bio.dto.BioInvStockDTO;
import bio.dto.BioInvStockPageRequestDTO;
import bio.dto.BioInvStockPageResponseDTO;

public interface BioInvStockService {
    BioInvStockPageResponseDTO<BioInvStockDTO> list(BioInvStockPageRequestDTO bioInvStockPageRequestDTO);
}
