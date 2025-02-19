package bio.service;

import bio.dto.BioInvStockDTO;
import bio.dto.BioInvStockPageRequestDTO;
import bio.dto.BioInvStockPageResponseDTO;

import java.util.List;
import java.util.Map;

public interface BioInvStockService {
    BioInvStockPageResponseDTO<BioInvStockDTO> list(BioInvStockPageRequestDTO bioInvStockPageRequestDTO);
    // 입고량과 출고량을 반환하는 메서드를 추가
    List<Map<String, Object>> getReceivedAndShippedQuantities();
}
