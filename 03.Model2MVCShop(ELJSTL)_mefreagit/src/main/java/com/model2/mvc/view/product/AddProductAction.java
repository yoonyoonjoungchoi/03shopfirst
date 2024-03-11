package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;


public class AddProductAction extends Action {

   @Override
   public String execute(   HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
      Product product=new Product();
      product.setImageFile(request.getParameter("imageFile"));
      product.setManufactureDay(request.getParameter("manufactureDay"));
      product.setPrice(Integer.parseInt(request.getParameter("price")));
      product.setProdDetail(request.getParameter("prodDetail"));
      product.setProdName(request.getParameter("prodName"));
      
      System.out.println("AddProcutAction ::"+product);
      
      ProductService productService=new ProductServiceImpl();
      productService.addProduct(product);
      
      
      return "forward:/product/addProduct.jsp";
   }
}

