package oneToManyAnn;

import java.util.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class Impl {

  public static void main(String[] args) {
  // Load the configuration and build the SessionFactory
  Configuration configuration = HibernateConfig.getConfig();
  configuration.addAnnotatedClass(Department.class);
  configuration.addAnnotatedClass(Employee.class);
  
  ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
  		.applySettings(configuration.getProperties())
  		.build();
  SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
  System.out.println("SessionFactory created successfully.");
  // Create a session
  Session session = sessionFactory.openSession();
  Transaction transaction = null;

  
  try {
      transaction = session.beginTransaction();
      // Create a Department
      Department department = new Department();
      department.setName("IT");

      // Create Employee 1
      Employee employee1 = new Employee();
      employee1.setName("John Doe");
      employee1.setDepartment(department);  // Set department for employee

      // Create Employee 2
      Employee employee2 = new Employee();
      employee2.setName("Jane Smith");
      employee2.setDepartment(department);  // Set department for employee

   // Set the employees for the department
      Set<Employee> employees = new HashSet();
      employees.add(employee1);
      employees.add(employee2);
      department.setEmployees(employees);

      // Save the Department (Employees will be saved automatically because of cascade)
      session.persist(department);

      transaction.commit();
  } catch (Exception e) {
      if (transaction != null) transaction.rollback();
      e.printStackTrace();
  } finally {
      session.close();
      sessionFactory.close();
  }
}
}