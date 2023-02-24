package com.ogutcenali.repository;

import com.ogutcenali.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface IUserRepository extends JpaRepository<User,Long> {

    List<User> findAllByOrderByName();

    List<User> findAllByNameLike(String name);

    List<User> findAllByEmailContainingIgnoreCase(String value);


    List<User> findAllByEmailEndingWith(String value);
    List<User> findAllByEmailEndsWith(String value);
    List<User> findAllByEmailStartingWith(String value);
    Boolean existsByEmailAndPassword(String email,String password);
    Boolean existsByEmail(String email);
    Optional<User> findOptionalByEmailAndPassword(String email,String password);

    @Query(value = "select * from tbl_user where length(password)>?1",nativeQuery = true)
    List<User> passwordLongerThan(int length);

    @Query(value = "select u from User as u where length(u.password)>:x")
    List<User> passwordControl(@Param("x") int length);
}
