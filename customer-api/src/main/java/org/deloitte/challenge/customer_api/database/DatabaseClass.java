package org.deloitte.challenge.customer_api.database;

import java.util.HashMap;
import java.util.Map;

import org.deloitte.challenge.customer_api.models.Customer;

public class DatabaseClass {

	private static Map<Long, Customer> customers = new HashMap<>();
	
	static
	{
		customers.put(new Long(1), new Customer(1, "Shashi", "Prashar", "Strathfield - 2135"));
		customers.put(new Long(2), new Customer(2, "Shuja", "Rahman", "Homebush - 2140"));
		customers.put(new Long(3), new Customer(3, "Rakesh", "Roshan", "Homebush - 2140"));
	}
	public static Map<Long, Customer> getCustomers()
	{
		return customers;
	}
}
