package com.example.clevervision.repository;

import com.example.clevervision.model.UsersModel;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<UsersModel,Integer> {
    Optional<UsersModel> findByLoginAndPassword(String login , String password );
    //UsersModel findFirstByLogin(String login);
    UsersModel findFirstByEmail(String email);
    UsersModel findFirstById(int id);

    List<UsersModel> findAllByRoleEquals(int role);
    void deleteById(int id);
    Page<UsersModel> findAllByLoginContaining(String name , Pageable pageable);

}
