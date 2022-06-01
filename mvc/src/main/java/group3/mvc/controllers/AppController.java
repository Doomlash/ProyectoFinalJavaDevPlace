package group3.mvc.controllers;

import group3.mvc.model.MyUser;
import group3.mvc.model.UserHolder;
import group3.mvc.model.request.RelationRequest;
import group3.mvc.services.implementation.IGroup;
import group3.mvc.services.implementation.IMyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import group3.mvc.model.FormRequest;
import group3.mvc.services.MiddleService;
import org.thymeleaf.Thymeleaf;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class AppController {

    @Autowired
    MiddleService s;

    @Autowired
    private IMyUser iMU;

    @Autowired
    private IGroup iMG;

    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("showGif", false);
        model.addAttribute("form", new FormRequest());
        return "home";
    }

    @PostMapping("/gif")
    public String postGif(FormRequest request, Model model, RedirectAttributes ra) {
        ra.addFlashAttribute("id", request.getS());
        return "redirect:/gif";
    }

    @GetMapping("/gif")
    public String gif(@ModelAttribute("id") String gifId, Model model){
        model.addAttribute("showGif", true);
        model.addAttribute("form", new FormRequest());
        model.addAttribute("id", gifId);
        model.addAttribute("ratio", s.getRatio(gifId));
        model.addAttribute("width", "30%");
        return "home";
    }

    ////ADDCONTACT////////////
    @GetMapping("/addContact")
    public String indexAddC(){
        return "addContact";
    }

    @GetMapping("/search")
    public String search(@RequestParam("username") String username, Model model){
        model.addAttribute("users",iMU.readUByUsername(username));
        return "addContact";
    }

    @PostMapping("/contact/{idC}")
    public String addContact(@PathVariable("idC") Long idC){
        UserHolder.setCurrentUser(iMU.readUByUsername("pataPollo"));
        Integer rta = iMU.addC(idC);
        return "redirect:/addContact";
    }

    ////////ADDGROUP/////
    @GetMapping("/addGroup")
    public String indexAddG(HttpSession httpSession){
        return "addGroup";
    }

    @GetMapping("/searchG")
    public String searchG(@RequestParam("username") String username, Model model){
        model.addAttribute("users",iMU.readUByUsername(username));
        return "redirect:/addGroup";
    }

    @PostMapping("/{idG}/group/{idC}")//////("{idGroup}/addGroup/{idC}")
    public String addGroup(@PathVariable("idG") Long idG,@PathVariable("idC") Long idC){
        Integer rta = iMG.addM(idG,idC);
        return "redirect:/addGroup";
    }

}