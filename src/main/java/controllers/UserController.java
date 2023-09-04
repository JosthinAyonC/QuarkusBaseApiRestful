package controllers;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import models.User;
import services.UserService;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserController {
    @Inject
    private UserService userService;

    @GET
    public Response getAll() {
        return userService.getAll();
    }

    @GET
    @Path("/{identity}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getByIdentity(@PathParam("identity") String identity) {
        return userService.getByIdentity(identity);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response save(@Valid User user) {
        return userService.save(user);
    }

    @PUT
    @Path("/edit/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(@Valid @PathParam("id") Long id, User user) {
        return userService.update(id, user);
    }

    @PUT
    @Path("/delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        return userService.deleteLogic(id);
    }

}
