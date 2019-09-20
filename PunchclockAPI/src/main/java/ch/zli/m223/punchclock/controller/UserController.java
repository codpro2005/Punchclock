package ch.zli.m223.punchclock.controller;

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
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {
        return userService.findAll();
    }

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

    public User getUserByJWT(@Valid HttpServletRequest request) {
        String username = this.getUsernameByJWT(request);
        User matchingUser = userService.findAll().stream().filter(t -> t.getUsername().equals(username)).findFirst().get();
        return matchingUser;
    }

    @RequestMapping("/valid")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public boolean getJWTValid(@Valid HttpServletRequest request) {
        String username = getUsernameByJWT(request);
        boolean userExists = userService.findAll().stream().anyMatch(t -> t.getUsername().equals(username));
        return userExists;
    }

    @RequestMapping("/current")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public User getCurrentUser(@Valid HttpServletRequest request) throws IOException, NoSuchFieldException, IllegalAccessException {
        User matchingUser = getUserByJWT(request);
        return matchingUser;
    }

    @RequestMapping("/sign-up")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@Valid @RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userService.createUser(user);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@Valid HttpServletRequest request) {
        User matchingUser = getUserByJWT(request);
        userService.deleteUser(matchingUser);
    }
}
