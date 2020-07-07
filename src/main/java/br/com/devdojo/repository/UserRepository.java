package br.com.devdojo.repository;

import br.com.devdojo.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author yvesguilherme on 06/07/2020.
 * @project spring-boot-essentials
 */
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findByUsername(String username);
}
