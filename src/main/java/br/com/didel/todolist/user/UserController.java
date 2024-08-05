package br.com.didel.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * modificador
 * public
 * private
 * protected
 */

 @RestController
 @RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;
    
    /*
     * String (texto)
     * Intereger (int) numeros inteiros
     * Double (double) numeros 0.0000
     * float (float) numeros 0.000
     * char (A C ) caracter
     * Date (data)
     * void 
     */
    /*
     * Body
     */
    @SuppressWarnings("rawtypes")
    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel) {
        var user = this.userRepository.findByUsername(userModel.getUsername());

        if(user !=null){
            //mensagem de erro
            //status Code
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario ja existe");
        }

        var passwordHashred = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());

        userModel.setPassword(passwordHashred);

        var  userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userCreated);
    }
}
