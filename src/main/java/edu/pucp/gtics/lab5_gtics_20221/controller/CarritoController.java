package edu.pucp.gtics.lab5_gtics_20221.controller;

import edu.pucp.gtics.lab5_gtics_20221.entity.*;
import edu.pucp.gtics.lab5_gtics_20221.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class CarritoController {



    public String listaCarrito (Model model, ...){

        return "carrito/lista";
    }

    public String nuevoCarrito(@RequestParam("id") int id, ...){

        return "redirect:/vista";
    }

    public String editarCarrito( ... ){

        return "redirect:/juegos/lista";
    }

    public String borrarCarrito(...){

        return "redirect:/carrito/lista";
    }


}
