package com.microservice.host.Services;

import com.microservice.host.DTO.HostInputDTO;
import com.microservice.host.DTO.HostResponseDTO;
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

    public List<HostResponseDTO> getAllHosts() {
        List<Host> hostListEntitys = hostRepository.findAll();

        if (hostListEntitys.isEmpty()) {
            throw new HostNotFoundException();
        }

        return hostListEntitys.stream().map((host) -> HostResponseDTO.builder()
                        .id(host.id)
                        .isVipHost(host.isVipHost)
                        .isRegularHost(host.isRegularHost)
                        .document(host.document)
                        .numVisits(host.numVisits)
                        .name(host.name)
                        .build())
                        .toList();
    }

    public HostResponseDTO getOneHost(Long idHost) {
        if(idHost == null){
            throw new IllegalArgumentException();
        }
        Host hostFound = hostRepository.findById(idHost).orElseThrow(HostNotFoundException::new);

        return HostResponseDTO.builder()
                .id(hostFound.id)
                .isVipHost(hostFound.isVipHost)
                .isRegularHost(hostFound.isRegularHost)
                .document(hostFound.document)
                .numVisits(hostFound.numVisits)
                .name(hostFound.name)
                .build();
    }

    public HostResponseDTO createHost(HostInputDTO host) {

        if (String.valueOf(host.document()).length() < 10){
            throw new DocumentLengthNotValidException();
        }

        Host hostEntity = Host.builder()
                .isRegularHost(true)
                .document(host.document())
                .name(host.name())
                .build();

        Host hostCreated = hostRepository.save(hostEntity);

        return HostResponseDTO.builder()
                .id(hostCreated.id)
                .isRegularHost(hostCreated.isRegularHost)
                .isVipHost(hostCreated.isVipHost)
                .document(hostCreated.document)
                .numVisits(hostCreated.numVisits)
                .name(hostCreated.name)
                .build();
    }

    public HostResponseDTO updateHost(Long idHost, HostResponseDTO hostResponseDTO) {

        if (String.valueOf(hostResponseDTO.document()).length() < 10){
            throw new DocumentLengthNotValidException();
        }

        Host hostToUpdate = hostRepository.findById(idHost).orElseThrow(HostNotFoundException::new);

        hostToUpdate.document = hostResponseDTO.document();
        hostToUpdate.numVisits = hostResponseDTO.numVisits();
        hostToUpdate.name = hostResponseDTO.name();

        if(hostToUpdate.numVisits >= 3){
            hostToUpdate.isVipHost = true;
        }else {
            hostToUpdate.isRegularHost = true;
        }

        Host hostUpdated = hostRepository.save(hostToUpdate);

        return HostResponseDTO.builder()
                .id(hostToUpdate.id)
                .isVipHost(hostToUpdate.isVipHost)
                .isRegularHost(hostToUpdate.isRegularHost)
                .document(hostToUpdate.document)
                .numVisits(hostToUpdate.numVisits)
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
