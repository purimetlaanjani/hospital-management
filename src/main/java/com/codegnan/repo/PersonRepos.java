package com.codegnan.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codegnan.entity.Person;

/*  because doctor and patient inherit from person
 * this repository can perform quries on person object
 * but usaually we work doctorrepo or patient repo for type satisfy
 */


@Repository
public interface PersonRepos extends JpaRepository<Person,Integer>{

}
