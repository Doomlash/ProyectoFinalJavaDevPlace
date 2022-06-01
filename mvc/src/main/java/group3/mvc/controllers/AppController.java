package group3.mvc.controllers;

import group3.mvc.model.MyUser;
import group3.mvc.services.MyUserService;
import group3.mvc.services.implementation.IGroup;
import group3.mvc.services.implementation.IMessages;
import group3.mvc.services.implementation.IMyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import group3.mvc.model.FormRequest;
import group3.mvc.model.UserHolder;
import group3.mvc.services.MiddleService;


@Controller
public class AppController {

    @Autowired
    MiddleService s;

    @Autowired
    PasswordEncoder pe;

    @Autowired
    IMyUser iMU;

    @Autowired
    IGroup iG;

    @Autowired
    IMessages iM;



    @GetMapping("/loginSuccess")
    public String initLogin(){
        s.login();
        return "redirect:/home";
    }

    @GetMapping("/registerSuccess")
    public String initRegister(){
        s.register();
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("text", UserHolder.getCurrentUser().toString());
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
    public String indexAddC(@RequestParam(required = false,name = "username") String username, Model model){
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
    public String indexAddG(){
        return "addGroup";
    }

    @GetMapping("/searchG")
    public String searchG(@RequestParam("username") String username, Model model){
        model.addAttribute("users",iMU.readUByUsername(username));
        return "redirect:/addGroup";
    }

    @PostMapping("/{idG}/group/{idC}")//////("{idGroup}/addGroup/{idC}")
    public String addGroup(@PathVariable("idG") Long idG, @PathVariable("idC") Long idC){
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
    public String addBlock(@ModelAttribute("block") MyUser block, Model model){
        Integer rta = iMU.addB(block);
        return "redirect:/chatRoom";
    }


    @PostMapping("/group/delete/{idG}")
    public String delGroup(@PathVariable("idG")Long idG){
        Integer rta = iG.deleteG(idG);
        return "redirect:/chatRoom";
    }
}