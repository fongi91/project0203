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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/bio")
public class BioProductInventoryVController {
    private final BioProductInventoryVServiceImpl bioProductInventoryVServiceImpl;

    @GetMapping("/bioProductInventoryVList")
    public void getBioProductInventoryVList(BioProductInventoryVPageRequestDTO bioProductInventoryVPageRequestDTO,
                                            @RequestParam(name = "startDate", required = false) String startDate,
                                            @RequestParam(name = "endDate", required = false) String endDate,
                                            Model model){
        //날짜범위가 있을 경우 DTO에 추가
        bioProductInventoryVPageRequestDTO.setStartDate(startDate);
        bioProductInventoryVPageRequestDTO.setEndDate(endDate);

        BioProductInventoryVPageResponseDTO bioProductInventoryVPageResponseDTO = bioProductInventoryVServiceImpl.list(bioProductInventoryVPageRequestDTO);
        log.info("responseDTO:" + bioProductInventoryVPageResponseDTO);
        model.addAttribute("responseDTO", bioProductInventoryVPageResponseDTO);
    }
}
