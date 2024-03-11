package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;


public class UpdateProductAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		String prodNo=(String)request.getParameter("prodNo");
		
		Product product=new Product();
		product.setProdNo(Integer.parseInt(request.getParameter("prodNo")));
		product.setProdDetail(request.getParameter("prodDetail"));
		product.setManufactureDay(request.getParameter("manufactureDay"));
		product.setPrice(Integer.parseInt(request.getParameter("price")));
		
		ProductService productService=new ProductServiceImpl();
		productService.updateProduct(product);
		
		
		return "redirect:/getProduct.do?prodNo="+prodNo;
	}
}