package it.cardinali.report.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/************************************************************************
 * Created: 25/03/2021                                                  *
 * Author:  mcardinali                                                  *
 ************************************************************************/
@SpringBootTest
@AutoConfigureMockMvc
public class WebTest {
  @Autowired
  private MockMvc mvc;

  @MockBean
  private Report service;

  @Test
  public void getReport1()
    throws Exception {

/*    Employee alex = new Employee("alex");

    List<Employee> allEmployees = Arrays.asList(alex);

    given(service.getAllEmployees()).willReturn(allEmployees);*/

    mvc.perform(get("/reports/report1")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());

  }

  @Test
  public void getAnagrafica() throws Exception {
    mvc.perform(get("/reports/anagrafica")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());

  }

  @Test
  public void testMessage() throws Exception {
    this.mvc.perform(get("/reports/"))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().string(containsString("Hello World!")));
  }


}
