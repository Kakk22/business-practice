import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

/**
 * @author by cyf
 * @date 2020/11/2.
 */
@SpringBootTest
public class test {

    @Test
    void t1(){
        System.out.println(UUID.randomUUID());
    }
}
