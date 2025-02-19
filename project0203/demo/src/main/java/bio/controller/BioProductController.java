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


//        // 이미지 파일 처리
//        if (!productImage.isEmpty()) {
//            try {
//                String imageFileName = productImage.getOriginalFilename();
//                File destination = new File("path/to/your/upload/directory", imageFileName);
//                productImage.transferTo(destination);
//                bioProductDTO.setImageFileName(imageFileName);
//            } catch (IOException e) {
//                log.error("Image upload failed", e);
//                redirectAttributes.addFlashAttribute("errorMessage", "이미지 업로드에 실패했습니다.");
//                return "redirect:/bio/bioProductRegister";
//            }
//        }
//


        log.info(bioProductDTO);
        try {
            String productCode = bioProductService.register(bioProductDTO);
            redirectAttributes.addFlashAttribute("result", productCode);
        } catch(DataIntegrityViolationException e){
            log.error("중복 제품코드", e);
            redirectAttributes.addFlashAttribute("errorMessage", "중복된 제품코드는 사용할 수 없습니다.");
            return "redirect:/bio/bioProductRegister";
        }




        return "redirect:/bio/bioProductList";
    }

    @GetMapping({"/bioProductRead", "/bioProductModify"})
    public void read(String productCode, BioProductPageRequestDTO bioProductPageRequestDTO, Model model){
        BioProductDTO bioProductDTO = bioProductService.readOne(productCode);
        log.info(bioProductDTO);

        if("B0001".equals(productCode)){
            bioProductDTO.setImageFileName("img1.png");
        } else if("B0002".equals(productCode)){
            bioProductDTO.setImageFileName("img2.png");
        } else if("B0003".equals(productCode)){
            bioProductDTO.setImageFileName("img3.png");
        } else if("B0004".equals(productCode)){
            bioProductDTO.setImageFileName("img4.png");
        } else if("B0005".equals(productCode)){
            bioProductDTO.setImageFileName("img5.png");
        } else if("B0006".equals(productCode)){
            bioProductDTO.setImageFileName("img6.png");
        } else if("B0007".equals(productCode)){
            bioProductDTO.setImageFileName("img7.png");
        } else{
            bioProductDTO.setImageFileName("img8.png");
        }

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
            redirectAttributes.addAttribute("productCode", bioProductDTO.getProductCode());
            return "redirect:/bio/bioProductModify?"+link;
        }
        bioProductService.modify(bioProductDTO);
        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addAttribute("productCode", bioProductDTO.getProductCode());
        return "redirect:/bio/bioProductRead";
    }

    @PostMapping("/bioProductRemove")
    public String remove(String productCode, RedirectAttributes redirectAttributes){
        log.info("remove.....post....." + productCode);
        bioProductService.remove(productCode);
        redirectAttributes.addFlashAttribute("result", "removed");
        return "redirect:/bio/bioProductList";
    }
}