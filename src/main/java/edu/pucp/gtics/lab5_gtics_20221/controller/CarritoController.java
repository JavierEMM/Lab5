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
    @Autowired
    JuegosxUsuarioRepository juegosxUsuarioRepository;

    @GetMapping("/lista")
    public String listaCarrito (Model model, HttpSession session){

        ArrayList<Juegos> carrito = (ArrayList<Juegos>) session.getAttribute("carrito");
        session.setAttribute("ncarrito",carrito.size());
        model.addAttribute("carrito",carrito);
        return "carrito/lista";
    }

    @GetMapping("/compra")
    public String comprarCarrito(HttpSession session,Authentication authentication){
        ArrayList<Juegos> carrito = (ArrayList<Juegos>) session.getAttribute("carrito");
        ArrayList<JuegosxUsuario> juegosxUsuarios = new ArrayList<>();
        User user =  userRepository.findByCorreo(authentication.getName());
        for(Juegos i:carrito){
            JuegosxUsuario juegosxUsuario = new JuegosxUsuario();
            juegosxUsuario.setIdjuego(i);
            juegosxUsuario.setIdusuario(user);
            juegosxUsuarios.add(juegosxUsuario);
        }
        juegosxUsuarioRepository.saveAll(juegosxUsuarios);
        carrito.clear();
        session.setAttribute("carrito",carrito);
        session.setAttribute("ncarrito",carrito.size());
        return "redirect:/vista";
    }

    @GetMapping("/agregar")
    public String nuevoCarrito(@RequestParam("id") int id, HttpSession session){
        ArrayList<Juegos> carrito = (ArrayList<Juegos>) session.getAttribute("carrito");
        Optional<Juegos> juego = juegosRepository.findById(id);
        if(juego.isPresent()){
            carrito.add(juego.get());
        }else{
            return "redirect:/vista";
        }
        session.setAttribute("carrito",carrito);
        session.setAttribute("ncarrito",carrito.size());
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
            Integer ids = optionalJuegos.get().getIdjuego();
            int i=0;
            for(Juegos juego:carrito){
                if(juego.getIdjuego() == ids){
                    break;
                }
                i=i+1;
            }
            carrito.remove(i);

            sesion.setAttribute("carrito",carrito);
        }
        return "redirect:/carrito/lista";
    }


}
