package ca.amazing.exchangeproject.config;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/*
 * The scenario is somewhat unusual since typically entity attributes are used for rendering with views (e.g. HTML template).
 * For @ResponseBody only the return value is used to render the response. That said the use of @ResponseBody doesn't preclude
 * the use of @SessionAttributes. The errors has to do with trying to create the session for the first time too late. Normally
 * the use of something like Spring Security with a Filter to store authentication in the session ensures there is always an
 * HTTP session. In the absence of that you could add your own Filter or HandlerInterceptor that simply calls request.getSession(true).
 * That's enough to ensure a session is present.
 *
 * https://jira.spring.io/browse/SPR-12877
 * http://www.baeldung.com/spring-boot-add-filter
 * */
@Component
@Order(1)
public class SessionFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest req = (HttpServletRequest) request;
    req.getSession(true);
    chain.doFilter(request, response);
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // TODO Auto-generated method stub

  }

  @Override
  public void destroy() {
    // TODO Auto-generated method stub

  }
}