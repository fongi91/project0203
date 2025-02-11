package bio.controller;

import bio.dto.BioInvShippingDTO;
import bio.dto.BioInvShippingPageRequestDTO;
import bio.dto.BioInvShippingPageResponseDTO;
import bio.repository.BioInvShippingRepository;
import bio.service.BioInvShippingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
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
public class BioInvShippingController {
    private final BioInvShippingService bioInvShippingService;
    private final BioInvShippingRepository bioInvShippingRepository;

    @GetMapping("/BioInvShippinglist")
    public void list(BioInvShippingPageRequestDTO bioInvShippingPageRequestDTO, Model model){
        BioInvShippingPageResponseDTO<BioInvShippingDTO> responseDTO = bioInvShippingService.list(bioInvShippingPageRequestDTO);
        log.info(responseDTO);
        model.addAttribute("responseDTO", responseDTO);
    }


    @GetMapping("/BioInvShippingregister")
    public void registerGET(){

    }
    @PostMapping("/BioInvShippingregister")
    public String registerPost(@Valid BioInvShippingDTO bioInvShippingDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            log.info("has errors......");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/bio/BioInvShippingregister";
        }

        if (bioInvShippingDTO.getIsShipped() == null) {
            bioInvShippingDTO.setIsShipped(false);
        }

        Long shippingId = bioInvShippingService.register(bioInvShippingDTO);
        redirectAttributes.addFlashAttribute("result", shippingId);
        return "redirect:/bio/BioInvShippinglist";
    }

    @GetMapping({"/BioInvShippingread", "/BioInvShippingmodify"})
    public void read(Long shippingId, BioInvShippingPageRequestDTO bioInvShippingPageRequestDTO, Model model){
        BioInvShippingDTO bioInvShippingDTO = bioInvShippingService.readOne(shippingId);
        log.info(bioInvShippingDTO);
        model.addAttribute("dto", bioInvShippingDTO);
    }

    @PostMapping("/BioInvShippingmodify")
    public String modify(BioInvShippingPageRequestDTO bioInvShippingPageRequestDTO, @Valid BioInvShippingDTO bioInvShippingDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            String link = bioInvShippingPageRequestDTO.getLink();
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("shippingId", bioInvShippingDTO.getShippingId());
            return "redirect:/bio/BioInvShippingmodify?"+link;
        }

        if (bioInvShippingDTO.getIsShipped() == null) {
            bioInvShippingDTO.setIsShipped(false);
        }

        bioInvShippingService.modify(bioInvShippingDTO);
        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addAttribute("shippingId", bioInvShippingDTO.getShippingId());
        return "redirect:/bio/BioInvShippingread";
    }

    @PostMapping("/BioInvShippingremove")
    public String remove(Long shippingId, RedirectAttributes redirectAttributes){
        bioInvShippingService.remove(shippingId);
        redirectAttributes.addFlashAttribute("result", "removed");
        return "redirect:/bio/BioInvShippinglist";
    }
}
