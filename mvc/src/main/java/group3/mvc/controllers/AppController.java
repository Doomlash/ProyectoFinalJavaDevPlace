package group3.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import group3.mvc.model.FormRequest;
import group3.mvc.model.MyUser;
import group3.mvc.model.UserHolder;
import group3.mvc.services.MiddleService;
import group3.mvc.services.implementation.IGroup;
import group3.mvc.services.implementation.IMessages;
import group3.mvc.services.implementation.IMyUser;


@Controller
@RequestMapping("/mvc")
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
        return "redirect:/mvc/chatRoom";
    }

    @GetMapping("/registerSuccess")
    public String initRegister(){
        s.register();
        return "redirect:/mvc/chatRoom";
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
        iMU.addC(user);
        return "redirect:/mvc/chatRoom";
    }

    @GetMapping("/deleteContact/{id}")
    public String deleteContact(@PathVariable("id") Long id, Model model){
        iMU.removeC(id);
        return "redirect:/mvc/chatRoom";
    }

    @GetMapping("/deleteBlock/{id}")
    public String deleteBlock(@PathVariable("id") Long id, Model model){
        iMU.removeB(id);
        return "redirect:/mvc/chatRoom";
    }

    //////Chat room
    @GetMapping("/chatRoom")
    public String chatRoom(Model model){
        MyUser user = UserHolder.getCurrentUser();
        model.addAttribute("contacts", user.getContacts());
        model.addAttribute("blocks", user.getBlocks());
        model.addAttribute("listTab", "active");
        return "chatRoom";
    }

    @GetMapping("/chat/{id}")
    public String loadChat(@PathVariable("id") Long id, Model model){
        model.addAttribute("chatTab", "active");
        return "chatRoom";
    }

    // ////////ADDGROUP/////
    // @GetMapping("/addGroup")
    // public String indexAddG(){
    //     return "addGroup";
    // }

    // @GetMapping("/searchG")
    // public String searchG(@RequestParam("username") String username, Model model){
    //     model.addAttribute("users",iMU.readUByUsername(username));
    //     return "redirect:/mvc/addGroup";
    // }

    // @PostMapping("/{idG}/group/{idC}")//////("{idGroup}/addGroup/{idC}")
    // public String addGroup(@PathVariable("idG") Long idG, @PathVariable("idC") Long idC){
    //     Integer rta = iG.addM(idG,idC);
    //     return "redirect:/mvc/addGroup";
    // }


    


    // @PostMapping("/group/delete/{idG}")
    // public String delGroup(@PathVariable("idG")Long idG){
    //     Integer rta = iG.deleteG(idG);
    //     return "redirect:/mvc/chatRoom";
    // }


}