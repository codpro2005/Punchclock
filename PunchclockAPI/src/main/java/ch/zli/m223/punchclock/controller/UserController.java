package ch.zli.m223.punchclock.controller;

import ch.zli.m223.punchclock.domain.Entry;
import ch.zli.m223.punchclock.domain.User;
import ch.zli.m223.punchclock.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ch.zli.m223.punchclock.security.SecurityConstants.HEADER_STRING;
import static ch.zli.m223.punchclock.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

        this.generateUsers();
    }

    /**
     * Script to generate users
     * @return generated users
     */
    private List<User> generateUsers() {
        User user1 = new User("Danilo", "Passwort123");
        User user2 = new User("Pascal", "Pascalwort123");
        User user3 = new User("Official Donald Trump", "BigWalls1");
        Entry entry1 = new Entry(LocalDateTime.now().minusHours(3), LocalDateTime.now().minusHours(1), user1);
        Entry entry2 = new Entry(LocalDateTime.now().minusMinutes(5), LocalDateTime.now(), user1);
        Entry entry3 = new Entry(LocalDateTime.now().minusHours(5), LocalDateTime.now().minusHours(2), user3);
        user1.setEntries(Arrays.asList(entry1, entry2));
        user3.setEntries(Arrays.asList(entry3));
        List<User> users = Arrays.asList(user1, user2, user3);
        List<User> addedUsers = new ArrayList<>();
        for (User user : users) {
            addedUsers.add(this.createUser(user));
        }
        return addedUsers;
    }

    /**
     * searches for, and returns all users
     * @return all users
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    /**
     * returns the username found inside the jwt in the servlet request.
     * @param request the request in which the jwt is located.
     * @returns the correct username.
     */
    private String getUsernameByJWT(HttpServletRequest request) {
        String jwt = request.getHeader(HEADER_STRING).substring(TOKEN_PREFIX.length());
        String[] split_string = jwt.split("\\.");
//        String base64EncodedHeader = split_string[0];
        String base64EncodedBody = split_string[1];
//        String base64EncodedSignature = split_string[2];

        String payload = new String(new Base64(true).decode(base64EncodedBody));
        JSONObject object = new JSONObject(payload);
        String username = object.getString("sub");
        return username;
    }

    /**
     * returns the user found by the username.
     * @param request the request in which the jwt is located to be passed to the getUsernameByJWT function.
     * @returns the user
     */
    public User getUserByJWT(@Valid HttpServletRequest request) {
        String username = this.getUsernameByJWT(request);
        User matchingUser = userService.findAll().stream().filter(t -> t.getUsername().equals(username)).findFirst().get();
        return matchingUser;
    }

    /**
     * checks if the jwt is valid or not.
     * @param request the request in which the jwt is located.
     * @returns the boolean value if the jwt is valid.
     */
    @RequestMapping("/valid")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public boolean getJWTValid(@Valid HttpServletRequest request) {
        String username = getUsernameByJWT(request);
        boolean userExists = userService.findAll().stream().anyMatch(t -> t.getUsername().equals(username));
        return userExists;
    }

    /**
     * searches for the current user by the jwt.
     * @param request the request in which the jwt is located.
     * @returns the found user.
     */
    @RequestMapping("/current")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public User getCurrentUser(@Valid HttpServletRequest request) {
        User matchingUser = getUserByJWT(request);
        return matchingUser;
    }

    /**
     * the function to allow the creation of a user without needing a jwt to do this process.
     * @param user the user to be created.
     * @returns the created user.
     */
    @RequestMapping("/sign-up")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@Valid @RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userService.createUser(user);
    }

    /**
     * updates a specific user.
     * @param user the user to be updated.
     * @param request the request in which the jwt is located.
     * @returns the updated user.
     */
    @RequestMapping("update")
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@Valid @RequestBody User user,@Valid HttpServletRequest request) {
        user.setId(getUserByJWT(request).getId());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userService.updateUser(user);
    }

    /**
     * deletes a specific user found inside the jwt.
     * @param request the request in which the jwt is located
     */
    @RequestMapping("/delete")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@Valid HttpServletRequest request) {
        User matchingUser = getUserByJWT(request);
        this.userService.deleteUser(matchingUser);
    }
}
