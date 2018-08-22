package com.economic.resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;

//@RestController
//@RequestMapping("/tokens")
public class TokenResource {
	
//	@Autowired
//	private EconomicApiProperty economicApiProperty;

	@DeleteMapping("/revoke")
	public void revoke(HttpServletRequest req, HttpServletResponse resp) {
//		Cookie cookie = new Cookie("refreshToken", null);
//		cookie.setHttpOnly(true);
//		cookie.setSecure(economicApiProperty.getSeguranca().isEnableHttps());
//		cookie.setPath(req.getContextPath() + "/oauth/token");
//		cookie.setMaxAge(0);
//		
//		resp.addCookie(cookie);
//		resp.setStatus(HttpStatus.NO_CONTENT.value());
	}
	
}