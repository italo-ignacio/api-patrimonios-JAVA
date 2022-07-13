package com.api.manager.controllers;

import com.api.manager.dtos.UserDto;
import com.api.manager.models.UserModel;
import com.api.manager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping
    public ResponseEntity<Object> saveUser(@RequestBody @Valid UserDto userDto){
        Map<String, String> response = new HashMap<>();
        var userModel = new UserModel();

        if(userDto.getName()!=null){userModel.setName(userDto.getName());}
        if(userDto.getEmail()!=null){ userModel.setEmail(userDto.getEmail());}
        if(userDto.getPassword()!=null){userModel.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));}

        userModel.setCreatedAt(LocalDateTime.now());
        userModel.setUpdatedAt(LocalDateTime.now());
        userModel.setIs_admin(false);

        try{
            userService.save(userModel);
            response.put("message", "User created successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            String error = e.getCause().getCause().getMessage();
            response.put("ERROR", "TRUE");
            if(error.contains("Duplicate entry")){
                response.put("message", "E-mail already exists");
            }else {
                response.put("message", "Could not created user");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getByIdUser(@PathVariable(value = "id") Long id){
        Map<String, String> response = new HashMap<>();
        Optional<UserModel> userModelOptional = userService.findById(id);
        if(!userModelOptional.isPresent()){
            response.put("ERROR", "TRUE");
            response.put("message", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id")Long id){
        Map<String, String> response = new HashMap<>();
        Optional<UserModel> userModelOptional = userService.findById(id);
        if(!userModelOptional.isPresent()){
            response.put("ERROR", "TRUE");
            response.put("message", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        try{
            userService.delete(userModelOptional.get());
            response.put("message", "Successfully deleted");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            response.put("ERROR", "TRUE");
            response.put("message", "Could not delete");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id")Long id,@RequestBody @Valid UserDto userDto){
        Map<String, String> response = new HashMap<>();
        Optional<UserModel> userModelOptional = userService.findById(id);
        if(!userModelOptional.isPresent()){
            response.put("ERROR", "TRUE");
            response.put("message", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        var userModel = userModelOptional.get();
        if(userDto.getName()!=null){response.put("Warning", "Cannot change the name");}
        if(userDto.getEmail()!=null){ userModel.setEmail(userDto.getEmail());}
        if(userDto.getPassword()!=null){userModel.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));}

        userModel.setUpdatedAt(LocalDateTime.now());
        try{
            userService.save(userModel);
            response.put("message", "User updated successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            String error = e.getCause().getCause().getMessage();
            response.put("ERROR", "TRUE");
            if(error.contains("Duplicate entry")){
                response.put("message", "E-mail already exists");
            }else {
                response.put("message", "Could not update");
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
