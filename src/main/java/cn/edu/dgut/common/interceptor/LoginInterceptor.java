package cn.edu.dgut.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.dgut.common.util.Const;
import cn.edu.dgut.pojo.TAdmin;
import cn.edu.dgut.pojo.TDoctor;

public class LoginInterceptor implements HandlerInterceptor {
	
	/** 
     * Handler执行完成之后调用这个方法 
     */  
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception arg3)
			throws Exception {

	}

	/** 
     * Handler执行之后，ModelAndView返回之前调用这个方法 
     */  
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView arg3)
			throws Exception {

	}

	 /** 
     * Handler执行之前调用这个方法 
     */ 
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		//获取请求的URL  
        String url = request.getRequestURI();  
        //URL:login.jsp是公开的;这个demo是除了login.jsp是可以公开访问的，其它的URL都进行拦截控制  
        if(url.indexOf("login")>=0){  
            return true;  
        }  
        //获取Session  
        
        if(request.getSession().getAttribute("doctorInfo") != null && request.getSession().getAttribute("doctorInfo") !=""){  
            return true;  
        }  
        
        
        if(request.getSession().getAttribute("adminInfo") != null && request.getSession().getAttribute("adminInfo") !=""){  
            return true;  
        }  
        
        if(request.getSession().getAttribute(Const.CURRENT_USER) != null && request.getSession().getAttribute(Const.CURRENT_USER) !=""){  
            return true;  
        }  
        //不符合条件的，跳转到登录界面  
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);  
          
        return false;  
	}

}
