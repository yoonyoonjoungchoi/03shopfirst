package com.model2.mvc.view.product;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class GetProductAction extends Action{

   @Override
   public String execute(   HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
      int prodNo=Integer.parseInt(request.getParameter("prodNo"));
      
      ProductService productService=new ProductServiceImpl();
      Product product=productService.getProduct(prodNo);
 
      request.setAttribute("product", product);
      
      
      Cookie[] cookies = request.getCookies();
      String history = "";
      if(cookies  != null )
      {	  
    		for(Cookie cookie : cookies)
    		{
    			if(cookie.getName().equals("history"))
    			{
    				history = cookie.getValue();
    				history += "and" + prodNo;
    				cookie = new Cookie("history", history);
    				response.addCookie(cookie);
    				break;
    				
    			}else
    			{
    				cookie = new Cookie("history", Integer.toString(prodNo));
    				response.addCookie(cookie);
    			}
    		}
    	}else
    	{
    		Cookie cookie = new Cookie("history", Integer.toString(prodNo));
    		response.addCookie(cookie);
    	}
    		
    	return "forward:/product/getProduct.jsp";
	}
}
		
    			
            
      
//  	request.setCharacterEncoding("euc-kr");
//  	response.setCharacterEncoding("euc-kr");
//  	String history = null;
//  	Cookie[] cookies = request.getCookies();
//  	if (cookies!=null && cookies.length > 0) {
//  		for (int i = 0; i < cookies.length; i++) {
//  			Cookie cookie = cookies[i];
//  			if (cookie.getName().equals("history")) {
//  				history = cookie.getValue();
//  				history += "and"+prodNo;
//  			
//  		}
//  		if (history != null) {
//  			String[] h = history.split("and");
//  			for (int i = 0; i < h.length; i++) {
////  				if (!h[i].equals("null")) {
////  
//
//
//				}
//			}
//		}
//	}
//

      
      
      
      //
      // Cookie[] cookies = request.getCookie();
      //for 
      //"history" �ִ��� check
      // ������ -> cook[i].name?  // ���� ��Ű�� + "and" + ���ο� prodNo 
      //+"and" + prodNo
      //response
      //
      //"history" ������
      //��Ű�� ���� �������߰���?
      //���� getpAction���� �� ��ǰ��ȣprodNo
      //Cookie cookie ~~ // �̸��� "history" ��Ű
      //response
      //
      // 10000and/10001/and100002
   