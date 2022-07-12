package com.api.manager.controllers;

import com.api.manager.dtos.PatrimonyDto;
import com.api.manager.models.PatrimonyModel;
import com.api.manager.models.UserModel;
import com.api.manager.services.PatrimonyService;
import com.api.manager.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("/patrimonies")
public class PatrimonyController {

    @Autowired
    PatrimonyService patrimonyService;

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<Object> savePatrimony(@RequestBody @Valid PatrimonyDto patrimonyDto){
        Optional<UserModel> userModelOptional = userService.findById(patrimonyDto.getUser().getId());
        if(!userModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        var patrimonyModel = new PatrimonyModel();
        BeanUtils.copyProperties(patrimonyDto,patrimonyModel);

        patrimonyModel.setCreatedAt(LocalDateTime.now());
        patrimonyModel.setUpdatedAt(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CREATED).body(patrimonyService.save(patrimonyModel));
    }

    @GetMapping
    public ResponseEntity<List<PatrimonyModel>> getAllPatrimonies(){
        return ResponseEntity.status(HttpStatus.OK).body(patrimonyService.findAll());
    }
}
