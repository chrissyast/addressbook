package com.astles.addressbook.repository;

import com.astles.addressbook.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer>, CrudRepository<Contact, Integer> {
    List<Contact> findAllByUserId(Integer id);
}