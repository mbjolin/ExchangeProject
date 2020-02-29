package ca.amazing.exchangeproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ca.amazing.exchangeproject.model.DataTableJs;
import ca.amazing.exchangeproject.repository.UserRepository;

@Controller
public class DashboardController {

  @Autowired private UserRepository userRepo;

  @RequestMapping("/")
  public String initDashboard() throws Exception {
    return "dashboard";
  }

  @GetMapping("/datatable")
  @ResponseBody
  public DataTableJs dataTable() {

    DataTableJs dataTable = new DataTableJs();
    dataTable.setTable("myTable");
    dataTable.setPrimaryKey("user.id");

    dataTable.setData(userRepo.findAll());

    return dataTable;
  }
}
