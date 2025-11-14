package com.microservice.host.controller;


import com.microservice.host.DTO.HostDTO;
import com.microservice.host.Entity.Host;
import com.microservice.host.Services.HostService;
import com.microservice.host.Utils.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/hosts")
public class HostController {

    private HostService hostService;

    public HostController(HostService hostService){
        this.hostService = hostService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<HostDTO>> getAllHosts (){
        List<Host> listOfEntityHost =  hostService.getAllHosts();

        List<HostDTO> hostDTOList = listOfEntityHost.stream()
                .map((host) -> new HostDTO(host.id,host.isVipHost, host.isRegularHost,host.document, host.name))
                .toList();

        return ResponseEntity.ok().body(hostDTOList);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getHost(@PathVariable Long id){
        HostDTO hostFound = hostService.getOneHost(id);

        if(hostFound != null) {
            return ResponseEntity.ok(hostFound);
        }else {
            return ResponseEntity.unprocessableEntity().body("Can't get the host you searched :(");
        }
    }


    @PostMapping("/create")
    public ResponseEntity<?> createHost(@RequestBody HostDTO hostDTO){
        try {
            Host hostResponse = hostService.createHost(hostDTO);
            Response responseSuccess = new Response<Host>("Host created Succesfully", LocalDateTime.now(), hostResponse);
            return ResponseEntity.ok(responseSuccess);
        } catch (Exception e) {
            Response responseFailure = new Response<String>("Can't create the host", LocalDateTime.now(), e.getMessage());
            return ResponseEntity.unprocessableEntity().body(responseFailure);
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateHost(@PathVariable("id") Long idHost, @RequestBody HostDTO hostDTO){
        try {
            Host response = hostService.updateHost(idHost, hostDTO);
            Response responseSuccess = new Response<Host>("Host updated Succesfully", LocalDateTime.now(), response);
            return ResponseEntity.ok(responseSuccess);
        } catch (Exception e) {
            Response responseFailure = new Response<String>("Can't update the host", LocalDateTime.now(), e.getMessage());
            return ResponseEntity.unprocessableEntity().body(responseFailure);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteHost (@PathVariable("id") Long idHost){
        try {
            hostService.deleteHost(idHost);
            Response responseSuccess = new Response<String>("Host deleted succesfully", LocalDateTime.now(), "no data");
            return ResponseEntity.ok(responseSuccess);
        } catch (Exception e) {
            Response responseFailure = new Response<String>("An error has ocurred", LocalDateTime.now(), e.getMessage());
            return ResponseEntity.unprocessableEntity().body(responseFailure);
        }
    }
}
