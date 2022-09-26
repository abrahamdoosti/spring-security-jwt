package com.example.security.springsecurityjwt.config;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.access.intercept.aopalliance.MethodSecurityInterceptor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * custom interceptor, intercepts the method call when header is passed  with value check_auth
 * 
 * This is basically used to check if a client has authorization to a certain method or not.
 * 
 *The header need to be present for this validation to work, if the header check_auth is present 
 * Then the response can be either 200 with null payload when user is authorized, or 403 if user is not authorized 
 * 
 * @author abrah
 *
 */
public class MyMethodSecurityInterceptor extends MethodSecurityInterceptor {
	public static final String AUTH_CHECK_HEADER = "check_auth";

	public Object invoke(final MethodInvocation mi) throws Throwable {
		InterceptorStatusToken token = super.beforeInvocation(mi);

		Object result;
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
					.getRequest();
			if (request.getHeader(AUTH_CHECK_HEADER) != null) {
				result = null;
			} else {
				result = mi.proceed();
			}
		} finally {
			super.finallyInvocation(token);
		}
		return super.afterInvocation(token, result);
	}
}
