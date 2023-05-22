package com.example.clevervision.controller;

import com.example.clevervision.model.UsersModel;
import com.example.clevervision.service.BusService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalTime;

@Controller
public class BusController {

 private final BusService busService;
    @Autowired
    private HttpServletRequest request;


    public BusController(BusService busService) {
        this.busService = busService;
    }

    @PostMapping("/StartBus")
    public String StartBus(Model model, HttpSession session,
                           @RequestParam("driverId") int driverId,
                           @RequestParam("destination") int destination,
                           @RequestParam("busId") int busId,
                           @RequestParam("startTime") LocalTime startTime
    )
   {
       UsersModel user = (UsersModel) session.getAttribute("user");
       busService.AddVoyage(busId , destination,driverId , startTime);
       busService.updateBusPositions();
       model.addAttribute("user",user);
       return("redirect:/main");
   }
   @PostMapping("/dashboard/StartBusNow")
    public String StartBusNow(Model model , HttpSession session , @RequestParam("id") int id )
   {
       UsersModel user = (UsersModel) session.getAttribute("user");
       busService.StartBusNow(id);
       model.addAttribute("user",user);
       return("redirect:/main");
   }

   @PostMapping("/AddBus")
   public String AddBus(Model model , HttpSession session ,
                        @RequestParam("mat") int mat ,
                        @RequestParam("marque") String marque )
   {
       UsersModel user = (UsersModel) session.getAttribute("user");
      Boolean bus =  busService.AddBus(mat , marque);
       model.addAttribute("user",user);
       return bus == true ?  "redirect:/dashboardBus?success" : "redirect:/dashboardBus?error";
   }
   @PostMapping("/EditBus")
    public String EditBus(Model model , HttpSession session ,
                          @RequestParam("id") int mat ,
                          @RequestParam("Newmarque") String marque )
   {
       UsersModel user = (UsersModel) session.getAttribute("user");
       Boolean bus =  busService.EditBus(mat,marque);
       model.addAttribute("user",user);
       return bus == true ?  "redirect:/dashboardBus" : "redirect:/dashboardBus?";
   }


}
