package cz.muni.fi.anatomytutor.web.restserver;

import cz.muni.fi.anatomytutor.api.UserService;
import cz.muni.fi.anatomytutor.api.dto.AuthUserDto;
import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.RecoverableDataAccessException;

/**
 * REST resource for operations on AuthUser entities. Jersey version 2.X.X
 *
 * @author Jan Kucera
 */
@Path("/user")
public class UserRestResource {

    private AuthUserDto user = null;
//    Spring beans can't be injected directly into JAX-RS classes by using just Spring XML configuration,
//    instead Jersey jersey-spring3 dependency needs to be added
    @Autowired
    private UserService userService;
    final static Logger log = LoggerFactory.getLogger(UserRestResource.class);

    /*
     * Finds a user by id, returns that user.
     */
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getUserById(@PathParam("id") Long id) {
        log.debug("Server: get user with id: " + id);
        if (id == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            user = userService.getById(id);
        } catch (RecoverableDataAccessException ex) {
            if (ex.getCause().getClass().equals(NoResultException.class)) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            throw new WebApplicationException(ex, Response.Status.INTERNAL_SERVER_ERROR);
        }
        return Response.status(Response.Status.OK).entity(user).build();
    }

    /*
     * Registers a new user, returns that user dto object, having its id (userId) assigned.
     */
    @POST
    @Path("/createuser")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createUser(AuthUserDto newUser) {
        log.debug("Server: create user: " + newUser);
        if (newUser == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            userService.register(newUser);
        } catch (IllegalArgumentException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            user = userService.getById(newUser.getId());
        } catch (RecoverableDataAccessException ex) {
            throw new WebApplicationException(ex, Response.Status.INTERNAL_SERVER_ERROR);
        }
        Response.ResponseBuilder builder = Response.status(Response.Status.OK);
        builder.entity(user);
        return makeCORS(builder);
    }

    /*
     * Updates user, returning dto object as it was updated in the database. 
     */
    @PUT
    @Path("/updateuser")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateUser(AuthUserDto userToUpdate) {
        log.debug("Server: update with user: " + userToUpdate);
        if (userToUpdate == null || userToUpdate.getId() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            userService.update(userToUpdate);
            user = userService.getById(userToUpdate.getId());
        } catch (RecoverableDataAccessException ex) {
            if (ex.getCause().getClass().equals(IllegalArgumentException.class)) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            throw new WebApplicationException(ex, Response.Status.INTERNAL_SERVER_ERROR);
        }
        Response.ResponseBuilder builder = Response.status(Response.Status.OK);
        builder.entity(user);
        return makeCORS(builder);
        //return Response.status(Response.Status.OK).entity(user).build();
    }

    /*
     * Removes user from DB (unregisters), returns only status information.
     */
    @DELETE
    @Path("/removeuser/{id}")
    public Response removeUser(@PathParam("id") Long id) {
        log.debug("Server: remove user with id: " + id);
        if (id == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            userService.remove(userService.getById(id));
        } catch (RecoverableDataAccessException ex) {
            if (ex.getCause().getClass().equals(IllegalArgumentException.class)
                    || ex.getCause().getClass().equals(NoResultException.class)) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            throw new WebApplicationException(ex, Response.Status.INTERNAL_SERVER_ERROR);
        }
        Response.ResponseBuilder builder = Response.status(Response.Status.OK);
        return makeCORS(builder);
        //return Response.status(Response.Status.OK).build();
    }

    // Cross domain
    private String _corsHeaders;

    private Response makeCORS(ResponseBuilder req, String returnMethod) {
        ResponseBuilder rb = req.header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");

        if (!"".equals(returnMethod)) {
            rb.header("Access-Control-Allow-Headers", returnMethod);
        }

        return rb.build();
    }

    private Response makeCORS(ResponseBuilder req) {
        return makeCORS(req, _corsHeaders);
    }

    // This OPTIONS request/response is necessary
    // if you consumes other format than text/plain or
    // if you use other HTTP verbs than GET and POST
    // nevím jak mapovat vice cest najednou, podle různých zdroju to snad ani nejde?
    @OPTIONS
    @Path("/createuser")
    public Response corsMyResource(@HeaderParam("Access-Control-Request-Headers") String requestH) {
        _corsHeaders = requestH;
        return makeCORS(Response.ok(), requestH);
    }

    @OPTIONS
    @Path("/updateuser")
    public Response corsMyResource2(@HeaderParam("Access-Control-Request-Headers") String requestH) {
        _corsHeaders = requestH;
        return makeCORS(Response.ok(), requestH);
    }

    @OPTIONS
    @Path("/removeuser/{id}")
    public Response corsMyResource3(@HeaderParam("Access-Control-Request-Headers") String requestH) {
        _corsHeaders = requestH;
        return makeCORS(Response.ok(), requestH);
    }
}
