package ca.amazing.exchangeproject.controller;

import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ca.amazing.exchangeproject.domain.User;
import ca.amazing.exchangeproject.repository.UserRepository;
import ca.amazing.exchangeproject.type.ActionType;

@Controller
@RequestMapping("/user")
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  private static final String VIEW = "dialog/user";

  private UserRepository repo;

  private ObjectMapper mapper = new ObjectMapper();

  @Autowired
  public UserController(UserRepository userRepo) {
      this.repo = userRepo;
  }

  @RequestMapping(method = {RequestMethod.GET})
  public ModelAndView genericGetForm(
      @Valid User object,
      BindingResult result,
      @RequestParam("id") Optional<Long> objectId,
      @RequestParam("action") Optional<ActionType> action,
      @RequestParam("modal") Optional<Boolean> isModal) {

    logger.debug("Generic Form for User : {}, {}, {}", objectId, action, isModal);

    ActionType currentAction = action.orElse(ActionType.read);

    ModelAndView mav = new ModelAndView(VIEW);

    switch (currentAction) {
      case create:
        mav.addObject("isEdit", Boolean.TRUE);
        break;
      case read:
        mav.addObject("user", repo.findById(objectId.orElse(0L)).orElse(new User()));
        break;
      case update:
        mav.addObject("isEdit", Boolean.TRUE);
        break;
      case delete:
        mav.addObject("user", repo.findById(objectId.orElse(0L)).orElse(new User()));
        break;
    }
    String json = "";
    try {
      json = mapper.writeValueAsString(object);

    } catch (JsonProcessingException ex) {
      logger.error("Object to json exception : {}", ex);
    }
    mav.addObject("json", json);
    mav.addObject("isModal", isModal.orElse(Boolean.FALSE));
    return mav;
  }

  @RequestMapping(method = {RequestMethod.POST})
  public ModelAndView genericPostForm(
      @Valid User object,
      BindingResult result,
      @RequestParam("id") Optional<Long> objectId,
      @RequestParam("action") Optional<ActionType> action,
      @RequestParam("modal") Optional<Boolean> isModal) {

    logger.debug("Generic Form for User : {}, {}, {}", objectId, action, isModal);

    ActionType currentAction = action.orElse(ActionType.read);

    ModelAndView mav = new ModelAndView(VIEW);

    switch (currentAction) {
      case create:
        mav.addObject("user", repo.save(object));
        mav.addObject("isEdit", Boolean.TRUE);
        break;
      case read:
        mav.addObject("user", repo.findById(objectId.orElse(0L)));
        break;
      case update:
        mav.addObject("user", repo.save(object));
        mav.addObject("isEdit", Boolean.TRUE);
        break;
      case delete:
        repo.deleteById(objectId.orElse(0L));
        break;
    }

    String json = "";
    try {
      json = mapper.writeValueAsString(object);

    } catch (JsonProcessingException ex) {
      logger.error("Object to json exception : {}", ex);
    }
    mav.addObject("json", json);
    mav.addObject("isModal", isModal.orElse(Boolean.FALSE));
    return mav;
  }
}
