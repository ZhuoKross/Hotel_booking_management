package com.microservice.host.controller;


import com.microservice.host.DTO.HostInputDTO;
import com.microservice.host.DTO.HostResponseDTO;
import com.microservice.host.Services.HostService;
import com.microservice.host.Utils.Response;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/hosts")
public class HostController {

    private HostService hostService;

    public HostController(HostService hostService){
        this.hostService = hostService;
    }


    @GetMapping("/all")
    public ResponseEntity<Response<List<HostResponseDTO>>> getAllHosts() {
        List<HostResponseDTO> hostResponseDTOList = hostService.getAllHosts();
        return ResponseEntity.ok(new Response<List<HostResponseDTO>>("Host fetched succesfully", LocalDateTime.now(), hostResponseDTOList));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Response<HostResponseDTO>> getHost(@PathVariable("id") Long id) {
        HostResponseDTO hostFound = hostService.getOneHost(id);
        return ResponseEntity.ok(new Response<HostResponseDTO>("Host fetched succesfully", LocalDateTime.now(), hostFound));
    }


    @PostMapping("/create")
    public ResponseEntity<Response<HostResponseDTO>> createHost(@Valid @RequestBody HostInputDTO host) {
        HostResponseDTO hostCreated = hostService.createHost(host);
        return ResponseEntity.ok(new Response<HostResponseDTO>("Host created succesfully", LocalDateTime.now(), hostCreated));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Response<HostResponseDTO>> updateHost(@PathVariable("id") Long idHost, @RequestBody @Valid HostResponseDTO hostResponseDTO) {
        HostResponseDTO hostUpdated = hostService.updateHost(idHost, hostResponseDTO);
        return ResponseEntity.ok(new Response<HostResponseDTO>("Host updated succesfully", LocalDateTime.now(), hostUpdated));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<String>> deleteHost(@PathVariable("id") Long idHost) {
        hostService.deleteHost(idHost);
        return ResponseEntity.ok(new Response<String>("Host deleted succesfully", LocalDateTime.now(), "no data"));
    }
}
