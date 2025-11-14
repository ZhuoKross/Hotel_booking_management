package com.microservice.host.Services;

import com.microservice.host.DTO.HostDTO;
import com.microservice.host.Entity.Host;
import com.microservice.host.Repository.HostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HostService {

    private HostRepository hostRepository;

    public HostService(HostRepository hostRepository){
        this.hostRepository = hostRepository;
    }

    public List<Host> getAllHosts (){
        return hostRepository.findAll();
    }

    public HostDTO getOneHost(Long idHost){
        Host hostFound = hostRepository.findById(idHost).orElse(null);

        HostDTO hostDTO = HostDTO.builder()
                .id(hostFound.id)
                .isVipHost(hostFound.isVipHost)
                .isRegularHost(hostFound.isRegularHost)
                .document(hostFound.document)
                .name(hostFound.name)
                .build();

        return hostDTO;
    }

    public Host createHost(HostDTO hostDTO){

        Host hostEntity = Host.builder()
                .isRegularHost(hostDTO.isRegularHost())
                .isVipHost(hostDTO.isVipHost())
                .document(hostDTO.document())
                .name(hostDTO.name())
                .build();

        return hostRepository.save(hostEntity);
    }

    public Host updateHost(Long idHost, HostDTO hostDTO){

        Host hostEntity = Host.builder()
                .isVipHost(hostDTO.isVipHost())
                .isRegularHost(hostDTO.isRegularHost())
                .document(hostDTO.document())
                .name(hostDTO.name())
                .build();


        Host hostToUpdate = hostRepository.findById(idHost).orElse(null);

        hostToUpdate.isRegularHost = hostEntity.isRegularHost;
        hostToUpdate.isVipHost = hostEntity.isVipHost;
        hostToUpdate.document = hostEntity.document;
        hostToUpdate.name = hostEntity.name;

        return hostRepository.save(hostToUpdate);
    }

    public void deleteHost(Long idHost){
         hostRepository.deleteById(idHost);
    }
}
