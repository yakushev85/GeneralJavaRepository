package org.yakushev.shopwebapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.yakushev.shopwebapp.model.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

	Page<User> getAll(Pageable pageable);

	User getById(Long id);

	User add(User value);

	User update(User value);

	User findByUsernameOrderByIdDesc(String username);

	User getUserFromRequest(HttpServletRequest request);

	void checkAdminRole(HttpServletRequest request);

	boolean isAdminRole(HttpServletRequest request);
}
