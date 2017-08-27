package org.deloitte.challenge.clientapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.deloitte.challenge.clientapp.model.Customer;

/**
 * Customer - REST Client
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Client client = ClientBuilder.newClient();
        WebTarget baseTarget = client.target("http://localhost:8080/customer-api/webapi/");
        WebTarget allCustomersTarget = baseTarget.path("customers");
        WebTarget singleMessageTarget = allCustomersTarget.path("{id}");
        WebTarget searchCriteriaTarget = allCustomersTarget.path("{criteria}").path("{value}");
        
        Response response = allCustomersTarget.request().get();
        List<Customer> customerList = response.readEntity(List.class);
        System.out.println(customerList.toString());
        
        Response response2 = singleMessageTarget.resolveTemplate("id", "2")
        										.request()
        										.get();
        Customer customer = response2.readEntity(Customer.class);
        System.out.println(customer.toString());
        
        Map<String, Object> pathParams = new HashMap<String, Object>();
        pathParams.put("criteria", "address");
        pathParams.put("value", "2140");
        
        Response response3 = searchCriteriaTarget.resolveTemplates(pathParams)
										         .request()
												 .get();
        List<Customer> c2 = response3.readEntity(List.class);
        System.out.println(c2.toString());
        
        Customer c4 = new Customer(4, "Mani", "XXX", "Homebush - 2135");
        Response response4 = allCustomersTarget.request()
        				  .post(Entity.json(c4));
        System.out.println(response4.toString());
    }
}
