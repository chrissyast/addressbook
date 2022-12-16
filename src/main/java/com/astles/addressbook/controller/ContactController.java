package com.astles.addressbook.controller;

import com.astles.addressbook.auth.AuthService;
import com.astles.addressbook.entity.Contact;
import com.astles.addressbook.exceptions.ValidationExceptionHandler;
import com.astles.addressbook.repository.ContactRepository;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Component
@RequestMapping("/contacts")
@Validated
public class ContactController {

    @Autowired
    public ContactRepository contactRepository;

    @GetMapping
    public ResponseEntity<List<Contact>> getAll() {
        try {
            List<Contact> items = new ArrayList<>();
            Integer loggedInUserId = AuthService.getCallingUserId();
            contactRepository.findAllByUserId(loggedInUserId).forEach(items::add);
            if (items.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Contact> getById(@PathVariable("id") Integer id) {
        Optional<Contact> existingItemOptional = contactRepository.findById(id);
        Integer loggedInUserId = AuthService.getCallingUserId();
        if (existingItemOptional.isPresent() && existingItemOptional.get().getId() == loggedInUserId) {
            return new ResponseEntity<>(existingItemOptional.get(), HttpStatus.OK);
        } else if (existingItemOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Contact item) {
        Integer loggedInUserId = AuthService.getCallingUserId();
        item.setUserId(loggedInUserId);
        try {
            Contact savedItem = contactRepository.save(item);
            return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            return new ValidationExceptionHandler().handle(e);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Contact> update(@PathVariable("id") Integer id, @RequestBody Contact item) {
        Integer loggedInUserId = AuthService.getCallingUserId();
        try {
            Optional<Contact> existingItemOptional = contactRepository.findById(id);
            if (existingItemOptional.isPresent() && existingItemOptional.get().getId() == loggedInUserId) {
                return new ResponseEntity<>(contactRepository.save(item), HttpStatus.OK);
            } else if (existingItemOptional.isPresent()) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Integer id) {
        Integer loggedInUserId = AuthService.getCallingUserId();
        try {
            Optional<Contact> existingItemOptional = contactRepository.findById(id);
            if (existingItemOptional.isPresent() && existingItemOptional.get().getId() == loggedInUserId) {
                contactRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else if (existingItemOptional.isPresent()) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}