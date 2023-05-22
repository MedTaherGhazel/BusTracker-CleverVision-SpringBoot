package com.example.clevervision.controller;

import com.example.clevervision.model.CompletedTravelModel;
import com.example.clevervision.model.UsersModel;
import com.example.clevervision.model.TravelModel;
import com.example.clevervision.service.BusService;
import com.example.clevervision.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {
    private final UsersService usersService;
    private final BusService busService;
    @Autowired
    private HttpServletRequest request;

    public MainController(UsersService usersService, BusService busService) {
        this.usersService = usersService;
        this.busService = busService;
    }

    @GetMapping("/main")
    public String showMainPage(Model model, HttpSession session) {
        UsersModel user = (UsersModel) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
            TravelModel travelModel = busService.VoyageData();
            List<TravelModel> VoyageList = busService.listVoyageMain();
            List<CompletedTravelModel> VoyageList2 = busService.showCompletedTravels();
            if(travelModel !=null) {
                model.addAttribute("VoyageData", travelModel.getBusPosition());
            }
            if (VoyageList2!=null)
            {
                model.addAttribute("CompletedVoyageList",VoyageList2 );
            }
            if(VoyageList!=null)
            {
                model.addAttribute("VoyageList", VoyageList);
            }

            return "main_page";
        } else {
            return "redirect:/login";
        }
    }
    @GetMapping("/main/bus")
    public ResponseEntity<Integer> GetPosition()
    {
        TravelModel travelModel = busService.VoyageData();
        if(travelModel !=null)
        {
            Integer data = travelModel.getBusPosition();
            return ResponseEntity.ok(data);
        }
        else{
            return ResponseEntity.ofNullable(0);
        }
    }
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage() {
       HttpSession session = request.getSession(false);
       if(session!=null)
       {
           session.invalidate();
       }
       return "redirect:/login";
    }


    @GetMapping("/editProfile")
    public String showEditProfilePage(Model model, HttpSession session) {
        UsersModel user = (UsersModel) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
            return "editProfile_page";
        } else {
            return "redirect:/login";
        }
    }



    @PostMapping("/editProfile")
    public String editProfile(@ModelAttribute UsersModel usersModel, Model model, HttpSession session) {
        System.out.println("Edit profile request: " + usersModel);
        UsersModel OldUser = (UsersModel) session.getAttribute("user");
       System.out.println("Old user"+ OldUser.getEmail()+ OldUser.getRole());
        UsersModel UpdatedUser = usersService.UpdateProfile(OldUser.getEmail(), usersModel.getLogin(), usersModel.getEmail());
        if (UpdatedUser != null) {
            session.setAttribute("user", UpdatedUser);
            return "redirect:/main";
        }
        else  return "redirect:/editProfile?error";
        }

        @PostMapping("/ChangePasswordProfile")
    public String ChangePasswordProfile(@RequestParam String currentPassword ,@RequestParam String newPassword ,@RequestParam String confirmPassword, Model model, HttpSession session) {
            System.out.println("Edit profile request: " + currentPassword + " "+ newPassword + " " + confirmPassword);
            if (!newPassword.equals(confirmPassword))
            {
                return "redirect:/editProfile?error1";
            }
            else{
                UsersModel OldUser = (UsersModel) session.getAttribute("user");
                UsersModel UpdatedUser = usersService.ChangePassword(OldUser.getEmail(), currentPassword, newPassword);
                if(UpdatedUser!=null)
                {
                    session.setAttribute("user", UpdatedUser);
                    return "redirect:/main";
                }
                else
                {
                    return "redirect:/editProfile?error";
                }
            }
        }







    @PostMapping("/report")
    public String report( @RequestParam (name="desc") String desc,
                         @RequestParam(name="type") int type,
                         @RequestParam(name="voy" , required = false) Integer voyId ,
                          @RequestParam(name="user") String user
     )
    {

        // Check if voyId is null or not
        if (voyId != 0) {
            // Handle the case when voyId is present
            usersService.Report(type, desc, voyId, user);
        } else {
          usersService.Report2(type,desc,user);
        }
        return "redirect:/main";
    }



    }

