package umtaxi.springserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import umtaxi.springserver.model.SchoolEntity;
import umtaxi.springserver.model.UserEntity;
import umtaxi.springserver.repository.UserRepository;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {this.userRepository = userRepository;}



    public void addUser (UserEntity user) {
        userRepository.save(user);
    }
    public UserEntity findUserById (long id ){
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return null;//new UserEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return user.get();
    }

    public UserEntity authenticate(String phone, String password){
        Optional<UserEntity> user = this.userRepository.findByPhoneNumber(phone);
        if(user.isPresent()){
            UserEntity loginUser = user.get();
            if(loginUser.password == password){ return loginUser;}
        }
        return null;
    }

}
