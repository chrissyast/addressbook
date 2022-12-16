package com.astles.addressbook;

import com.astles.addressbook.auth.AuthService;
import com.astles.addressbook.controller.ContactController;
import com.astles.addressbook.entity.Contact;
import com.astles.addressbook.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AddressbookApplicationTests {

	@Autowired
	private ContactController contactController;

	@Autowired
	private ContactRepository repository;

	@BeforeEach
	void populateData() {
		Contact contact1 = new Contact();
		contact1.setUserId(AuthService.getCallingUserId());
		contact1.setName("John Smith");
		contact1.setPhoneNumber("235235235");
		repository.save(contact1);

		Contact contact2 = new Contact();
		contact2.setUserId(66);
		contact2.setName("John Doe");
		contact2.setPhoneNumber("32552525");
		repository.save(contact2);
	}

	@Test
	void getAllOnlyReturnsContactsForCurrentUser() {
		ResponseEntity<List<Contact>> contactsResponse = contactController.getAll();
		List<Contact> contacts = contactsResponse.getBody();
		assertEquals(contacts.size(), 1);
	}

}
