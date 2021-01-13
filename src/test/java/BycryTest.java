import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/11/6 17:33
 */
public class BycryTest {

    @Test
    public void test(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password1 = bCryptPasswordEncoder.encode("5fab340980324f629e687d26ced48298");
        String password2 = bCryptPasswordEncoder.encode("d34c7b26ef614456aa06ba4acfe0b589");
        String password3 = bCryptPasswordEncoder.encode("b0fca803239140a3a679b4eb1808da60");

        System.out.println(password1);
        System.out.println(password2);
        System.out.println(password3);
    }

}
