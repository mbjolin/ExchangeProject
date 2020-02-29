package ca.amazing.exchangeproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ca.amazing.exchangeproject.domain.Activity;
import ca.amazing.exchangeproject.repository.ActivityRepository;
import ca.amazing.exchangeproject.type.ActionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/activity")
public class ActivityController {

    private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);

    private static final String VIEW = "dialog/activity";

    private ActivityRepository repo;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public ActivityController(ActivityRepository repo) {
        this.repo = repo;
    }

    @RequestMapping(method = {RequestMethod.GET})
    public ModelAndView genericGetForm(
            @Valid Activity object,
            BindingResult result,
            @RequestParam("id") Optional<Long> objectId,
            @RequestParam("action") Optional<ActionType> action,
            @RequestParam("modal") Optional<Boolean> isModal) {

      logger.debug("Generic Form for Activity : {}, {}, {}", objectId, action, isModal);

        ActionType currentAction = action.orElse(ActionType.read);

        ModelAndView mav = new ModelAndView(VIEW);

        switch (currentAction) {
            case create:
                mav.addObject("isEdit", Boolean.TRUE);
                break;
            case read:
                mav.addObject("activity", repo.findById(objectId.orElse(0L)));
                break;
            case update:
                mav.addObject("isEdit", Boolean.TRUE);
                break;
            case delete:
                mav.addObject("activity", repo.findById(objectId.orElse(0L)));
                break;
        }
        String json = "";
        try {
            json = mapper.writeValueAsString(object);

        } catch (JsonProcessingException ex) {
          logger.error("Object to json exception : {}", ex.getStackTrace()[0]);
        }
        mav.addObject("json", json);
        mav.addObject("isModal", isModal.orElse(Boolean.FALSE));
        return mav;
    }

    @RequestMapping(method = {RequestMethod.POST})
    public ModelAndView genericPostForm(
            @Valid Activity object,
            BindingResult result,
            @RequestParam("id") Optional<Long> objectId,
            @RequestParam("action") Optional<ActionType> action,
            @RequestParam("modal") Optional<Boolean> isModal) {

      logger.debug("Generic Form for Activity : {}, {}, {}", objectId, action, isModal);

        ActionType currentAction = action.orElse(ActionType.read);

        ModelAndView mav = new ModelAndView(VIEW);

        switch (currentAction) {
            case create:
                mav.addObject("activity", repo.save(object));
                mav.addObject("isEdit", Boolean.TRUE);
                break;
            case read:
                mav.addObject("activity", repo.findById(objectId.orElse(0L)));
                break;
            case update:
                mav.addObject("activity", repo.save(object));
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
          logger.error("Object to json exception : {}", ex.getStackTrace()[0]);
        }
        mav.addObject("json", json);
        mav.addObject("isModal", isModal.orElse(Boolean.FALSE));
        return mav;
    }

}