package com.microservice.booking.Utils;

import com.microservice.booking.Client.model.HostDTO;
import com.microservice.booking.Client.model.RoomDTO;
import org.springframework.stereotype.Component;

@Component
public class BookingUtils {

    public float calculateTotalPriceBooking(RoomDTO room, HostDTO host, int startDate, int endDate){
        int numDaysBooking = endDate - startDate;
        float valueDiscountVipHost = 0;
        float valueDiscountNumberOfVisits = 0;
        float valuePerNightBooking = 0;
        float totalPriceBooking = room.price();

        if (room.personsCapacity() == 2){
             valuePerNightBooking= 7.5f;
        }
        if(room.personsCapacity() > 2 && room.personsCapacity() <= 4){
            valuePerNightBooking = 10;
        }

        totalPriceBooking += numDaysBooking * valuePerNightBooking;

        if(host.isVipHost()){
            valueDiscountVipHost = (totalPriceBooking * 10) / 100;
            totalPriceBooking -= valueDiscountVipHost;
        }
        if(host.numVisits() >= 2 && host.numVisits() <= 4){
            valueDiscountNumberOfVisits = (totalPriceBooking * 5) / 100;
            totalPriceBooking -= valueDiscountNumberOfVisits;
        }
        if(host.numVisits() > 5 && host.numVisits() <= 10){
            valueDiscountNumberOfVisits = (totalPriceBooking * 8) / 100;
            totalPriceBooking -= valueDiscountNumberOfVisits;
        }

        return totalPriceBooking;
    }
}
