package com.ecommerce.controller;


import com.ecommerce.dto.request.UserRequestDTO;
import com.ecommerce.dto.request.UserUpdateRequestDTO;
import com.ecommerce.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@Api(tags = "endpoint for user")
@RequestMapping("${app.root.url}/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ApiOperation("create user")
    public ResponseEntity<?> create(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        return new ResponseEntity<>(userService.create(userRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping
    @ApiOperation("update user")
    public ResponseEntity<?> update(@Valid @RequestBody UserUpdateRequestDTO userUpdateRequestDTO) {
        return new ResponseEntity<>(userService.update(userUpdateRequestDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation("find user by id")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("delete user by id")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.delete(id), HttpStatus.OK);
    }

    @PostMapping("/logout/all")
    @ApiOperation(" logout all the active sessions on their account")
    public ResponseEntity<?> logoutAllActiveSessions(@RequestParam("username") String username) {
        return new ResponseEntity<>(userService.logoutAllActiveSessions(username), HttpStatus.OK);
    }

}
