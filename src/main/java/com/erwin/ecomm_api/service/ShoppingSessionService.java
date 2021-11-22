package com.erwin.ecomm_api.service;

import com.erwin.ecomm_api.domain.ShoppingSession;
import com.erwin.ecomm_api.model.ShoppingSessionDTO;
import com.erwin.ecomm_api.repos.ShoppingSessionRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class ShoppingSessionService {

    private final ShoppingSessionRepository shoppingSessionRepository;

    public ShoppingSessionService(final ShoppingSessionRepository shoppingSessionRepository) {
        this.shoppingSessionRepository = shoppingSessionRepository;
    }

    public List<ShoppingSessionDTO> findAll() {
        return shoppingSessionRepository.findAll()
                .stream()
                .map(shoppingSession -> mapToDTO(shoppingSession, new ShoppingSessionDTO()))
                .collect(Collectors.toList());
    }

    public ShoppingSessionDTO get(final Integer id) {
        return shoppingSessionRepository.findById(id)
                .map(shoppingSession -> mapToDTO(shoppingSession, new ShoppingSessionDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public ShoppingSessionDTO getByUserId(final Integer userId) {
        var obj =  shoppingSessionRepository.findByuserId(userId);
        var dto = new ShoppingSessionDTO();
        mapToDTO(obj, dto);

        return dto;
                /*.map(shoppingSession -> mapToDTO(shoppingSession, new ShoppingSessionDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));*/

        /*return shoppingSessionRepository.findById(id)
                .map(shoppingSession -> mapToDTO(shoppingSession, new ShoppingSessionDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));*/
    }

    public Integer create(final ShoppingSessionDTO shoppingSessionDTO) {
        final ShoppingSession shoppingSession = new ShoppingSession();
        mapToEntity(shoppingSessionDTO, shoppingSession);
        return shoppingSessionRepository.save(shoppingSession).getId();
    }

    public void update(final Integer id, final ShoppingSessionDTO shoppingSessionDTO) {
        final ShoppingSession shoppingSession = shoppingSessionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(shoppingSessionDTO, shoppingSession);
        shoppingSessionRepository.save(shoppingSession);
    }

    public void delete(final Integer id) {
        shoppingSessionRepository.deleteById(id);
    }

    private ShoppingSessionDTO mapToDTO(final ShoppingSession shoppingSession,
            final ShoppingSessionDTO shoppingSessionDTO) {
        shoppingSessionDTO.setId(shoppingSession.getId());
        shoppingSessionDTO.setUserId(shoppingSession.getUserId());
        shoppingSessionDTO.setTotal(shoppingSession.getTotal());
        return shoppingSessionDTO;
    }

    private ShoppingSession mapToEntity(final ShoppingSessionDTO shoppingSessionDTO,
            final ShoppingSession shoppingSession) {
        shoppingSession.setUserId(shoppingSessionDTO.getUserId());
        shoppingSession.setTotal(shoppingSessionDTO.getTotal());
        return shoppingSession;
    }

}
