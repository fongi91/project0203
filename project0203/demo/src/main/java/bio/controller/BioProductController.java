package bio.controller;

import bio.dto.BioProductDTO;
import bio.dto.BioProductPageRequestDTO;
import bio.dto.BioProductPageResponseDTO;
import bio.service.BioProductService;
import lombok.extern.log4j.Log4j2;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/bio")
public class BioProductController {
    private final BioProductService bioProductService;

    @GetMapping("/bioProductList")
    public void bioProductList(BioProductPageRequestDTO bioProductPageRequestDTO, Model model) {
        BioProductPageResponseDTO bioProductPageResponseDTO = bioProductService.list(bioProductPageRequestDTO);
        log.info("responseDTO:" + bioProductPageResponseDTO);
        model.addAttribute("responseDTO", bioProductPageResponseDTO);
    }


    @GetMapping("/bioProductRegister")
    public void registerGET(){
    }


    @PostMapping("/bioProductRegister")
    public String registerPost(@Valid BioProductDTO bioProductDTO, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){
        log.info("BioProduct post register~~~~~~~~!!!!!!!!!!!!");
        if (bindingResult.hasErrors()) {
            log.info("bindingResult error!!!");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/bio/bioProductRegister";
        }
        log.info(bioProductDTO);
        try {
            Long bioNo = bioProductService.register(bioProductDTO);
            redirectAttributes.addFlashAttribute("result", bioNo);
        } catch(DataIntegrityViolationException e){
            log.error("중복 제품코드", e);
            redirectAttributes.addFlashAttribute("errorMessage", "중복된 제품코드는 사용할 수 없습니다.");
            return "redirect:/bio/bioProductRegister";
        }
        return "redirect:/bio/bioProductList";
    }

    @GetMapping({"/bioProductRead", "/bioProductModify"})
    public void read(Long bioNo, BioProductPageRequestDTO bioProductPageRequestDTO, Model model){
        BioProductDTO bioProductDTO = bioProductService.readOne(bioNo);
        log.info(bioProductDTO);
        model.addAttribute("dto", bioProductDTO);
    }

    @PostMapping("/bioProductModify")
    public String modify(BioProductPageRequestDTO bioProductPageRequestDTO,
                         @Valid BioProductDTO bioProductDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes){
        log.info("bioProduct modify post...." + bioProductDTO);
        if(bindingResult.hasErrors()){
            log.info("bindingResult has errors!!!!!");
            String link = bioProductPageRequestDTO.getLink();
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("bioNo", bioProductDTO.getBioNo());
            return "redirect:/bio/bioProductModify?"+link;
        }
        bioProductService.modify(bioProductDTO);
        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addAttribute("bioNo", bioProductDTO.getBioNo());
        return "redirect:/bio/bioProductRead";
    }

    @PostMapping("/bioProductRemove")
    public String remove(Long bioNo, RedirectAttributes redirectAttributes){
        log.info("remove.....post....." + bioNo);
        bioProductService.remove(bioNo);
        redirectAttributes.addFlashAttribute("result", "removed");
        return "redirect:/bio/bioProductList";
    }

}