package com.example.clevervision.controller;

import com.example.clevervision.model.*;
import com.example.clevervision.service.BusService;
import com.example.clevervision.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {
    private final UsersService usersService;
    private final BusService busService;
    @Autowired
    private HttpServletRequest request;

    public AdminController(UsersService usersService, BusService busService) {
        this.usersService = usersService;
        this.busService = busService;
    }
 @GetMapping("/dashboard")
 public String showDashboard(Model model , HttpSession session)
 {
     UsersModel user = (UsersModel) session.getAttribute("user");
     if (user != null) {
     model.addAttribute("user",user);

         int nbUsers = usersService.nbUsers();
         int nbBuses = busService.showBusList().size();
         int nbVoyAttente = busService.nbVoyageAttente();
         List<UsersModel> drivers = usersService.listDrivers();
         List<UsersModel> admins = usersService.listAdmins();
         int nbBusDispo = busService.showBusDispoList().size();
         int nbReports = usersService.nbReports();
         model.addAttribute("nbUsers",nbUsers);
         model.addAttribute("nbBuses",nbBuses);
         model.addAttribute("nbVoyAttente",nbVoyAttente);
         model.addAttribute("drivers",drivers);
         model.addAttribute("admins",admins);
         model.addAttribute("nbReports",nbReports);
         model.addAttribute("nbBusDispo",nbBusDispo);
         return "dashboard_page";
                      }
     else {
     return "redirect:/login";
       }
 }









    //Main dashboard(users)
    @GetMapping("/usersDashboard")
    public String showDashboardUsers(Model model,
                                HttpSession session,
                                @RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(name = "size", defaultValue = "4") int size
    ) {
        UsersModel user = (UsersModel) session.getAttribute("user");
        if (user != null) {
            Page<UsersModel> UsersList = usersService.getAllUsersParPage(page , size);
//            List<UsersModel> UsersList = usersService.listUsers();
            model.addAttribute("UsersList", UsersList);
            model.addAttribute("pages", new int[UsersList.getTotalPages()]);
            model.addAttribute("currentPage", page);
            model.addAttribute("user",user);
            return "dashboardUsers_page";
        } else {
            return "redirect:/login";
        }
    }
    @PostMapping("/editRole")
    public String editRole(@RequestParam("id") int id,@RequestParam("role") String role ,
                           Model model, HttpSession session,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "4") int size)
    {
        UsersModel user = (UsersModel) session.getAttribute("user");
        Page<UsersModel> UsersList = usersService.getAllUsersParPage(page , size);
        model.addAttribute("UsersList", UsersList);
        model.addAttribute("pages", new int[UsersList.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("user",user);
        usersService.EditRole(role,id);
        return "dashboardUsers_page";
    }

    @PostMapping("/search")
    public String SearchByName( Model model,
                                @RequestParam(name="nom") String nom,
                                @RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(name = "size", defaultValue = "2") int size,
                                HttpSession session){

        Page<UsersModel> filtredUsers = usersService.searchUsersContainingName(nom,page,size);
        UsersModel user = (UsersModel) session.getAttribute("user");
        if(filtredUsers!=null)
        {
            model.addAttribute("UsersList", filtredUsers);
            model.addAttribute("pages", new int[filtredUsers.getTotalPages()]);
            model.addAttribute("currentPage", page);
            String msg = "Users found :";
            model.addAttribute("foundMessage", msg);
            model.addAttribute("user",user);
        }
        else{
            Page<UsersModel> UsersList = usersService.getAllUsersParPage(page , size);
            model.addAttribute("UsersList", UsersList);
            model.addAttribute("pages", new int[UsersList.getTotalPages()]);
            model.addAttribute("currentPage", page);
            model.addAttribute("user",user);
        }
        return "dashboardUsers_page";
    }
//TraveLS
    @GetMapping("/travelDashboard")
    public String showTravelDashboardPage(Model model, HttpSession session) {
        UsersModel user = (UsersModel) session.getAttribute("user");
        if (user != null && user.getRole()==3) {
            model.addAttribute("user", user);
            List<UsersModel> DriversList = usersService.listDrivers();
            List<BusModel> BusList= busService.showBusDispoList();
            List<TravelModel> VoyageList = busService.listVoyageMain();
            model.addAttribute("DriversList", DriversList);
            model.addAttribute("BusList", BusList);
            model.addAttribute("VoyageList", VoyageList);

            return "TravelDashboard_page";
        } else {
            return "redirect:/login";
        }

    }

    @PostMapping("/deleteTravel")
    public String deleteTravel(@RequestParam(name="voyId") int voyId , @RequestParam(name="busId") int busId
    ,@RequestParam(name="driverId") int driverId)
    {
    busService.deleteVoy(voyId,driverId,busId);
    return "redirect:/travelDashboard";
    }

    //Reports
    @GetMapping("/reportsDashboard")
    public String showReportsDashboard(Model model,
                                       HttpSession session,
                                       @RequestParam(name = "page", defaultValue = "0") int page,
                                       @RequestParam(name = "size", defaultValue = "4") int size)
    {

        UsersModel user = (UsersModel) session.getAttribute("user");
        if (user != null) {
            Page<ReportModel> ReportsList = usersService.getAllReportsParPage(page,size);
            model.addAttribute("ReportsList", ReportsList);
            model.addAttribute("pages", new int[ReportsList.getTotalPages()]);
            model.addAttribute("currentPage", page);
            model.addAttribute("user",user);
            return "dashboardReports";
        } else {
            return "redirect:/login";
        }
    }

// Bus Manager
    @GetMapping("/dashboardBus")
    public String showDashboardBusPage(Model model, HttpSession session) {
        UsersModel user = (UsersModel) session.getAttribute("user");
        if (user != null && user.getRole()==3) {
            List<BusModel> BusList = busService.showBusList();
            model.addAttribute("user", user);
            model.addAttribute("BusList",BusList);
            return "dashboardBus_page";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/dashboardBus/deleteBus")
    public String DeleteBus(Model model , HttpSession session ,
                            @RequestParam("busId") int mat
    )
    {
        UsersModel user = (UsersModel) session.getAttribute("user");
        busService.DeleteBus(mat);
        model.addAttribute("user",user);
        return "redirect:/dashboardBus?successDelete";
    }

//Driver
    @GetMapping("/driverDashboard")
    public String showDashboardDriverPage(Model model, HttpSession session) {
        UsersModel user = (UsersModel) session.getAttribute("user");

        if (user != null && user.getRole()==2) {
            model.addAttribute("user", user);
            List<TravelModel> VoyageList = busService.listVoyage(user.getId());
            model.addAttribute("VoyageList", VoyageList);
            return "driverDashboard_page";
        } else {
            return "redirect:/login";
        }
    }

@GetMapping("/completedTravels")
    public String showCompletedTravelsPage(Model model , HttpSession session)
{
    UsersModel user = (UsersModel) session.getAttribute("user");
    if (user != null && user.getRole()==3) {
        model.addAttribute("user", user);
        List<CompletedTravelModel> completedTravelModelList = busService.showCompletedTravels();
        if(completedTravelModelList !=null) {
            model.addAttribute("VoyageList", completedTravelModelList);
        }
        return "CompletedTravelDashboard_page";
    } else {
        return "redirect:/login";
    }
}


}

