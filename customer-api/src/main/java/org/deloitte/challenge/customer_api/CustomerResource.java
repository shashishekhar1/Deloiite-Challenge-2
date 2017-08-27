package org.deloitte.challenge.customer_api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.deloitte.challenge.customer_api.models.Customer;
import org.deloitte.challenge.customer_api.service.CustomerService;

@Path("customers")
public class CustomerResource {

	private CustomerService service = new CustomerService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomers(@Context Request request) 
	{
		List<Customer> allCustomers = service.getAllCustomers();
		EntityTag eTag = new EntityTag(new Integer(allCustomers.hashCode()).toString());
		
		CacheControl cc = new CacheControl();
		cc.setMaxAge(3600);
		cc.setPrivate(true);

		ResponseBuilder builder = request.evaluatePreconditions(eTag);
		if (builder != null) {
	         builder.cacheControl(cc);
	         return builder.status(Status.NOT_MODIFIED)
	        		       .build();
	    }
		builder = Response.status(Status.FOUND);
		return builder.entity(allCustomers)
				.cacheControl(cc)
				.tag(eTag)
				.build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getCustomerById(@Context Request request,
									@PathParam("id") long id)
	{
		Customer customer = service.getCustomerById(id);
		CacheControl cc = new CacheControl();
		cc.setNoCache(true);
		if(customer == null)
		{
			return Response.status(Status.NOT_FOUND)
					.build();
		}

		return Response.status(Status.FOUND)
				.entity(customer)
				.cacheControl(cc)
				.build();
	}

	@GET
	@Path("/{criteria}/{value}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getCustomersBySearchCriteria(@Context Request request,
												 @PathParam("criteria") String criteria,
											   	 @PathParam("value") String value) 
	{
		List<Customer> customers = service.getCustomersBySearchCriteria(criteria, value);

		if(customers.isEmpty())
		{
			return Response.status(Status.NOT_FOUND)
					.build();
		}

		EntityTag eTag = new EntityTag(new Integer(customers.hashCode()).toString());
		CacheControl cc = new CacheControl();
		cc.setMaxAge(3600);
		cc.setPrivate(true);

		ResponseBuilder builder = request.evaluatePreconditions(eTag);
		if (builder != null) {
	         builder.cacheControl(cc);
	         return builder.status(Status.NOT_MODIFIED)
	        		       .build();
	    }

		builder = Response.status(Status.FOUND);
		return builder.entity(customers)
				.tag(eTag)
				.build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addCustomer(Customer customer)
	{
		Customer c = service.addCutomer(customer);
		return Response.status(Status.CREATED)
				.entity(c)
				.build();
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateCustomer(Customer customer)
	{
		Customer c = service.updateCustomer(customer);

		return Response.status(Status.OK)
				.entity(c)
				.build();
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response removeCustomer(@PathParam("id") long id)
	{
		Customer c = service.removeCustomer(id);

		return Response.status(Status.OK)
				.entity(c)
				.build();
	}
}
