package com.microservice.host;

import com.microservice.host.Entity.Host;

import java.util.List;
import java.util.Optional;

public class DataMocked {
    public static List<Host> getAllHostMock (){
        return List.of(
                new Host(2L, true, false, 100.00, 123,"Pedro"),
                new Host(3L, false, true, 600.00, 234, "Sebastián"),
                new Host(4L, true, false, 200.00, 456, "Fernando")
        );
    }

    public static Optional<Host> getOneHostMock(){
        return Optional.of(new Host( 5L,true, false, 250.00, 480, "Alfonso"));
    }
}
