package com.microservice.rooms.Utils;

import com.microservice.rooms.DTO.RoomDTO;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

@Component
public class RoomUtils {

    public float totalValue = 0;
    public final float valueOfWifiBenefit = 10.5f;
    public final float valueOfTvBenefit = 10.5f;
    public final float valuePerBed = 11.8f;
    public final float valuePerPerson = 16.5f;

    public float calculateValueRoom(RoomDTO room){
        DecimalFormat df = new DecimalFormat("#.00");
        totalValue += room.personsCapacity() * valuePerPerson;
        totalValue += room.numBeds() * valuePerBed;
        if(room.hasWifi()){
            totalValue += valueOfWifiBenefit;
        }
        if (room.hasTv()){
            totalValue += valueOfTvBenefit;
        }
        room.categories().forEach((categoryDTO) -> {
            if (categoryDTO.name().equals("luxury")){
                totalValue += (totalValue * 10) / 100;
            }
            if(categoryDTO.name().equals("VIP")){
                totalValue += (totalValue * 10) / 100;
            }
        });

        return Float.parseFloat(df.format(totalValue));
    }
}
