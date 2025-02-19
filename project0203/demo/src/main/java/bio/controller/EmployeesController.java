package bio.controller;

import bio.dto.EmployeesDTO;
import bio.dto.EmployeesPageRequestDTO;
import bio.dto.EmployeesPageResponseDTO;
import bio.service.EmployeesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/bio")
@Log4j2
@RequiredArgsConstructor
public class EmployeesController {
    private final EmployeesService employeesService;

    @GetMapping("/Employeeslist")
    public void list(EmployeesPageRequestDTO employeesPageRequestDTO,
                     Model model) {

        EmployeesPageResponseDTO<EmployeesDTO> responseDTO = employeesService.list(employeesPageRequestDTO);
        log.info(responseDTO);
        model.addAttribute("responseDTO", responseDTO);
    }

    @GetMapping("/Employeesregister")
    public void registerGET(){

    }
    @PostMapping("/Employeesregister")
    public String registerPost(@Valid EmployeesDTO employeesDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            log.info("has errors......");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/bio/Employeesregister";
        }
        try {
            Long eno = employeesService.register(employeesDTO);
            redirectAttributes.addFlashAttribute("result", eno);
        } catch (DataIntegrityViolationException e){
            log.error("중복 사원번호", e);
            redirectAttributes.addFlashAttribute("errorMessage", "중복된 사원번호는 사용할 수 없습니다.");
            return "redirect:/bio/Employeesregister";
        }
        return "redirect:/bio/Employeeslist";
    }

    @GetMapping({"/Employeesread", "/Employeesmodify"})
    public void read(Long eno,
                     EmployeesPageRequestDTO employeesPageRequestDTO,
                     Model model) {
        EmployeesDTO employeesDTO = employeesService.readOne(eno);

        log.info(employeesDTO);

        model.addAttribute("dto", employeesDTO);
    }

    @PostMapping("/Employeesmodify")
    public String modify(EmployeesPageRequestDTO employeesPageRequestDTO,
                         @Valid EmployeesDTO employeesDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        String link = employeesPageRequestDTO.getLink();

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("eno", employeesDTO.getEno());
            return "redirect:/bio/Employeesmodify?" + link;
        }

        try {
            employeesService.modify(employeesDTO);
            redirectAttributes.addFlashAttribute("result", "modified");
            redirectAttributes.addAttribute("eno", employeesDTO.getEno());
        } catch (DataIntegrityViolationException e) {
            log.error("중복 사원번호", e);
            redirectAttributes.addFlashAttribute("errorMessage", "중복된 사원번호는 사용할 수 없습니다.");
            redirectAttributes.addAttribute("eno", employeesDTO.getEno());  // eno 값 추가
            return "redirect:/bio/Employeesmodify?" + link;
        }

        return "redirect:/bio/Employeesread";
    }



    @PostMapping("/Employeesremove")
    public String remove(Long eno,
                         RedirectAttributes redirectAttributes){
        employeesService.remove(eno);
        redirectAttributes.addFlashAttribute("result", "removed");
        return "redirect:/bio/Employeeslist";
    }

}
