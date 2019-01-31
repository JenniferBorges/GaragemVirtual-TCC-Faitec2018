package com.academico.garagem.controller;

import com.academico.garagem.model.ServiceLocator;
import com.academico.garagem.model.constant.Constants;
import com.academico.garagem.model.criteria.UserCriteria;
import com.academico.garagem.model.entity.Notification;
import com.academico.garagem.model.entity.User;
import com.academico.garagem.model.entity.UserPhone;
import com.academico.garagem.model.enumeration.ENotification;
import com.academico.garagem.model.service.UserPhoneService;
import com.academico.garagem.model.service.UserService;
import com.academico.garagem.model.util.JWTUtil;
import com.academico.garagem.model.util.MailSender;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    public class UserPhoneList {

        private List<UserPhone> userPhoneList;

        public List<UserPhone> getUserPhoneList() {
            return userPhoneList;
        }

        public void setUserPhoneList(List<UserPhone> userPhoneList) {
            this.userPhoneList = userPhoneList;
        }

    }

    @RequestMapping(value = "/user/view-user/{id}", method = RequestMethod.GET)
    public ModelAndView userViewUserGET(@PathVariable Integer id, HttpSession session, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("user/view");

        try {
            UserService service = ServiceLocator.getInstance().getUserService();
            User user = service.findEntity(id);

            User _user = (User) session.getAttribute(Constants.SESSION_USER_LOGGED);

            if (user != null) {
                if (user.equals(_user)) {
                    mv = new ModelAndView("redirect:/user/edit/" + _user.getId());
                }
                mv.addObject("user", user);
            } else {
                response.sendError(HttpStatus.UNAUTHORIZED.value());
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return mv;
    }

    @RequestMapping(value = "/user/edit/{id}", method = RequestMethod.GET)
    public ModelAndView userEditGET(@PathVariable Integer id, HttpSession session, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("user/index");

        try {
            UserService service = ServiceLocator.getInstance().getUserService();
            User _user = service.findEntity(id);

            User user = (User) session.getAttribute(Constants.SESSION_USER_LOGGED);

            if (user != null && user.equals(_user)) {
                mv.addObject("user", _user);
            } else {
                response.sendError(HttpStatus.UNAUTHORIZED.value());
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return mv;
    }

    @RequestMapping(value = "/user/edit/{id}", method = RequestMethod.POST)
    public ModelAndView userEditPOST(@PathVariable Integer id, RedirectAttributes redir, HttpSession session,
            String name, String lastName, String email, String oldPassword, String password, String confirmPassword, String gender, String identity,
            @ModelAttribute("userPhoneList") UserPhoneList userPhoneList) {
        ModelAndView mv = new ModelAndView("redirect:/user/edit/" + id);
        Notification notification = new Notification();

        try {
            List<UserPhone> phoneList = userPhoneList.getUserPhoneList();

            Map<String, Object> fields = new HashMap<>();
            fields.put("id", id);
            fields.put("name", name);
            fields.put("lastName", lastName);
            fields.put("email", email);
            fields.put("password", password);
            fields.put("oldPassword", oldPassword);
            fields.put("confirmPassword", confirmPassword);
            fields.put("phoneList", phoneList);
            fields.put("gender", gender);
            fields.put("identity", identity);

            UserService service = ServiceLocator.getInstance().getUserService();
            Map<String, String> errors = service.validate(fields);

            User _user = service.findEntity(id);

            User user = (User) session.getAttribute(Constants.SESSION_USER_LOGGED);

            if (errors.isEmpty() && _user.equals(user) && identity != null && !identity.isEmpty()) {
                _user.setIdentity(identity);
                service.edit(_user);
            } else if (errors.isEmpty() && _user.equals(user)) {
                _user.setName(name);
                _user.setEmail(email);
                _user.setLastName(lastName);
                _user.setGender(gender);
                if (password != null) {
                    _user.setPassword(UserService.passwordEncoder.encode(password));
                }

                service.edit(_user);

                UserPhoneService phoneService = ServiceLocator.getInstance().getUserPhoneService();
                phoneService.destroyByUserId(_user.getId());
                if (phoneList != null && phoneList.size() > 0) {
                    for (UserPhone phone : phoneList) {
                        if (phone.getNumber() != null && !phone.getNumber().isEmpty()) {
                            phone.setUserId(user);
                            phoneService.create(phone);
                        }
                    }
                }

                session.setAttribute(Constants.SESSION_USER_LOGGED, _user);

                notification.setType(ENotification.SUCCESS);
                notification.setText("Usuário editado com successo!");
                notification.setTime(5000);
                redir.addFlashAttribute("notification", notification);
            } else {
                notification.setType(ENotification.ERROR);
                notification.setText("Erro ao editar usuário");
                notification.setTime(5000);
                redir.addFlashAttribute("notification", notification);
                redir.addFlashAttribute("errors", errors);
            }
        } catch (Exception ex) {
            notification.setType(ENotification.ERROR);
            notification.setText("Ops! Todo mundo erra e nós também erramos, tente novamente mais tarde ou contate nosso suporte");
            notification.setTime(5000);
            redir.addFlashAttribute("notification", notification);
            ex.printStackTrace();
        }

        return mv;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginGET(HttpSession session, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("user/login");

        return mv;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView loginPOST(RedirectAttributes redir, HttpServletResponse response, HttpServletRequest request, HttpSession session, String email, String password, boolean remember) {
        ModelAndView mv;
        Notification notification = new Notification();

        try {
            UserService service = ServiceLocator.getInstance().getUserService();

            User user = service.login(email, password);
            if (user != null) {
                session.setAttribute(Constants.SESSION_USER_LOGGED, user);

                if (remember) {
                    Cookie c = new Cookie(Constants.COOKIE_NAME, JWTUtil.create(email + ";" + password));
                    c.setMaxAge(30 * 24 * 60 * 60);
                    response.addCookie(c);
                } else {
                    deleteRememberMeCookie(response);
                }

                if (user.getIsAdmin()) {
                    mv = new ModelAndView("redirect:/admin");
                } else {
                    mv = new ModelAndView("redirect:/");
                }

                notification.setType(ENotification.SUCCESS);
                notification.setText("Login realizado com sucesso");
                notification.setTime(5000);
                redir.addFlashAttribute("notification", notification);
            } else {
                notification.setType(ENotification.ERROR);
                notification.setText("Email ou senha incorretos");
                notification.setTime(5000);
                redir.addFlashAttribute("notification", notification);
                mv = new ModelAndView("redirect:/login");
            }
        } catch (Exception ex) {
            notification.setType(ENotification.ERROR);
            //notification.setText("Ops! Todo mundo erra e nós também erramos, tente novamente mais tarde ou contate nosso suporte");
            notification.setText("Email ou senha incorretos");
            notification.setTime(5000);
            redir.addFlashAttribute("notification", notification);
            mv = new ModelAndView("redirect:/login");
            ex.printStackTrace();
        }

        return mv;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logoutGET(RedirectAttributes redir, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        ModelAndView mv = new ModelAndView("redirect:/");
        Map<String, Object> notification = new HashMap<>();

        session.setAttribute(Constants.SESSION_USER_LOGGED, null);
        session.invalidate();
        deleteRememberMeCookie(response);

        notification.put("type", "success");
        notification.put("text", "Logout realizado com sucesso");
        notification.put("time", 2000);
        redir.addFlashAttribute("notification", notification);

        return mv;
    }

    @RequestMapping(value = "/request-password", method = RequestMethod.GET)
    public ModelAndView requestPasswordGET() {
        ModelAndView mv = new ModelAndView("user/password/request");

        return mv;
    }

    @RequestMapping(value = "/request-password", method = RequestMethod.POST)
    public ModelAndView requestPasswordPOST(RedirectAttributes redir, HttpServletRequest request, String email) {
        ModelAndView mv = new ModelAndView("redirect:/request-password");
        Notification notification = new Notification();

        try {
            UserService service = ServiceLocator.getInstance().getUserService();

            User user = service.findByEmail(email);
            user.setResetToken(generateToken());
            user.setResetComplete(false);
            service.edit(user);

            sendPassword(user, request);

            notification.setType(ENotification.SUCCESS);
            notification.setText("Link enviado! Por favor, cheque seu email para alterar sua senha.");
            notification.setTime(5000);
            redir.addFlashAttribute("notification", notification);
        } catch (Exception ex) {
            notification.setType(ENotification.ERROR);
            //notification.setText("Ops! Todo mundo erra e nós também erramos, tente novamente mais tarde ou contate nosso suporte");
            notification.setText("Nenhum resultado para este e-mail\n"
                    + "Tente novamente com outras informações");
            notification.setTime(5000);
            redir.addFlashAttribute("notification", notification);
            ex.printStackTrace();
        }

        return mv;
    }

    @RequestMapping(value = "/password-reset", method = RequestMethod.GET)
    public ModelAndView passwordResetGET(RedirectAttributes redir, @RequestParam("x") String email, @RequestParam("y") String token) {
        ModelAndView mv = new ModelAndView("user/password/reset");

        return mv;
    }

    @RequestMapping(value = "/password-reset", method = RequestMethod.POST)
    public ModelAndView passwordResetPOST(RedirectAttributes redir, HttpServletResponse response, @RequestParam("x") String email, @RequestParam("y") String token, String password, String confirmPassword) {
        ModelAndView mv = new ModelAndView("redirect:/");

        Map<Integer, Object> criteria = new HashMap<>();
        criteria.put(UserCriteria.EMAIL_EQ, email);
        criteria.put(UserCriteria.RESET_TOKEN_EQ, token);
        criteria.put(UserCriteria.RESET_COMPLETE_EQ, false);

        try {

            UserService service = ServiceLocator.getInstance().getUserService();

            List<User> user = service.findByCriteria(criteria, 1, 0);
            if (user.size() > 0) {
                Map<String, Object> fields = new HashMap<>();
                fields.put("password", password);
                fields.put("confirmPassword", confirmPassword);

                Map<String, String> errors = service.validate(fields);

                if (errors.isEmpty()) {
                    User u = user.get(0);
                    u.setResetComplete(true);
                    u.setResetToken(null);
                    u.setPassword(UserService.passwordEncoder.encode(password));
                    service.edit(u);
                }
            } else {
                response.sendError(HttpStatus.UNAUTHORIZED.value());
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return mv;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView registerGET() {
        ModelAndView mv = new ModelAndView("user/register");

        return mv;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView registerPOST(RedirectAttributes redir, HttpServletRequest request, String name, String lastName, String email, String password, String confirmPassword) {
        ModelAndView mv = new ModelAndView("redirect:/");
        Notification notification = new Notification();

        try {
            Map<String, Object> fields = new HashMap<>();
            fields.put("name", name);
            fields.put("lastName", lastName);
            fields.put("email", email);
            fields.put("password", password);
            fields.put("confirmPassword", confirmPassword);

            UserService service = ServiceLocator.getInstance().getUserService();
            Map<String, String> errors = service.validate(fields);

            if (errors.isEmpty()) {
                User user = new User();

                user.setName(name);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setPassword(UserService.passwordEncoder.encode(password));
                user.setActive(false);
                user.setBanned(false);
                user.setAuthToken(generateToken());
                user.setResetToken(null);
                user.setResetComplete(false);
                user.setJoiningDate(new Date());

                //endereço
                service.create(user);

                sendActivation(user, request);

                notification.setType(ENotification.SUCCESS);
                notification.setText("Registrado com successo! Por favor, verifique seu email para ativar sua conta.");
                notification.setTime(5000);
                redir.addFlashAttribute("notification", notification);
            } else {
                mv = new ModelAndView("redirect:/register");
                notification.setType(ENotification.ERROR);
                notification.setText("Erro ao registrar");
                notification.setTime(5000);
                redir.addFlashAttribute("notification", notification);
                redir.addFlashAttribute("errors", errors);
                redir.addFlashAttribute("user", fields);
            }
        } catch (Exception ex) {
            notification.setType(ENotification.ERROR);
            notification.setText("Ops! Todo mundo erra e nós também erramos, tente novamente mais tarde ou contate nosso suporte");
            notification.setTime(5000);
            redir.addFlashAttribute("notification", notification);
            ex.printStackTrace();
        }

        return mv;
    }

    @RequestMapping(value = "/activate", method = RequestMethod.GET)
    public ModelAndView activateGET(RedirectAttributes redir, HttpServletResponse response, @RequestParam("x") String email, @RequestParam("y") String token) {
        ModelAndView mv = new ModelAndView("redirect:/");
        Notification notification = new Notification();

        Map<Integer, Object> criteria = new HashMap<>();
        criteria.put(UserCriteria.EMAIL_EQ, email);
        criteria.put(UserCriteria.AUTH_TOKEN_EQ, token);

        try {
            UserService service = ServiceLocator.getInstance().getUserService();

            List<User> user = service.findByCriteria(criteria, 1, 0);
            if (user.size() > 0) {
                User u = user.get(0);
                u.setActive(true);
                u.setAuthToken(null);
                service.edit(u);

                notification.setType(ENotification.SUCCESS);
                notification.setText("Conta ativada com sucesso!");
                notification.setTime(5000);
                redir.addFlashAttribute("notification", notification);
            } else {
                notification.setType(ENotification.ERROR);
                notification.setText("Erro ao ativar conta");
                notification.setTime(5000);
                redir.addFlashAttribute("notification", notification);
            }
        } catch (Exception ex) {
            notification.setType(ENotification.ERROR);
            notification.setText("Erro ao ativar a conta!");
            notification.setTime(5000);
            redir.addFlashAttribute("notification", notification);
            ex.printStackTrace();
        }

        return mv;
    }

    public String generateToken() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 10);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ssss");

        return JWTUtil.create(sdf.format(cal.getTime()));
    }

    public void sendActivation(User entity, HttpServletRequest request) throws Exception {
        MailSender mail = new MailSender();
        String url = getBaseUrl(request);
        mail.sendMail(entity.getEmail(), "Confirmação de registro", "<p>Seu cadastro foi feito com sucesso.</p><p> Para ativar sua conta, por favor clique neste link: <a href ='" + url + "/activate?x=" + entity.getEmail() + "&y=" + entity.getAuthToken() + "'>" + url + "/activate?x=" + entity.getEmail() + "&y=" + entity.getAuthToken() + "</a></p><p> Nossa equipe agradece seu interesse em se juntar à nossa plataforma.</p>");
    }

    public void sendPassword(User entity, HttpServletRequest request) throws Exception {
        MailSender mail = new MailSender();
        String url = getBaseUrl(request);
        mail.sendMail(entity.getEmail(), "Alteração de senha", "<p>Seu pedido de alteração de senha foi registrado.</p><p> Para alterar sua senha, por favor clique neste link: <a href ='" + url + "/password-reset?x=" + entity.getEmail() + "&y=" + entity.getResetToken() + "'>" + url + "/activate?x=" + entity.getEmail() + "&y=" + entity.getResetToken() + "</a></p>");
    }

    public static String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme() + "://";
        String serverName = request.getServerName();
        String serverPort = (request.getServerPort() == 80) ? "" : ":" + request.getServerPort();
        String contextPath = request.getContextPath();
        return scheme + serverName + serverPort + contextPath;
    }

    private static void deleteRememberMeCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(Constants.COOKIE_NAME, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

}
