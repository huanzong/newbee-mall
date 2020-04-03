package ltd.newbee.mall.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Component
public class ScheduleUtil implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleUtil.class);

//    @Scheduled(cron = "*/1 * * * * ?")
//    public void execute() {
//        logger.info("print word.");
//        logger.info(String.valueOf(System.currentTimeMillis()));
//    }

    @Override
    public void run(ApplicationArguments var1) throws Exception {
        System.out.println("**************MyApplicationRunner1!*************************");
    }
}
