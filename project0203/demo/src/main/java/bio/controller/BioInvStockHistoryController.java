package bio.controller;

import bio.dto.BioInvStockHistoryDTO;
import bio.dto.BioInvStockHistoryPageRequestDTO;
import bio.dto.BioInvStockHistoryPageResponseDTO;
import bio.repository.BioInvStockHistoryRepository;
import bio.service.BioInvStockHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bio")
@Log4j2
@RequiredArgsConstructor
public class BioInvStockHistoryController {
    private final BioInvStockHistoryService bioInvStockHistoryService;
    private final BioInvStockHistoryRepository bioInvStockHistoryRepository;

    @GetMapping("/BioInvStockHistorylist")
    //bioProductList
    public void list(BioInvStockHistoryPageRequestDTO bioInvStockHistoryPageRequestDTO, Model model){
        BioInvStockHistoryPageResponseDTO<BioInvStockHistoryDTO> responseDTO = bioInvStockHistoryService.list(bioInvStockHistoryPageRequestDTO);
        log.info(responseDTO);
        model.addAttribute("responseDTO", responseDTO);
    }
}
