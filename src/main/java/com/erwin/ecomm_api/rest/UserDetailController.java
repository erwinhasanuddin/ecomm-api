package com.erwin.ecomm_api.rest;

import com.erwin.ecomm_api.model.UserDetailDTO;
import com.erwin.ecomm_api.service.UserDetailService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/userDetails", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserDetailController {

    private final UserDetailService userDetailService;

    public UserDetailController(final UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @GetMapping
    public ResponseEntity<List<UserDetailDTO>> getAllUserDetails() {
        return ResponseEntity.ok(userDetailService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailDTO> getUserDetail(@PathVariable final Integer id) {
        return ResponseEntity.ok(userDetailService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createUserDetail(
            @RequestBody @Valid final UserDetailDTO userDetailDTO) {
        return new ResponseEntity<>(userDetailService.create(userDetailDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUserDetail(@PathVariable final Integer id,
            @RequestBody @Valid final UserDetailDTO userDetailDTO) {
        userDetailService.update(id, userDetailDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserDetail(@PathVariable final Integer id) {
        userDetailService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
