package org.example.mi_bms.controller;


import org.example.mi_bms.response.R;
import org.example.mi_bms.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/update")
    public R upadteVehicle(@RequestParam Integer carid,
                           @RequestParam Integer batteryType,
                           @RequestParam Double totalDistance,
                           @RequestParam Integer batteryHealth ) throws Exception{
        String result;
        try{
            result = vehicleService.updateVehicle(carid, batteryType, totalDistance, batteryHealth);
            return R.success(result);
        }catch (Exception e){
            return R.error(e.getMessage());
        }
    }
}
