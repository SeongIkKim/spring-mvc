package hello.servlet.web.frontcontroller.v4;

import java.util.Map;

public interface ControllerV4 {

    /**
     * V3을 개선하여 불필요한 ModelView 객체 생성로직을 제거한다.
     * @param paramMap request 대신 받은 임의 구현 모델
     * @param model 뷰로 넘길 모델 객체(참조를 넘겨서 process 과정에서 모델에 attribute를 set하고 이를 view로 넘긴다.
     * @return viewName만 반환
     */
    String process(Map<String, String> paramMap, Map<String, Object> model);
}
