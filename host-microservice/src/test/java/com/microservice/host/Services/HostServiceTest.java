package com.microservice.host.Services;

import com.microservice.host.DTO.HostResponseDTO;
import com.microservice.host.DataMocked;
import com.microservice.host.Entity.Host;
import com.microservice.host.Repository.HostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HostServiceTest {

    @Mock
    private HostRepository hostRepository;

    @InjectMocks
    HostService hostService;


    @Test
    void getAllHosts() {
        when(hostRepository.findAll()).thenReturn(DataMocked.getAllHostMock());
        List<Host> hostList = hostService.getAllHosts();
        assertNotNull(hostList);
    }

    @Test
    void getOneHost() {
        Long idHost = 3L;

        when(hostRepository.findById(anyLong())).thenReturn(DataMocked.getOneHostMock());

        HostResponseDTO hostFound = hostService.getOneHost(idHost);

        assertNotNull(hostFound);
        assertInstanceOf(HostResponseDTO.class, hostFound);

    }

    @Test
    void createHost() {
    }

    @Test
    void updateHost() {
    }

}