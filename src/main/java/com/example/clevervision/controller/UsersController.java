package com.example.clevervision.controller;


import com.example.clevervision.model.UsersModel;
import com.example.clevervision.service.UsersService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/login")
    public String getLoginPage(Model model)
    {
        model.addAttribute("loginRequest",new UsersModel());
        return "login_page";
    }
//aaaaaaaaaaaaaa
    @PostMapping("/register")
    public String register(@ModelAttribute UsersModel usersModel)
    {

    System.out.println("register request: "+usersModel);
    UsersModel registeredUser = usersService.registerUser(usersModel.getLogin(),usersModel.getPassword(),usersModel.getEmail());
    return registeredUser == null ? "redirect:/login?error" : "redirect:/login" ;
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UsersModel usersModel , Model model , HttpSession session)
    {

        UsersModel authenticated = usersService.authenticate(usersModel.getEmail(),usersModel.getPassword());
        if (authenticated!=null)
        {
//            model.addAttribute("userEmail",usersModel.getEmail());
            //session
           session.setAttribute("user", authenticated);
           System.out.println(authenticated);
            return "redirect:/main";
        }
        else  return "redirect:/login?error";
    }


}
