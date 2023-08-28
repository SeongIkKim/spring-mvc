package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username = {}, age = {}", username, age);
        response.getWriter().write("ok");
    }

    @ResponseBody // 상위가 Controller라 String 반환하면 기본적으로 View를 리졸빙하는데, @ResponseBody 어노테이션을 사용하면 @RestController처럼 http body message로 응답을 보낸다.
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
        @RequestParam("username") String memberName,
        @RequestParam("age") int memberAge
    ){
        log.info("username = {}, age = {}", memberName, memberAge);
        return "ok";
    }


    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age
    ){
        log.info("username = {}, age = {}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age){ // 파라미터 명과 request param명이 일치한다면 굳이 @RequestParam 어노테이션을 붙이지 않아도 된다. (String, int, Integer 등의 단순 타입일 경우)
        log.info("username = {}, age = {}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username, // 주의, 빈문자("")는 통과 - ex) ?username=
            @RequestParam(required = false) Integer age // required=false 일 경우 nullable한 값이 되므로 int같은 primitive 타입이 아니라 Integer같은 wrapper 타입으로 받아야한다(wrapper 타입은 객체라 nullable)
    ){
        log.info("username = {}, age = {}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true, defaultValue = "guest") String username, // required 설정 없이도 default만 설정해줄 수 있음
            @RequestParam(required = false, defaultValue = "-1") int age
    ){
        log.info("username = {}, age = {}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap){ // 파라미터 키 당 값이 하나임이 확실하다면 Map을 사용해도 된다. 그렇지 않으면 MultiValueMap을 사용하자.
        log.info("username = {}, age = {}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData){ // HelloData 객체 생성 후 요청 파라미터 이름으로 HelloData 프로퍼티를 찾아 setter 호출하여 바인딩
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());
        log.info("helloData = {}", helloData); // toString 호출
        return "ok";
    }


    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData){ // @ModelAttribute 어노테이션도 생략 가능
        // @RequestParam도 생략가능한데, 스프링에서 String, int, Integer같은 단순 타입은 @RequestParam으로 해석하고 나머지 타입은 모두 @ModelAttribute로 해석한다고 보면된다(argument resolver로 지정해둔 타입(ex-HttpServletRequest) 외 )
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());
        log.info("helloData = {}", helloData);
        return "ok";
    }
}
