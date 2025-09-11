package com.microservice.host.controller;


import com.microservice.host.DTO.HostDTO;
import com.microservice.host.Entity.Host;
import com.microservice.host.Services.HostService;
import com.microservice.host.Utils.Response;
import jakarta.validation.Valid;
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

    public HostController(HostService hostService) {
        this.hostService = hostService;
    }


    @GetMapping("/all")
    public ResponseEntity<Response<List<HostDTO>>> getAllHosts() {
        List<HostDTO> hostDTOList = hostService.getAllHosts();
        return ResponseEntity.ok(new Response<List<HostDTO>>("Host fetched succesfully", LocalDateTime.now(), hostDTOList));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Response<HostDTO>> getHost(@PathVariable Long id) {
        HostDTO hostFound = hostService.getOneHost(id);
        return ResponseEntity.ok(new Response<HostDTO>("Host fetched succesfully", LocalDateTime.now(), hostFound));
    }


    @PostMapping("/create")
    public ResponseEntity<Response<HostDTO>> createHost(@Valid @RequestBody HostDTO hostDTO) {
        HostDTO hostCreated = hostService.createHost(hostDTO);
        return ResponseEntity.ok(new Response<HostDTO>("Host created succesfully", LocalDateTime.now(), hostCreated));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Response<HostDTO>> updateHost(@PathVariable("id") Long idHost, @RequestBody @Valid HostDTO hostDTO) {
        HostDTO hostUpdated = hostService.updateHost(idHost, hostDTO);
        return ResponseEntity.ok(new Response<HostDTO>("Host updated succesfully", LocalDateTime.now(), hostUpdated));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<String>> deleteHost(@PathVariable("id") Long idHost) {
        hostService.deleteHost(idHost);
        return ResponseEntity.ok(new Response<String>("Host deleted succesfully", LocalDateTime.now(), "no data"));
    }
}
