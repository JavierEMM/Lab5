package edu.pucp.gtics.lab5_gtics_20221.controller;

import edu.pucp.gtics.lab5_gtics_20221.entity.*;
import edu.pucp.gtics.lab5_gtics_20221.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JuegosRepository juegosRepository;

    @GetMapping("/lista")
    public String listaCarrito (Model model, HttpSession sesion){

        ArrayList<Juegos> carrito = (ArrayList<Juegos>) sesion.getAttribute("carrito");
        if(carrito == null){
            carrito = new ArrayList<Juegos>();
            sesion.setAttribute("carrito",carrito);
        }
        sesion.setAttribute("ncarrito",carrito.size());
        model.addAttribute("carrito",carrito);
        return "carrito/lista";
    }

    @GetMapping("/compra")
    public String comprarCarrito(HttpSession sesion ){
        ArrayList<Juegos> carrito = (ArrayList<Juegos>) sesion.getAttribute("carrito");
        for(Juegos i:carrito){
            juegosRepository.obtenerJuegosPorUser(i.getIdjuego());
        }
        carrito.clear();
        return "redirect:/vista";
    }

    @GetMapping("/agregar")
    public String nuevoCarrito(@RequestParam("id") int id, HttpSession sesion){
        ArrayList<Juegos> carrito = (ArrayList<Juegos>) sesion.getAttribute("carrito");
        Juegos juego = juegosRepository.getById(id);
        carrito.add(juego);
        sesion.setAttribute("carrito",carrito);
        sesion.setAttribute("ncarrito",carrito.size());
        return "redirect:/vista";
    }

    //public String editarCarrito( ... ){

        //return "redirect:/juegos/lista";
    //}

    @GetMapping("/borrar")
    public String borrarCarrito(@RequestParam("id") int id, HttpSession sesion){
        ArrayList<Juegos> carrito = (ArrayList<Juegos>) sesion.getAttribute("carrito");
        Optional<Juegos> optionalJuegos = juegosRepository.findById(id);
        if(optionalJuegos.isPresent()){
            carrito.remove(optionalJuegos);
            return "redirect:/admin/salas";
        }
        return "redirect:/carrito/lista";
    }


}
