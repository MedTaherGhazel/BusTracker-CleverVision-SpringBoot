package com.example.clevervision.service;

import com.example.clevervision.model.CompletedTravelModel;
import com.example.clevervision.model.ReportModel;
import com.example.clevervision.model.UsersModel;
import com.example.clevervision.model.TravelModel;
import com.example.clevervision.repository.TravelsRepository;
import com.example.clevervision.repository.ReportRepository;
import com.example.clevervision.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final TravelsRepository travelsRepository;
    private final ReportRepository reportRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public UsersService(UsersRepository usersRepository, TravelsRepository travelsRepository, ReportRepository reportRepository) {
        this.usersRepository = usersRepository;
        this.travelsRepository = travelsRepository;
        this.reportRepository = reportRepository;
    }


    public UsersModel registerUser(String login , String password , String email)
    {
        //Verification Email unique
        if(usersRepository.findFirstByEmail(email)!=null)
        {
            System.out.println("Email is already used by another account");
            return null;
        }
        //Verification des champs
        else if(login !=null && password !=null){
            UsersModel newUser = new UsersModel();
            newUser.setLogin(login);
            //Spring security pour le cryptage de mot de passe
            String encodedPassword = passwordEncoder.encode(password);
            newUser.setPassword(encodedPassword);
            newUser.setEmail(email);
            newUser.setRole(1);
            return usersRepository.save(newUser);
        }
        else
        {
            return null;
        }
    }


    public UsersModel registerAdmin(String login , String password , String email)
    {
        //Verification Email unique
        if(usersRepository.findFirstByEmail(email)!=null)
        {
            System.out.println("Email is already used by another account");
            return null;
        }
        //Verification des champs
        else if(login !=null && password !=null){
            UsersModel newUser = new UsersModel();
            newUser.setLogin(login);
            //Spring security pour le cryptage de mot de passe
            String encodedPassword = passwordEncoder.encode(password);
            newUser.setPassword(encodedPassword);
            newUser.setEmail(email);
            newUser.setRole(3);
            return usersRepository.save(newUser);
        }
        else
        {
            return null;
        }
    }

    public UsersModel authenticate(String email , String password)
    {
        // 1 - verification de l"email
        UsersModel user = usersRepository.findFirstByEmail(email);
        if (user != null) {
            String encodedPassword = user.getPassword();
            //spring security
            if( passwordEncoder.matches(password, encodedPassword))
            {
                return user;
            }
        }
        return null;
    }

    public UsersModel UpdateProfile(String ReferenceEmail,String NewLogin , String NewEmail)
    {
        UsersModel Existinguser = usersRepository.findFirstByEmail(ReferenceEmail);
        if(Existinguser!=null && NewEmail != ReferenceEmail) {
            UsersModel checkNewEmail = usersRepository.findFirstByEmail(NewEmail);
            if(checkNewEmail == null) {
                Existinguser.setEmail(NewEmail);
            }
            Existinguser.setLogin(NewLogin);
            UsersModel UpdatedUser = usersRepository.save(Existinguser);
            return UpdatedUser;
        }
        else{
            return null;
        }
    }

    public UsersModel ChangePassword(String ReferenceEmail ,String currentPassword , String newPassword)
    {
        UsersModel Existinguser = usersRepository.findFirstByEmail(ReferenceEmail);
        if(Existinguser!=null)
        {
            String encodedPassword =Existinguser.getPassword();
            if( passwordEncoder.matches(currentPassword, encodedPassword))
            {
                Existinguser.setPassword(passwordEncoder.encode(newPassword));
                UsersModel UpdatedUser = usersRepository.save(Existinguser);
                return UpdatedUser;
            }
            else return  null;
        }
        else return null;
    }
    public List<UsersModel> listUsers()
    {
        List<UsersModel> Users = usersRepository.findAll();
     return Users;
    }
    public Page<UsersModel> getAllUsersParPage(int page, int size) {
        // TODO Auto-generated method stub
        return usersRepository.findAll(PageRequest.of(page, size));
    }

    public List<UsersModel> listDrivers()
    {
        List<UsersModel> Users = usersRepository.findAllByRoleEquals(2);
        return Users;
    }
    public List <UsersModel> listAdmins()
    {
       List<UsersModel>Admins = usersRepository.findAllByRoleEquals(3);
      return Admins;
    }

    public int nbUsers()
    {
         int Users = usersRepository.findAll().size();
         return Users;
    }

public void deleteUser(int id)
{
    usersRepository.deleteById(id);
}
public void EditRole(String role,int id)
{
    UsersModel user = usersRepository.findFirstById(id);
    if (role.equals("User"))
    {
    user.setRole(1);
    }
    else if(role.equals("Driver"))
    {
        user.setRole(2);
    }
    else{
        user.setRole(3);
    }
    usersRepository.save(user);
}

    public Page<UsersModel> searchUsersContainingName(String name, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return usersRepository.findAllByLoginContaining(name,pageable);

    }


    public ReportModel Report(int type , String desc , int voyId , String username)
    {

    ReportModel reportModel = new ReportModel();
    reportModel.setDescription(desc);
    reportModel.setType(type);
    reportModel.setReportername(username);
    if(voyId!=0)
    {
        TravelModel travelModel = travelsRepository.findFirstById(voyId);
        reportModel.setPassedTravelTime(travelModel.getHeureDepart());
    }
      reportRepository.save(reportModel);
     return  reportModel;
    }

    public ReportModel Report2(int type , String desc , String username)
    {
        ReportModel reportModel = new ReportModel();
        reportModel.setDescription(desc);
        reportModel.setType(type);
        reportModel.setReportername(username);
        reportRepository.save(reportModel);
        return  reportModel;
    }
    public Page<ReportModel> getAllReportsParPage(int page, int size) {
        // TODO Auto-generated method stub
        return reportRepository.findAll(PageRequest.of(page, size));
    }

    public int nbReports()
    {
        return  reportRepository.findAll().size();
    }




}
