package com.microservice.host.Services;

import com.microservice.host.DTO.HostDTO;
import com.microservice.host.Entity.Host;
import com.microservice.host.Repository.HostRepository;
import com.microservice.host.exceptions.DocumentLengthNotValidException;
import com.microservice.host.exceptions.HostNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HostService {

    private HostRepository hostRepository;

    public HostService(HostRepository hostRepository) {
        this.hostRepository = hostRepository;
    }

    public List<HostDTO> getAllHosts() {
        List<Host> hostListEntitys = hostRepository.findAll();

        if (hostListEntitys.isEmpty()) {
            throw new HostNotFoundException();
        }

        return hostListEntitys.stream().map((host) -> HostDTO.builder()
                        .id(host.id)
                        .isVipHost(host.isVipHost)
                        .isRegularHost(host.isRegularHost)
                        .document(host.document)
                        .name(host.name)
                        .build())
                        .toList();
    }

    public HostDTO getOneHost(Long idHost) {
        if(idHost == null){
            throw new IllegalArgumentException();
        }
        Host hostFound = hostRepository.findById(idHost).orElseThrow(HostNotFoundException::new);

        return HostDTO.builder()
                .id(hostFound.id)
                .isVipHost(hostFound.isVipHost)
                .isRegularHost(hostFound.isRegularHost)
                .document(hostFound.document)
                .name(hostFound.name)
                .build();
    }

    public HostDTO createHost(HostDTO hostDTO) {

        if (String.valueOf(hostDTO.document()).length() < 10){
            throw new DocumentLengthNotValidException();
        }

        Host hostEntity = Host.builder()
                .isRegularHost(hostDTO.isRegularHost())
                .isVipHost(hostDTO.isVipHost())
                .document(hostDTO.document())
                .name(hostDTO.name())
                .build();

        Host hostCreated = hostRepository.save(hostEntity);

        return HostDTO.builder()
                .id(hostCreated.id)
                .isRegularHost(hostCreated.isRegularHost)
                .isVipHost(hostCreated.isVipHost)
                .document(hostCreated.document)
                .name(hostCreated.name)
                .build();
    }

    public HostDTO updateHost(Long idHost, HostDTO hostDTO) {

        if (String.valueOf(hostDTO.document()).length() < 10){
            throw new DocumentLengthNotValidException();
        }

        Host hostEntity = Host.builder()
                .isVipHost(hostDTO.isVipHost())
                .isRegularHost(hostDTO.isRegularHost())
                .document(hostDTO.document())
                .name(hostDTO.name())
                .build();

        Host hostToUpdate = hostRepository.findById(idHost).orElseThrow(HostNotFoundException::new);

        hostToUpdate.isRegularHost = hostEntity.isRegularHost;
        hostToUpdate.isVipHost = hostEntity.isVipHost;
        hostToUpdate.document = hostEntity.document;
        hostToUpdate.name = hostEntity.name;

        hostRepository.save(hostToUpdate);

        return HostDTO.builder()
                .id(hostToUpdate.id)
                .isVipHost(hostToUpdate.isVipHost)
                .isRegularHost(hostToUpdate.isRegularHost)
                .document(hostToUpdate.document)
                .name(hostToUpdate.name)
                .build();
    }

    public void deleteHost(Long idHost) {
        if (idHost == null){
            throw new IllegalArgumentException();
        }
        hostRepository.deleteById(idHost);
    }
}
