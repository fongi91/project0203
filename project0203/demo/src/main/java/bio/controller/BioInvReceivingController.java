package bio.controller;

import bio.dto.BioInvReceivingDTO;
import bio.dto.BioInvReceivingPageRequestDTO;
import bio.dto.BioInvReceivingPageResponseDTO;
import bio.repository.BioInvReceivingRepository;
import bio.service.BioInvReceivingService;
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
public class BioInvReceivingController {
    private final BioInvReceivingService bioInvReceivingService;
    private final BioInvReceivingRepository bioInvReceivingRepository;

    @GetMapping("/BioInvReceivinglist")
    //bioProductList
    public void list(BioInvReceivingPageRequestDTO bioInvReceivingPageRequestDTO, Model model){
        BioInvReceivingPageResponseDTO<BioInvReceivingDTO> responseDTO = bioInvReceivingService.list(bioInvReceivingPageRequestDTO);
        log.info(responseDTO);
        model.addAttribute("responseDTO", responseDTO);


    }

    @GetMapping("/BioInvReceivingregister")
    public void registerGET(){

    }
    @PostMapping("/BioInvReceivingregister")
    public String registerPost(@Valid BioInvReceivingDTO bioInvReceivingDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            log.info("has errors......");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/bio/BioInvReceivingregister";
        }

        if (bioInvReceivingDTO.getIsReceived() == null) {
            bioInvReceivingDTO.setIsReceived(false);
        }

        Long receivingId = bioInvReceivingService.register(bioInvReceivingDTO);
        redirectAttributes.addFlashAttribute("result", receivingId);
        return "redirect:/bio/BioInvReceivinglist";
    }

    @GetMapping({"/BioInvReceivingread", "/BioInvReceivingmodify"})
    public void read(Long receivingId, BioInvReceivingPageRequestDTO bioInvReceivingPageRequestDTO, Model model){
        BioInvReceivingDTO bioInvReceivingDTO = bioInvReceivingService.readOne(receivingId);
        log.info(bioInvReceivingDTO);
        model.addAttribute("dto", bioInvReceivingDTO);
    }

    @PostMapping("/BioInvReceivingmodify")
    public String modify(BioInvReceivingPageRequestDTO bioInvReceivingPageRequestDTO,
                         @Valid BioInvReceivingDTO bioInvReceivingDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            String link = bioInvReceivingPageRequestDTO.getLink();
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("receivingId", bioInvReceivingDTO.getReceivingId());
            return "redirect:/bio/BioInvReceivingmodify?"+link;
        }

        if (bioInvReceivingDTO.getIsReceived() == null) {
            bioInvReceivingDTO.setIsReceived(false);
        }

        bioInvReceivingService.modify(bioInvReceivingDTO);
        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addAttribute("receivingId", bioInvReceivingDTO.getReceivingId());
        return "redirect:/bio/BioInvReceivingread";
    }

    @PostMapping("/BioInvReceivingremove")
    public String remove(Long receivingId, RedirectAttributes redirectAttributes){
        bioInvReceivingService.remove(receivingId);
        redirectAttributes.addFlashAttribute("result", "removed");
        return "redirect:/bio/BioInvReceivinglist";
    }
}
