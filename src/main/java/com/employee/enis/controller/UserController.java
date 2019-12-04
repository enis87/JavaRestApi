package com.employee.enis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.employee.enis.dao.UserDaoImpl;
import com.employee.enis.entity.User;


@Controller
@RequestMapping("/employees")
public class UserController {
	
	LeavesController controller = new LeavesController();	
	
	@Autowired
	private UserDaoImpl userdao;
	
	public UserController(UserDaoImpl theEmployeeService) {
		userdao = theEmployeeService;
	}
	
	// add mapping for "/list"

	@GetMapping("/list")
	public String listEmployees(Model theModel) {
		
		// get employees from db
		List<User> theEmployees = userdao.findAll();

		for(int i=0;i<theEmployees.size();i++) {
							
				theModel.addAttribute("notification", theEmployees.get(i).getNotification());
		
		}
		
		// add to the spring model
		theModel.addAttribute("employees", theEmployees);		
		return "/employees/list-employees";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		// create model attribute to bind form data
		User theEmployee = new User();
		
		theModel.addAttribute("employee", theEmployee);
		
		return "/employees/employee-form";
	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("employeeId") long theId,
									Model theModel) {
		
		// get the employee from the service
		User theEmployee = userdao.findById(theId);
		
		// set employee as a model attribute to pre-populate the form
		theModel.addAttribute("employee", theEmployee);
		
		// send over to our form
		return "/employees/employee-form";			
	}
	
	
	@PostMapping("/save")
	public String saveEmployee(@ModelAttribute("employee") User theEmployee) {
		
		// save the employee
		userdao.save(theEmployee);
		
		// use a redirect to prevent duplicate submissions
		return "redirect:/employees/list";
	}
	
	
	@GetMapping("/delete")
	public String delete(@RequestParam("employeeId") long theId) {
		
		// delete the employee
		userdao.deleteById(theId);
		
		// redirect to /employees/list
		return "redirect:/employees/list";
		
	}
	
	@GetMapping("/search")
	public String delete(@RequestParam("employeeName") String theName,
						 Model theModel) {
		
		// delete the employee
		List<User> theEmployees = userdao.searchBy(theName);
		
		// add to the spring model
		theModel.addAttribute("employees", theEmployees);
		
		// send to /employees/list
		return "/employees/list-employees";
		
	}
	
}


















