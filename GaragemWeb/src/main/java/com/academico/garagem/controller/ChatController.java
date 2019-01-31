package com.academico.garagem.controller;

import com.academico.garagem.model.ServiceLocator;
import com.academico.garagem.model.constant.Constants;
import com.academico.garagem.model.criteria.MessageCriteria;
import com.academico.garagem.model.criteria.RentGarageCriteria;
import com.academico.garagem.model.entity.Message;
import com.academico.garagem.model.entity.RentGarage;
import com.academico.garagem.model.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChatController {

    @RequestMapping(value = "/chat/request", method = RequestMethod.GET)
    public ModelAndView chaRequestGET(HttpSession session) throws Exception {
        ModelAndView mv = new ModelAndView("chat/index");

        User user = (User) session.getAttribute(Constants.SESSION_USER_LOGGED);

        Map<Integer, Object> criteria = new HashMap<>();
        criteria.put(RentGarageCriteria.VEHICLE_ID_USER_ID_EQ, user.getId());
        criteria.put(RentGarageCriteria.IS_AUTH_EQ, true);
        criteria.put(RentGarageCriteria.FINAL_DATE_TIME_EQ, null);
        List<RentGarage> requestRentGarageUserList = ServiceLocator.getInstance().getRentGarageService().findByCriteria(criteria, 0, 0);

        mv.addObject("requestRentGarageUserList", requestRentGarageUserList);

        return mv;
    }

    @RequestMapping(value = "/chat/received", method = RequestMethod.GET)
    public ModelAndView chatReceivedGET(HttpSession session) throws Exception {
        ModelAndView mv = new ModelAndView("chat/index");

        User user = (User) session.getAttribute(Constants.SESSION_USER_LOGGED);

        Map<Integer, Object> criteria = new HashMap<>();
        criteria.put(RentGarageCriteria.ADVERTISEMENT_ID_GARAGE_ID_USER_ID_EQ, user.getId());
        criteria.put(RentGarageCriteria.IS_AUTH_EQ, true);
        criteria.put(RentGarageCriteria.FINAL_DATE_TIME_EQ, null);
        List<RentGarage> receivedRentGarageUserList = ServiceLocator.getInstance().getRentGarageService().findByCriteria(criteria, 0, 0);

        mv.addObject("receivedRentGarageUserList", receivedRentGarageUserList);

        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/chat/messages", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String chatMessagesGET(HttpSession session, int rentGarageId) throws Exception {
        User user = (User) session.getAttribute(Constants.SESSION_USER_LOGGED);

        HashMap<Integer, Object> criteria = new HashMap<>();
        criteria.put(MessageCriteria.RENT_GARAGE_ID_EQ, rentGarageId);
        List<Message> list = ServiceLocator.getInstance().getMessageService().findByCriteria(criteria, 0, 0);

        return new ObjectMapper().writeValueAsString(list);
    }

    @ResponseBody
    @RequestMapping(value = "/chat/messages", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer chatMessagesPOST(HttpSession session, int userToId, int rentGarageId, String text) throws Exception {
        User user = (User) session.getAttribute(Constants.SESSION_USER_LOGGED);

        Message message = new Message();
        message.setUserToId(new User(userToId));
        message.setUserFromId(user);
        message.setMessage(text);
        message.setRentGarageId(new RentGarage(rentGarageId));
        message.setDateTime(new Date());
        ServiceLocator.getInstance().getMessageService().create(message);

        return message.getId();
    }

}
