package rc.bootsecurity.db;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rc.bootsecurity.model.User;

import java.util.Arrays;
import java.util.List;

@Service
public class DbInit implements CommandLineRunner {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public DbInit(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    	System.out.println("db.DbInit.java의 DbInit에 왔습니다.");
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    	System.out.println("db.DbInit.java의 DbInit의 userRepository"+userRepository);
    	System.out.println("db.DbInit.java의 DbInit의 passwordEncoder"+passwordEncoder);
    }
    @Override
    public void run(String... args) {
    	System.out.println("db.DbInit.java의 run에 왔습니다");
        // Delete all
        this.userRepository.deleteAll();


        // Crete users
        User dan = new User("dan",passwordEncoder.encode("dan123"),"USER","");
    	System.out.println("db.DbInit.java의 run의 dan = "+dan);
        User admin = new User("admin",passwordEncoder.encode("admin123"),"ADMIN","ACCESS_TEST1,ACCESS_TEST2");
     	System.out.println("db.DbInit.java의 run의 admin = "+admin);
        User manager = new User("manager",passwordEncoder.encode("manager123"),"MANAGER","ACCESS_TEST1");
     	System.out.println("db.DbInit.java의 run의 manager = "+manager);

        List<User> users = Arrays.asList(dan,admin,manager);
     	System.out.println("db.DbInit.java의 run의 users(이놈은 리스트) = "+users);



        // Save to db
        this.userRepository.saveAll(users);
    }
}
