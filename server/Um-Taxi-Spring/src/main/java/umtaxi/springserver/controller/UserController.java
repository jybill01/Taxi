package umtaxi.springserver.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import umtaxi.springserver.dto.LoginDto;
import umtaxi.springserver.dto.LoginResultDto;
import umtaxi.springserver.dto.UserDto;
import umtaxi.springserver.model.UserEntity;
import umtaxi.springserver.service.UserService;

@RestController()
@RequestMapping(path ="users")
public class UserController {
    private UserService userService;
    @Autowired
    UserController(UserService userService){ this.userService = userService; }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserDto userDto){
        UserEntity userEntity = new UserEntity();
        userEntity.name = userDto.name;
        userEntity.phoneNumber = userDto.phoneNumber;
        userEntity.password = userDto.password;
        this.userService.addUser(userEntity);
    }

    @PostMapping("/login")
    public LoginResultDto login(LoginDto dto){
        UserEntity result  = this.userService.authenticate(dto.phone, dto.password) ;

            if(result == null){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
            LoginResultDto resultDto = new LoginResultDto();
            resultDto.userId = result.id;
            return resultDto;
    }

}
