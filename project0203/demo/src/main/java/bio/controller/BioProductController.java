package bio.controller;

import bio.dto.BioProductDTO;
import bio.dto.BioProdutPageRequestDTO;
import bio.dto.BioProductPageResponseDTO;
import bio.service.BioProductService;
import lombok.extern.log4j.Log4j2;
import lombok.RequiredArgsConstructor;
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
    public void bioProductList(BioProdutPageRequestDTO bioProdutPageRequestDTO, Model model) {
        BioProductPageResponseDTO bioProductPageResponseDTO = bioProductService.list(bioProdutPageRequestDTO);
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
        Long bioNo = bioProductService.register(bioProductDTO);
        redirectAttributes.addFlashAttribute("result", bioNo);
        return "redirect:/bio/bioProductList";
    }

    @GetMapping({"/bioProductRead", "/bioProductModify"})
    public void read(Long bioNo, BioProdutPageRequestDTO bioProdutPageRequestDTO, Model model){
        BioProductDTO bioProductDTO = bioProductService.readOne(bioNo);
        log.info(bioProductDTO);
        model.addAttribute("dto", bioProductDTO);
    }

    @PostMapping("/bioProductModify")
    public String modify(BioProdutPageRequestDTO bioProdutPageRequestDTO,
                         @Valid BioProductDTO bioProductDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes){
        log.info("bioProduct modify post...." + bioProductDTO);
        if(bindingResult.hasErrors()){
            log.info("bindingResult has errors!!!!!");
            String link = bioProdutPageRequestDTO.getLink();
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