package org.acme;

import static org.eclipse.microprofile.lra.annotation.ws.rs.LRA.LRA_HTTP_CONTEXT_HEADER;
import static org.eclipse.microprofile.lra.annotation.ws.rs.LRA.LRA_HTTP_ENDED_CONTEXT_HEADER;

import java.net.URI;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.lra.LRAResponse;
import org.eclipse.microprofile.lra.annotation.AfterLRA;
import org.eclipse.microprofile.lra.annotation.Complete;
import org.eclipse.microprofile.lra.annotation.LRAStatus;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;

@Path("/hello")
//@LRA
public class GreetingResource {

	
	
	
	@Context
	private UriInfo context;

	
	
	
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy";
    }
	
    //curl -X PUT -H "Long-Running-Action: byebye" http://localhost:8080/hello/do

  @PUT
  @Path("/do")
  @LRA(value = LRA.Type.REQUIRES_NEW)
  public Response doLRA(@HeaderParam(LRA_HTTP_CONTEXT_HEADER) URI lraId) {
  	System.err.println("DEBUG: doing " + lraId);
  	// pretend to do some work
  	try {
  		Thread.sleep(5000);
  	} catch (InterruptedException e) {
  		// ignore 
  	}
      return Response.ok().entity(lraId + "\n").build();
  }

    
    
    
	
	//curl -X PUT -H "Long-Running-Action: byebye" http://localhost:8080/hello/complete

	@Complete
    @Path("/complete")
    @PUT
    public Response complete(@HeaderParam(LRA_HTTP_CONTEXT_HEADER) URI lraId) {
    	System.err.println("DEBUG: completing " + lraId);
        return LRAResponse.completed();
    }

	
    //curl -X PUT -H "Long-Running-Action-Ended: byebye" -H "Content-Type: application/json" http://localhost:8080/hello/after -d '{"String": "Active"}'

    @PUT
    @Path("/after")
    @AfterLRA
    public Response afterLRA(@HeaderParam(LRA_HTTP_ENDED_CONTEXT_HEADER) URI endedLRAId, LRAStatus status) {
        System.err.println("DEBUG: after " + endedLRAId + " status " + status + " for path " + context.getPath());
        return Response.ok("ok").build();
    }
}