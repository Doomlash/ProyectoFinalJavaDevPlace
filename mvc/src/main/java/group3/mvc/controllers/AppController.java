package group3.mvc.controllers;

import group3.mvc.model.MyUser;
import group3.mvc.model.request.RelationRequest;
import group3.mvc.services.implementation.IMyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import group3.mvc.model.FormRequest;
import group3.mvc.services.MiddleService;

import java.util.List;
import java.util.stream.Collectors;


@Controller
public class AppController {

    @Autowired
    MiddleService s;

    @Autowired
    private IMyUser iMU;

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

    @GetMapping("/addContact")
    public String add(Model model){
        model.addAttribute("users",iMU.listAllUsers());
        return "addContact";
    }

    @GetMapping("/search")
    public String search(@RequestParam("id") long id, Model model){
        //List<MyUser> userList = iMU.readU(id).stream().collect(Collectors.toList());
        MyUser user = iMU.readU(id);
        RelationRequest rr = new RelationRequest();
        model.addAttribute("users",user);
        model.addAttribute("relation",rr);
        return "addContact";
    }

//    @PostMapping("/contact/{idc}")
//    public Integer addContact(@PathVariable("idc") Long id){
//
//        return iMU.addC();
//    }


}