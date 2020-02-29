package ca.amazing.exchangeproject.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AgendaController {

  @GetMapping("/agenda")
  public String initAgenda(Map<String, Object> model) {

    return "agenda";
  }

}