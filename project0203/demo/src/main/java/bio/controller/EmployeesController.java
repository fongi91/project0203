package bio.controller;

import bio.domain.EmployeeRole;
import bio.domain.Employees;
import bio.dto.EmployeesDTO;
import bio.dto.EmployeesPageRequestDTO;
import bio.dto.EmployeesPageResponseDTO;
import bio.service.EmployeesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
    public void registerGET(@ModelAttribute("employee") Employees employee){

    }
    @PostMapping("/Employeesregister")
    public String registerPost(@ModelAttribute("employee") Employees employee,
                               @Valid EmployeesDTO employeesDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            log.info("has errors......");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/bio/Employeesregister";
        }
        Long eno = employeesService.register(employeesDTO);
        redirectAttributes.addFlashAttribute("result", eno);
        return "redirect:/bio/Employeeslist";
    }

    @GetMapping({"/Employeesread", "/Employeesmodify"})
    public void read(@ModelAttribute("employee") Employees employee,
                     Long eno,
                     EmployeesPageRequestDTO employeesPageRequestDTO,
                     Model model) {
        EmployeesDTO employeesDTO = employeesService.readOne(eno);

        log.info(employeesDTO);

        model.addAttribute("dto", employeesDTO);
    }

    @PostMapping("/Employeesmodify")
    public String modify(@ModelAttribute("employee") Employees employee,
                         EmployeesPageRequestDTO employeesPageRequestDTO,
                         @Valid EmployeesDTO employeesDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            String link = employeesPageRequestDTO.getLink();
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("eno", employeesDTO.getEno());
            return "redirect:/bio/Employeesmodify?"+link;
        }
        employeesService.modify(employeesDTO);
        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addAttribute("eno", employeesDTO.getEno());
        return "redirect:/bio/Employeesread";
    }

    @PostMapping("/Employeesremove")
    public String remove(@ModelAttribute("employee") Employees employee,
                         Long eno,
                         RedirectAttributes redirectAttributes){
        employeesService.remove(eno);
        redirectAttributes.addFlashAttribute("result", "removed");
        return "redirect:/bio/Employeeslist";
    }

}
