package group3.mvc.controllers;

import group3.mvc.model.MyUser;
import group3.mvc.model.UserHolder;
import group3.mvc.services.implementation.IGroup;
import group3.mvc.services.implementation.IMyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import group3.mvc.model.FormRequest;
import group3.mvc.services.MiddleService;

import javax.servlet.http.HttpSession;


@Controller
public class AppController {

    @Autowired
    MiddleService s;

    @Autowired
    private IMyUser iMU;

    @Autowired
    private IGroup iG;

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
    public String indexAddC(@RequestParam(required = false,name = "username") String username,Model model){
        if(username != null) {
            MyUser user = iMU.readUByUsername(username);
            model.addAttribute("users", user);
        }
        return "addContact";
    }

    @PostMapping("/contact")
    public String addContact(@ModelAttribute("users") MyUser user, Model model){
        UserHolder.setCurrentUser(iMU.readUByUsername("pataPollo"));
        Integer rta = iMU.addC(user);
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
        Integer rta = iG.addM(idG,idC);
        return "redirect:/addGroup";
    }


    //////Chat room
    @GetMapping("/chatRoom")
    public String chatRoom(){
        return "chatRoom";
    }

    @PostMapping("/contact/removeC/{idC}")
    public String removeContact(@PathVariable("idC")Long idC){
        Integer rta = iMU.removeC(idC);
        return "redirect:/chatRoom";
    }

    @PostMapping("/contact/addB/{idC}")
    public String addBlock(@PathVariable("idC")Long idC){
        Integer rta = iMU.addB(idC);
        return "redirect:/chatRoom";
    }



    @PostMapping("/group/delete/{idG}")
    public String delGroup(@PathVariable("idG")Long idG){
        Integer rta = iG.deleteG(idG);
        return "redirect:/chatRoom";
    }
















    //////DEMAS
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        return "registerForm";
    }


}