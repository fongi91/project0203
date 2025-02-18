package bio.service;

import bio.dto.BioInvStockHistoryDTO;
import bio.dto.BioInvStockHistoryPageRequestDTO;
import bio.dto.BioInvStockHistoryPageResponseDTO;

public interface BioInvStockHistoryService {
    BioInvStockHistoryPageResponseDTO<BioInvStockHistoryDTO> list(BioInvStockHistoryPageRequestDTO bioInvStockHistoryPageRequestDTO);
}
