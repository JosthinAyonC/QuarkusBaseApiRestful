package services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import customResponse.CustomResponse;
import models.User;
import repositories.UserRepository;

@ApplicationScoped
public class UserService {

    CustomResponse customRes;

    @Inject
    private UserRepository userRepository;

    public Response getAll() {
        if (userRepository.findAll().count() == 0) {
            customRes = new CustomResponse("No se encontraton usuarios", 404);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(customRes)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } else {
            return Response
                    .accepted(userRepository.findAll().list())
                    .build();
        }
    }

    public Response getByIdentity(String identity) {
        if (userRepository.findByIdentity(identity) == null) {
            customRes = new CustomResponse("No se encontro un usuario con identidad: " + identity, 404);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(customRes)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } else {
            return Response
                    .accepted(userRepository.findByIdentity(identity))
                    .build();
        }
    }

    public Response save(User user) {
        try {
            Response response = validateUniqueConstraints(user);
            if (response != null) {
                return response;
            }
            userRepository.persist(user);
            return Response
                    .status(Response.Status.CREATED)
                    .entity(user)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

    public Response update(Long id, User user) {
        try {
            User userToUpdate = userRepository.findById(id);
            saveUserUpdated(userToUpdate, user);
            userRepository.persist(userToUpdate);
            return Response
                    .status(Response.Status.CREATED)
                    .entity(userToUpdate)
                    .build();
        } catch (Exception e) {
            customRes = new CustomResponse(e.getMessage(), 500);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(customRes)
                    .build();
        }
    }

    public Response deleteLogic(Long id) {
        try {
            User user = userRepository.findById(id);
            if (user.getStatus() == 'N' || user == null) {
                customRes = new CustomResponse("No se encontro un usuario con id: " + id, 404);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(customRes)
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
            user.setStatus('N');
            userRepository.persist(user);
            return Response
                    .status(Response.Status.OK)
                    .entity(customRes)
                    .build();
        } catch (Exception e) {
            customRes = new CustomResponse(e.getMessage(), 500);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(customRes)
                    .build();
        }
    }

    // Actualizaciones parciales
    private void saveUserUpdated(User userToUpdate, User user) {
        if (user.getEmail() != null) {
            userToUpdate.setEmail(user.getEmail());
        }
        if (user.getFirstname() != null) {
            userToUpdate.setFirstname(user.getFirstname());
        }
        if (user.getLastname() != null) {
            userToUpdate.setLastname(user.getLastname());
        }
        if (user.getIdentity() != null) {
            userToUpdate.setIdentity(user.getIdentity());
        }
        if (user.getPassword() != null) {
            userToUpdate.setPassword(user.getPassword());
        }
        if (user.getPhone() != null) {
            userToUpdate.setPhone(user.getPhone());
        }
        if (user.getRoles() != null) {
            user.getRoles().size();
        }
        
    }

    //Validar campos unicos
    private Response validateUniqueConstraints(User user){
        if (userRepository.findByIdentity(user.getIdentity()) != null) {
            customRes = new CustomResponse("Ya existe un usuario con identidad: " + user.getIdentity(), 400);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(customRes)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        if (userRepository.findByUsername(user.getUsername()) != null) {
            customRes = new CustomResponse("Ya existe un usuario con nombre de usuario: " + user.getUsername(), 400);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(customRes)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            customRes = new CustomResponse("Ya existe un usuario con email: " + user.getEmail(), 400);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(customRes)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        return null;
    }

}
