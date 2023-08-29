package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);
        response.getWriter().write("ok");
    }

    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException { // HttpServlet req, resp 대신 inputStream과 writer를 바로 받을수도 있음
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);
        responseWriter.write("ok");
    }

    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {
        // HttpEntity는 header, body를 편리하게 조회. @RequestParam, @ModelAttribute와는 관계없음! 해당 어노테이션들은 Body가 아니라 쿼리파라미터를 사용하는 경우
        String messageBody = httpEntity.getBody(); // Stream으로부터 string을 가져오는 과정을 HttpEntity 내부의 HttpMessageConverter로 수행
        log.info("messageBody={}", messageBody);
        return new HttpEntity<>("ok"); // 응답에도 사용가능
    }

    @ResponseBody // String 응답값을 Http message body에 넣어 response로 돌려준다. View를 사용하지 않는다.
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) throws IOException {
        // Header 정보가 필요하다면 'HttpEntity'를 사용하거나 @RequestHeader를 사용하면 된다.
        log.info("messageBody={}", messageBody);
        return "ok";
    }
}
