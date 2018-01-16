package com.luv2code.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.luv2code.hibernate.demo.entity.Employee;

public class HibernateChallengeDemo {

	public static void main(String[] args) {
		
		// create session factory
		SessionFactory factory = new Configuration()
							 .configure("hibernate.cfg.xml")
							 .addAnnotatedClass(Employee.class)
							 .buildSessionFactory();
		
		// create session
		Session session = factory.getCurrentSession();
		
		try {
			// CREATE 
			// create an employee object
			System.out.println("Creating a new employee object...");
			Employee employee1 = new Employee("John", "Doe", "ABC, Inc");
			Employee employee2 = new Employee("Jane", "Doe", "ABC, Inc");
			Employee employee3 = new Employee("Bob", "Smith", "unemployed");
			
			// start a transaction
			session.beginTransaction();
			
			// save the employee object
			System.out.println("Saving the employee...");
			session.save(employee1);
			session.save(employee2);
			session.save(employee3);
			
			// commit the transaction
			session.getTransaction().commit();
			
			System.out.println("Employee(s) have been saved!");
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
			
			// READ
			// get a new session and start transaction
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			// read the objects
			System.out.println("Reading in employee with id: " + employee2.getId());
			
			Employee emp = session.get(Employee.class, employee2.getId());
			
			System.out.println("Retrieved employee: " + emp);
			
			// commit the transaction
			session.getTransaction().commit();
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
			
			// UPDATE
			// get a new session and start transaction
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			// update the employee that was last retrieved
			System.out.println("Updating employee: " + emp);
			Employee myEmp = session.get(Employee.class, emp.getId());
			myEmp.setCompany("XYZ Company");
			System.out.println("Employee updated: " + emp);
			
			// commit the transaction
			session.getTransaction().commit();
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
			
			// DELETE
			// get a new session and start transaction
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			// delete an employee...
			System.out.println("Deleting employee(s) working for ABC");
			
			int count = session.createQuery("delete from Employee e where e.company like '%XYZ%'").executeUpdate();
			System.out.println("Deleted " + count + " employee record(s)");
			
			// commit the transaction
			session.getTransaction().commit();
			System.out.println("All done!!");				
			
		} finally {
			factory.close();
		}
		
		

	}

}
