package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {

    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        ModelAndView mav = new ModelAndView("response/hello")
                .addObject("data", "hello!"); // 모델 바인딩

        return mav;
    }

    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) {
        model.addAttribute("data", "hello!");
        return "response/hello"; // ViewResolver를 통해 resources/templates아래의 response/hello 뷰를 반환
    }

    // 너무 명시성이 떨어져서 권장하지 않음
    /**
     * @Controller를 사용하고, HttpServletResponse, OutputStream(Writer)같은 Http 메시지 바디를 처리하는 파라미터가 없으면 요청 URL을 참고해서 논리 뷰 경로로 활용
     */
    @RequestMapping("/response/hello")
    public void responseViewV3(Model model) {
        model.addAttribute("data", "hello!");
    }
}
