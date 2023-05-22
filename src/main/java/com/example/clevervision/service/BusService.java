package com.example.clevervision.service;

import com.example.clevervision.model.BusModel;
import com.example.clevervision.model.CompletedTravelModel;
import com.example.clevervision.model.UsersModel;
import com.example.clevervision.model.TravelModel;
import com.example.clevervision.repository.CompletedTravelsRepository;
import com.example.clevervision.repository.TravelsRepository;
import com.example.clevervision.repository.GarageRepository;
import com.example.clevervision.repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class BusService {
    private final TravelsRepository travelsRepository;
    private final GarageRepository garageRepository;
    private  final UsersRepository usersRepository;

    private final CompletedTravelsRepository completedTravelsRepository;

    public BusService(TravelsRepository travelsRepository, GarageRepository garageRepository, UsersRepository usersRepository, CompletedTravelsRepository completedTravelsRepository) {
        this.travelsRepository = travelsRepository;
        this.garageRepository=garageRepository;
        this.usersRepository = usersRepository;
        this.completedTravelsRepository = completedTravelsRepository;
    }
    public TravelModel VoyageData()
    {
        TravelModel travelModel = travelsRepository.findFirstByEnRoute(1);
        if(travelModel !=null){
            return travelModel;
        }
        else{
            return null;
        }
    }

    public List<TravelModel> listVoyage(int id)
    {
        List<TravelModel> VoyageList = travelsRepository.findAllByDriverId(id);
        return VoyageList;
    }
    public List<TravelModel> listVoyageMain()
    {
        List<TravelModel> VoyageList = travelsRepository.findAll();
        return VoyageList;
    }

    public int nbVoyageAttente()
    {
        int nb = travelsRepository.findAllByEnRoute(0).size();
        return nb;
    }


    public Boolean AddBus(int mat , String marque)
    {
        if( garageRepository.findFirstByMat(mat) == null ) {
            BusModel bus = new BusModel();
            bus.setMat(mat);
            bus.setMarque(marque);
            bus.setDispo(true);
            garageRepository.save(bus);
            return true;
        }
        else {
            return false;
        }
    }
    public Boolean EditBus(int mat , String marque)
    {
        BusModel bus = garageRepository.findFirstByMat(mat);
        if( bus!=null) {
            bus.setMarque(marque);
            garageRepository.save(bus);
            return true;
        }
        else {
            return false;
        }
    }

    public void DeleteBus(int id )
    {
        garageRepository.deleteById(id);
    }

    public List<BusModel> showBusList()
    {
        List <BusModel> busList = garageRepository.findAll();
        return busList;
    }
    public List<BusModel> showBusDispoList()
    {
        List <BusModel> busList = garageRepository.findAllByDispo(true);
        return busList;
    }

    public List<CompletedTravelModel> showCompletedTravels()
    {
        List<CompletedTravelModel> voyageList = completedTravelsRepository.findAll();
        return voyageList;
    }


    public void AddVoyage(int busId , int destination , int driverId , LocalTime startTime)
    {
        TravelModel newVoy = new TravelModel();
        newVoy.setHeureDepart(startTime);
        newVoy.setHeureArrive(startTime.plusMinutes(20));
        newVoy.setEnRoute(0);
        if(destination == 1)
        {
            newVoy.setBusPosition(0);
        }
        else{
            newVoy.setBusPosition(101);
        }
        BusModel RelatedBus = garageRepository.findFirstByMat(busId);
        UsersModel RelatedDriver = usersRepository.findFirstById(driverId);
        newVoy.setBus(RelatedBus);
        newVoy.setDestination(destination);
        newVoy.setDriver(RelatedDriver);
        travelsRepository.save(newVoy);
    }

    @Transactional
    public void deleteVoy(int voyId , int driverId , int busId)
    {
        UsersModel driver = usersRepository.findFirstById(driverId);
        BusModel bus = garageRepository.findFirstByMat(busId);
        travelsRepository.deleteByIdAndBusAndDriver(voyId,bus,driver);
    }
    public void StartBusNow(int id)
    {
        TravelModel bus = travelsRepository.findFirstById(id);
        bus.setEnRoute(1);
        bus.getBus().setDispo(false);
        travelsRepository.save(bus);

    }
    @Transactional
    @Scheduled(fixedRate = 1000) // Run every second
    public void updateBusPositions() {
        // Get the current positions of all buses
        TravelModel bus = travelsRepository.findFirstByEnRoute(1);
        if (bus != null) {
            //check if desination 1 : bizerte -> iset / 2 : iset -> bizerte
            if(bus.getDestination()==1){
                //check if bus has arrived or not to 101 : iset
                if (bus.getBusPosition() < 101) {
                    // Update the position of each bus
                    bus.setBusPosition(bus.getBusPosition() + 1);
                    // Save the updated bus positions to the database
                    travelsRepository.save(bus);
                }
                else{
                    bus.setEnRoute(0);
                    BusModel busModel = garageRepository.findFirstByMat(bus.getBus().getMat());
                    busModel.setDispo(true);
                    garageRepository.save(busModel);
                    CompletedTravelModel completedTravelModel = new CompletedTravelModel();
                    completedTravelModel.setId(bus.getId());
                    completedTravelModel.setBusMat(bus.getBus().getMat());
                    completedTravelModel.setDriverId(bus.getDriver().getId());
                    completedTravelModel.setDestination(bus.getDestination());
                    completedTravelModel.setHeureArrive(bus.getHeureArrive());
                    completedTravelModel.setHeureDepart(bus.getHeureDepart());
                    completedTravelsRepository.save(completedTravelModel);
                    deleteVoy(bus.getId(), bus.getDriver().getId() ,bus.getBus().getMat());
                }
            }
            else if (bus.getDestination()==2){
                //check if bus has arrived or not to 0 : bizerte
                if (bus.getBusPosition() > 0) {
                    // Update the position of each bus
                    bus.setBusPosition(bus.getBusPosition() - 1);
                    // Save the updated bus positions to the database
                    travelsRepository.save(bus);
                }

                else{
                    bus.setEnRoute(0);
                    BusModel busModel = garageRepository.findFirstByMat(bus.getBus().getMat());
                    busModel.setDispo(true);
                    garageRepository.save(busModel);
                    CompletedTravelModel completedTravelModel = new CompletedTravelModel();
                    completedTravelModel.setId(bus.getId());
                    completedTravelModel.setBusMat(bus.getBus().getMat());
                    completedTravelModel.setDriverId(bus.getDriver().getId());
                    completedTravelModel.setDestination(bus.getDestination());
                    completedTravelModel.setHeureArrive(bus.getHeureArrive());
                    completedTravelModel.setHeureDepart(bus.getHeureDepart());
                    completedTravelsRepository.save(completedTravelModel);
                    deleteVoy(bus.getId(), bus.getDriver().getId() ,bus.getBus().getMat());
                }
            }
        }
    }
}
