package com.cn.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ExceptionHandler implements HandlerExceptionResolver {

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception ex) {
        Map<String, Object> model = new HashMap<>();
        model.put("ex", ex);
        //根据不同错误转向不同页面
        if(ex instanceof BusinessException) {
            return new ModelAndView("business_error", model);
        }else if(ex instanceof ParameterException) {
            return new ModelAndView("parameter_error", model);
        } else {
            return new ModelAndView("error", model);
        }
    }
}