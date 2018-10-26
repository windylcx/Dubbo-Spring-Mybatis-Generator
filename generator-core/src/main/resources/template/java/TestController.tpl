package template.java;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
public class TestController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(CGIConstants.TEST)
    @ResponseBody
    public BaseResponse test(HttpServletRequest httpServletRequest) {
        logger.debug("request test:{}"+httpServletRequest);
        return new BaseResponse();
        
    }

}
