package org.deloitte.challenge.clientapp;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.deloitte.challenge.clientapp.model.Customer;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import junit.framework.TestCase;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomerApiTest extends TestCase {

	Client client = ClientBuilder.newClient();
    WebTarget baseTarget = client.target("http://localhost:8080/customer-api/webapi/");
    WebTarget allCustomersTarget = baseTarget.path("customers");
    WebTarget singleMessageTarget = allCustomersTarget.path("{id}");
    WebTarget searchCriteriaTarget = allCustomersTarget.path("{criteria}").path("{value}");
    
    public void testGetAllCustomerAPI() {
    	Response response = allCustomersTarget.request().get();
        List<Customer> customerList = response.readEntity(List.class);

        assertEquals(3, customerList.size());
        assertEquals(response.getStatus(), 302);
    }
    
    public void testGetCustomerById()
    {
    	Response response = singleMessageTarget.resolveTemplate("id", "2")
												.request()
												.get();
    	Customer customer = response.readEntity(Customer.class);
    	
    	assertEquals(customer.getId(), 2);
        assertEquals(response.getStatus(), 302);
    }
    
    public void testGetCustomerByIdNegative()
    {
    	Response response = singleMessageTarget.resolveTemplate("id", "100")
												.request()
												.get();

        assertEquals(response.getStatus(), 404);
    }

    public void testNewCustomerAdditionApi()
    {
    	Customer c = new Customer("Mani", "XXX", "Homebush - 2135");
        Response response = allCustomersTarget.request()
        				  .post(Entity.json(c));

        assertEquals(response.getStatus(), 201);
    }
    
    public void testUpdateCustomerApi()
    {
    	Response response = singleMessageTarget.resolveTemplate("id", "4")
				.request()
				.get();
    	Customer customer = response.readEntity(Customer.class);
    	customer.setLastName("YYYY");
    	
        Response updateResponse = allCustomersTarget.request()
        									        .put(Entity.json(customer));
        assertEquals(updateResponse.getStatus(), 200);

        Response updatedResponse = singleMessageTarget.resolveTemplate("id", "4")
				.request()
				.get();
        Customer updatedCustomer = updatedResponse.readEntity(Customer.class);
        
        assertEquals(updatedCustomer.getLastName(), "YYYY");
        assertEquals(response.getStatus(), 302);
    }
    
    public void testUpdateDeleteCustomerApi()
    {
        Response deleteResponse = singleMessageTarget.resolveTemplate("id", "4")
        											 .request()
        											 .delete();
        assertEquals(deleteResponse.getStatus(), 200);

        Response response = singleMessageTarget.resolveTemplate("id", "4")
				.request()
				.get();
        assertEquals(response.getStatus(), 404);
    }
}
