package hello.springmvc.basic.requestmapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class MappingController {

    @RequestMapping(value = "/hello-basic", method= RequestMethod.GET)
    public String helloBasic(){
        log.info("helloBasic");
        return "OK";
    }

    @GetMapping(value="/mapping-get-v2")
    public String mappingGetV2(){
        log.info("mapping-get-v2");
        return "ok";
    }

    /**
     * PathVariable 사용 - @PathVariable("userId")
     * 어노테이션의 파라미터는 변수명이 같으면 생략 가능 - @PathVariable
     */
    @GetMapping(value = "/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String userId){
        log.info("mappingPath userId={}", userId);
        return "ok";
    }

    /**
     * PathVariable 다중 사용
     */
    @GetMapping(value = "/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable String orderId){
        log.info("mappingPath userId={}, orderId={}", userId, orderId);
        return "ok";
    }

    /**
     * 파라미터 조건 매핑 - 지정된 쿼리파라미터를 넣어주어야 매핑됨
     * 잘 사용하지는 않음
     * params="mode"
     * params="!mode"
     * params="mode=debug"
     * params="mode!=debug"
     * params= {"mode=debug", "data=good"} // 여러 파라미터 중 하나로 접근시 매핑
     */
    @GetMapping(value = "/mapping-param", params="mode=debug")
    public String mappingParam(){
        log.info("mappingParam");
        return "ok";
    }

    /**
     * 특정 헤더로 추가 매핑
     * headers="mode"
     * headers="!mode"
     * headers="mode=debug"
     * headers="mode!=debug"
     */
    @GetMapping(value = "/mapping-header", headers="mode=debug")
    public String mappingHeader(){
        log.info("mappingHeader");
        return "ok";
    }

    /**
     * 미디어 타입 조건 매핑 - http 요청 Content-type, consume(request를 해당 타입으로 해석,소비(consume)하겠다는 의미)
     * consumes="application/json"
     * consumes="!application/json"
     * consumes="application/*"
     * consumes="*\/*"
     * MediaType.APPLICATION_JSON_VALUE
     */
    @PostMapping(value = "/mapping-consume", consumes="application/json")
    public String mappingConsumes(){
        log.info("mappingConsumes");
        return "ok";
    }

    /**
     * Accept 헤더 기반 Media Type, produces(서버가 클라이언트에게 반환하는 타입)
     * request를 보낼때 produces 형식을 허용하는 Accept header를 보내줘야 값을 받을 수 있다. 아니면 406(Not Acceptable)을 반환한다.
     * produces="text/html"
     * produces="!text/html"
     * produces="text/*"
     * produces="*\/*"
     */
    @PostMapping(value = "/mapping-produce", produces="text/html")
    public String mappingProduces(){
        log.info("mappingProduces");
        return "ok";
    }

}
