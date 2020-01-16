package com.paschoalini.springboot.endpoint;

import static java.util.Arrays.asList;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.paschoalini.springboot.models.Student;

@RestController
@RequestMapping("/student")
public class StudentEndpoint {
	//@Autowired
	//private DateUtil dateUtil;
	
	@RequestMapping(method = RequestMethod.GET, path="/list")
	public List<Student> listAll() {
		// System.out.println(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
		return asList(new Student("Student #01"), new Student("Student #02"));
	}
}
