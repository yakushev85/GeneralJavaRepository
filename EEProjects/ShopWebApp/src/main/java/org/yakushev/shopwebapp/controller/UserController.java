package org.yakushev.shopwebapp.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.yakushev.shopwebapp.model.User;
import org.yakushev.shopwebapp.service.UserService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private Gson gson;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public String getAll(HttpServletRequest request) {
        checkAdminRole(request);
        return gson.toJson(userService.getAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public String getItemById(@PathVariable Long id, HttpServletRequest request) {
        checkAdminRole(request);
        return gson.toJson(userService.getById(id));
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json")
    @Transactional
    public String add(@RequestBody User user, HttpServletRequest request) {
        checkAdminRole(request);
        return gson.toJson(userService.add(user));
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = "application/json")
    @Transactional
    public String update(@RequestBody User user, HttpServletRequest request) {
        checkAdminRole(request);
        return gson.toJson(userService.update(user));
    }

	@RequestMapping(value = "/username/{username}", method = RequestMethod.GET, produces = "application/json")
	public String findByUsername(@PathVariable String username, HttpServletRequest request) {
        checkAdminRole(request);
		return gson.toJson(userService.findByUsernameOrderByIdDesc(username));
	}

	private void checkAdminRole(HttpServletRequest request) {
        User user = userService.getUserFromRequest(request);

        if (user == null || !user.getRole().equalsIgnoreCase("admin")) {
            throw new IllegalArgumentException("User doesn't have access to the operation.");
        }
    }

}