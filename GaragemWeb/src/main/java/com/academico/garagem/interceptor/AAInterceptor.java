package com.academico.garagem.interceptor;

import com.academico.garagem.model.ServiceLocator;
import com.academico.garagem.model.constant.Constants;
import com.academico.garagem.model.entity.User;
import com.academico.garagem.model.util.JWTUtil;
import java.util.Arrays;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AAInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String targetURL = request.getRequestURI().replace(request.getContextPath(), "");

        if (isStaticResource(targetURL)) {
            return true;
        }

        // Verifica se possui usuário válido na sessão.
        User user = getAuthenticatedUser(request);
        if (user != null) {
            request.getSession().setAttribute(Constants.SESSION_USER_LOGGED, user);

            String url = (String) request.getSession().getAttribute(Constants.SESSION_PREVIOUS_URL);
            if (url != null) {
                request.getSession().removeAttribute(Constants.SESSION_PREVIOUS_URL);
                response.sendRedirect(request.getContextPath() + url);
            }

            return true;

            // Se o usuário for do tipo MANAGER, liberado acesso total.
            // Ou libera URL's para todos os usuários
            /*if (user.getIsAdmin()) {
                request.getSession().removeAttribute("error");
                return true;
            } else if (targetURLAux.length > 2) {
                if (targetURLAux[2].contentEquals("perfil")
                        || targetURLAux[2].contentEquals("notificacao")) {
                    request.getSession().removeAttribute("error");
                    return true;
                }
            }

            // Redirecionamento caso não possua permissão.
            if (currentURL != null) {
                if (currentURL.isEmpty() || currentURL.equals(targetURL)) {
                    request.getSession().setAttribute("error", 501);
                    response.sendRedirect("/" + targetURLAux[1] + "/inicio");
                } else {
                    request.getSession().setAttribute("error", 501);
                    response.sendRedirect("/" + targetURLAux[1] + "/error");
                }
            }*/
        } else {
            String[] targetURLAux = targetURL.split("/");
            // URL's liberadas para todos os usuários
            if (targetURLAux.length <= 1) {
                return true;
            } else if (targetURLAux.length > 1) {
                /*
             *   targetURLAux[0] contexto
             *   targetURLAux[1+] URL do Controller
                 */
                switch (targetURLAux[1]) {
                    case "search":
                    case "register":
                    case "activate":
                    case "password-reset":
                    case "request-password":
                    case "logout":
                    case "login":
                    case "error":
                        return true;
                    default:
                        //response.sendError(HttpStatus.UNAUTHORIZED.value());
                        request.getSession().setAttribute(Constants.SESSION_PREVIOUS_URL, targetURL);
                        response.sendRedirect(request.getContextPath() + "/login");
                        break;
                }
            }
        }

        return false;
    }

    private boolean isStaticResource(String url) {
        return url.startsWith("/resources");
    }

    public static User getAuthenticatedUser(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(Constants.SESSION_USER_LOGGED);
        if (user == null) {
            Optional<Cookie> rememberMeCookie = getRememberMeCookie(request);
            if (rememberMeCookie.isPresent()) {
                try {
                    String token = JWTUtil.decode(rememberMeCookie.get().getValue());
                    String[] tokenSplitted = token.split(";");
                    String email = tokenSplitted[0];
                    String password = tokenSplitted[1];
                    user = ServiceLocator.getInstance().getUserService().login(email, password);
                } catch (Exception e) {
                    e.printStackTrace();
                    user = null;
                }
            }
        } else {
            user = ServiceLocator.getInstance().getUserService().findByEmail(user.getEmail());
        }
        return user;
    }

    private static Optional<Cookie> getRememberMeCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .filter(c -> c.getName().equals(Constants.COOKIE_NAME))
                    .findFirst();
        }
        return Optional.empty();
    }

}
