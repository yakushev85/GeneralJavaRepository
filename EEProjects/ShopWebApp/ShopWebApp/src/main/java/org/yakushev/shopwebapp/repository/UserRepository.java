package org.yakushev.shopwebapp.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.yakushev.shopwebapp.model.User;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	User findByUsernameOrderByIdDesc(String username);
}
