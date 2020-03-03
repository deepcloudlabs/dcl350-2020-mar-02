package com.example.hr;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.hr.entity.Department;
import com.example.hr.entity.Employee;
import com.example.hr.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = HrMicroserviceApplication.class)
@AutoConfigureMockMvc
class HrMicroserviceApplicationTests {
	@Autowired
	private MockMvc mvc;
	@MockBean
	private EmployeeRepository empRepo;
	@Autowired
	private ObjectMapper mapper;

	@Test
	void createEmployee_isOk() throws Exception {
		Employee jack = new Employee();
		jack.setIdentity("10231002100");
		jack.setFullname("Jack Bauer");
		jack.setSalary(100_000);
		jack.setDepartment(Department.IT);
		jack.setIban("TR282238840087568690127692");
		jack.setPhoto(null);
		jack.setFulltime(true);
		jack.setBirthYear(1965);
		Mockito.when(empRepo.existsById("10231002100")).thenReturn(false);
		Mockito.when(empRepo.save(jack)).thenReturn(jack);
		mvc.perform(post("/employees").content(mapper.writeValueAsString(jack)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("identity", is("10231002100")));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	void getEmployees_twoEmps() throws Exception {
		Employee jack = new Employee();
		jack.setIdentity("10231002100");
		jack.setFullname("Jack Bauer");
		jack.setSalary(100_000);
		jack.setDepartment(Department.IT);
		jack.setIban("TR282238840087568690127692");
		jack.setPhoto(null);
		jack.setFulltime(true);
		jack.setBirthYear(1965);
		Employee kate = new Employee();
		kate.setIdentity("14171091712");
		kate.setFullname("Kate Austen");
		kate.setSalary(200_000);
		kate.setDepartment(Department.SALES);
		kate.setIban("TR881879375711450419301043");
		kate.setPhoto(null);
		kate.setFulltime(true);
		kate.setBirthYear(1985);
		Page page = Mockito.mock(Page.class);
		Mockito.when(empRepo.findAll(PageRequest.of(0, 10))).thenReturn(page);
		Mockito.when(page.getContent()).thenReturn(Arrays.asList(jack, kate));
		mvc.perform(get("/employees?page=0&size=10")).andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(2))).andExpect(jsonPath("$[0].identity", is("10231002100")))
				.andExpect(jsonPath("$[1].identity", is("14171091712")));
	}

}
