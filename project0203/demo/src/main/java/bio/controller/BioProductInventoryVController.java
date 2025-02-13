package bio.controller;


import bio.dto.BioProductInventoryVPageRequestDTO;
import bio.dto.BioProductInventoryVPageResponseDTO;
import bio.service.BioProductInventoryVServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/bio")
public class BioProductInventoryVController {
    private final BioProductInventoryVServiceImpl bioProductInventoryVServiceImpl;

    @GetMapping("/bioProductInventoryVList")
    public void getBioProductInventoryVList(BioProductInventoryVPageRequestDTO bioProductInventoryVPageRequestDTO, Model model){
        BioProductInventoryVPageResponseDTO bioProductInventoryVPageResponseDTO = bioProductInventoryVServiceImpl.list(bioProductInventoryVPageRequestDTO);
        log.info("responseDTO:" + bioProductInventoryVPageResponseDTO);
        model.addAttribute("responseDTO", bioProductInventoryVPageResponseDTO);
    }
}
