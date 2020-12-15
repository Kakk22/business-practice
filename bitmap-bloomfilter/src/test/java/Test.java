import com.cyf.service.SignService;
import com.cyf.service.impl.SignServiceImpl;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

/**
 * @author 陈一锋
 * @date 2020/12/13.
 */
@SpringBootTest
public class Test {


    @org.junit.Test
    public void t1() {
        LocalDate now = LocalDate.now();
        String type = String.format("u%d", now.getDayOfMonth());
        System.out.println(now);
        System.out.println(type);
    }

    @org.junit.Test
    public void v2() {
        SignService signService = new SignServiceImpl();
        signService.getContinuousSignCount(19L,LocalDate.now());
    }
}
