
package org.yein.animate.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yein.animate.backend.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserIdAndUserPassword(String userId, String userPassword);

}
