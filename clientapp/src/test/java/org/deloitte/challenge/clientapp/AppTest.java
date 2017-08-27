package org.deloitte.challenge.clientapp;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
	Client client = ClientBuilder.newClient();
    WebTarget baseTarget = client.target("http://localhost:8080/customer-api/webapi/");
    WebTarget allCustomersTarget = baseTarget.path("customers");
    WebTarget singleMessageTarget = allCustomersTarget.path("{id}");
    WebTarget searchCriteriaTarget = allCustomersTarget.path("{criteria}").path("{value}");
    
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
}
