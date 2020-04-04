package ltd.newbee.mall.util;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Component
public class ScheduleUtil implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments var1) throws Exception {

        System.out.println("**************MyApplicationRunner1!*************************");
    }
}
