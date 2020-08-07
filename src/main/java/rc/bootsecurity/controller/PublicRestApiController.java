package rc.bootsecurity.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rc.bootsecurity.db.UserRepository;
import rc.bootsecurity.model.User;

import java.util.List;

@RestController
@RequestMapping("api/public")
@CrossOrigin
public class PublicRestApiController {
    private UserRepository userRepository;

    public PublicRestApiController(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    
    // Available to all authenticated users
    @GetMapping("test")
    public String test1(){
    	System.out.println("Contorller의 PublicRestApiController.java의 test1에 왔습니다");
        return "API Test";
    }
    
    // Available to managers
    @GetMapping("management/reports")
    public String reports(){
    	System.out.println("Contorller의 PublicRestApiController.java의 reports에 왔습니다");
        return "Some report data";
    }
    
    // Available to ROLE_ADMIN
    @GetMapping("admin/users")
    public List<User> users(){
    	System.out.println("Contorller의 PublicRestApiController.java의 users에 왔습니다");
    	System.out.println("Contorller의 PublicRestApiController.java의 users의 userRepoitory.findAll() ="+userRepository.findAll());
        return this.userRepository.findAll();
    }
}
