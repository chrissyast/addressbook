package com.astles.addressbook.controller;

import com.astles.addressbook.entity.Contact;
import com.astles.addressbook.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Component
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    public ContactRepository contactRepository;

    @GetMapping
    public ResponseEntity<List<Contact>> getAll() {
        try {
            List<Contact> items = new ArrayList<>();

            contactRepository.findAll().forEach(items::add);

            if (items.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Contact> getById(@PathVariable("id") Integer id) {
        Optional<Contact> existingItemOptional = contactRepository.findById(id);

        if (existingItemOptional.isPresent()) {
            return new ResponseEntity<>(existingItemOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Contact> create(@RequestBody Contact item) {
        try {
            Contact savedItem = contactRepository.save(item);
            return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Contact> update(@PathVariable("id") Integer id, @RequestBody Contact item) {
        Optional<Contact> existingItemOptional = contactRepository.findById(id);
        if (existingItemOptional.isPresent()) {
            Contact existingItem = existingItemOptional.get();
            return new ResponseEntity<>(contactRepository.save(existingItem), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Integer id) {
        try {
            contactRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}