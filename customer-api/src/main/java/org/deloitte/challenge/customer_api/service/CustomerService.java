package org.deloitte.challenge.customer_api.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.deloitte.challenge.customer_api.database.DatabaseClass;
import org.deloitte.challenge.customer_api.models.Customer;

public class CustomerService {

	private static Map<Long, Customer> customers = DatabaseClass.getCustomers();
	
	public List<Customer> getAllCustomers()
	{
		return new ArrayList<>(customers.values());
	}
	
	public List<Customer> getCustomersBySearchCriteria(String criteria, String value)
	{
		List<Customer> filteredList = new ArrayList<>();
		
		for(Customer customer : customers.values()) {
			switch(criteria) {
			case "firstname" :
				if(customer.getFirstName().equalsIgnoreCase(value)) {
					filteredList.add(customer);
				}
				break;
				
			case "lastname" :
				if(customer.getLastName().equalsIgnoreCase(value)) {
					filteredList.add(customer);
				}
				break;
				
			case "address" :
				if(customer.getAddress().contains(value)) {
					filteredList.add(customer);
				}
				break;
			}
		}
		return filteredList;
	}

	public synchronized Customer addCutomer(Customer c)
	{
		c.setId(customers.size() + 1);
		return customers.put(c.getId(), c);
	}
	
	public Customer removeCustomer(long id)
	{
		return customers.remove(id);
	}
	
	public Customer updateCustomer(Customer c)
	{
		c.setModifiedDate(new Date());
		return customers.put(c.getId(), c);
	}
	
	public Customer getCustomerById(long id)
	{
		return customers.get(id);
	}
}
