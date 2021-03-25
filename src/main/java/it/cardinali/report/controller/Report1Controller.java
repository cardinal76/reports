package it.cardinali.report.controller;

/************************************************************************
 * Created: 24/03/2021                                                  *
 * Author:  mcardinali                                                  *
 ************************************************************************/

import it.cardinali.report.service.Report;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/reports")
public class Report1Controller {
  final Report report;

  public Report1Controller(Report report) {
    this.report = report;
  }

  @GetMapping("/report1")
  public void getReport1() {
    report.start();
  }

  @GetMapping("/anagrafica")
  public void getAnagrafica() {
    report.anagrafica();
  }

  @GetMapping("/")
  public String getTest() {
    return "Hello World!";
  }

}
