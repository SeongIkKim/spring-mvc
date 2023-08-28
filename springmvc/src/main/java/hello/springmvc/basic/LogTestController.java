package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController // 그냥 @Controller는 view 반환하는데, @RestController는 http body로 문자열만 편리하게 반환할 수 있음
public class LogTestController {

    // @Slf4j 어노테이션이 아래 주석처리된 코드를 대신해줌
//    private final Logger log = LoggerFactory.getLogger(getClass()); // 현재 내 클래스명을 딴 로거 생성


    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        System.out.println("name = " + name);

        log.trace("trace log={}", name);
        log.debug("debug log={}", name);
        log.info(" info log={}", name);
        log.warn(" warn log={}", name);
        log.error("error log={}", name);

        return "ok";
    }
}
