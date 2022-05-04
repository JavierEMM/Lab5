package edu.pucp.gtics.lab5_gtics_20221.controller;

import edu.pucp.gtics.lab5_gtics_20221.entity.Plataformas;
import edu.pucp.gtics.lab5_gtics_20221.repository.PlataformasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/plataformas")

public class PlataformasController {

    @Autowired
    PlataformasRepository plataformasRepository;

    @GetMapping(value = {"/lista"})
    public String listaPlataformas (Model model){
        List<Plataformas> listaplataformas = plataformasRepository.findAll(Sort.by("nombre"));
        model.addAttribute("listaplataformas", listaplataformas);

        return "plataformas/lista";
    }

    @GetMapping("/editar")
    public String editarPlataformas(@RequestParam("id") int id, Model model){
        Optional<Plataformas> opt = plataformasRepository.findById(id);

        if (opt.isPresent()){
            Plataformas plataforma = opt.get();
            model.addAttribute("plataforma", plataforma);
            return "plataformas/editarFrm";
        }else {
            return "redirect:/plataformas/lista";
        }

    }

    @GetMapping("/nuevo")
    public String nuevaPlataforma(@ModelAttribute("plataforma") Plataformas plataforma){
            return "plataformas/editarFrm";
    }

    @PostMapping("/guardar")
    public String guardarPlataforma(Model model, RedirectAttributes attr, @ModelAttribute("plataforma") @Valid Plataformas plataforma, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.hasFieldErrors("idplataforma"));
            model.addAttribute("plataforma", plataforma);
            return "plataformas/editarFrm";
        } else {
            if (plataforma.getIdplataforma() == 0) {
                attr.addFlashAttribute("msg", "Plataforma creada exitosamente");
            } else {
                attr.addFlashAttribute("msg", "Plataforma actualizada exitosamente");
            }
            plataformasRepository.save(plataforma);
            return "redirect:/plataformas/lista";
        }
    }

    @GetMapping("/borrar")
    public String borrarPlataforma(@RequestParam("id") int id){
        Optional<Plataformas> opt = plataformasRepository.findById(id);
        if (opt.isPresent()) {
            plataformasRepository.deleteById(id);
        }
        return "redirect:/plataformas/lista";
    }

}
