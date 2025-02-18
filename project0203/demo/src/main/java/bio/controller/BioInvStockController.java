package bio.controller;

import bio.dto.BioInvStockDTO;
import bio.dto.BioInvStockPageRequestDTO;
import bio.dto.BioInvStockPageResponseDTO;
import bio.repository.BioInvStockRepository;
import bio.service.BioInvStockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/bio")
@Log4j2
@RequiredArgsConstructor
public class BioInvStockController {
    private final BioInvStockService bioInvStockService;
    private final BioInvStockRepository bioInvStockRepository;

    @GetMapping("/BioInvStocklist")
    //bioProductList
    public void list(BioInvStockPageRequestDTO bioInvStockPageRequestDTO, Model model){
        BioInvStockPageResponseDTO<BioInvStockDTO> responseDTO = bioInvStockService.list(bioInvStockPageRequestDTO);
        log.info(responseDTO);
        model.addAttribute("responseDTO", responseDTO);


    }

}
