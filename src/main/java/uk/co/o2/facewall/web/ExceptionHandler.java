package uk.co.o2.facewall.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.status;


@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public Response toResponse(Exception exception) {
        logger.error("Uncaught Error: ", exception);
        Response response = status(500).entity("<h1> Sorry. An error occurred.</h1> <img src='http://www.eacdirectory.co.ke/blog/wp-content/uploads/2014/03/fail-whale.jpg' width='50%'/>").build();
        return response;
    }
}





