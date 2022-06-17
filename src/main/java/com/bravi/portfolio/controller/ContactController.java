package com.bravi.portfolio.controller;

import com.bravi.portfolio.dto.ContactRequest;
import com.bravi.portfolio.dto.ContactResponse;
import com.bravi.portfolio.paths.PathName;
import com.bravi.portfolio.service.impl.ContactServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(PathName.CONTACT)
public class ContactController {

    private final ContactServiceImpl contactService;

    @GetMapping
    public ResponseEntity<List<ContactResponse>> getAllContacts() {
        return ResponseEntity.ok(contactService.getAllContacts());
    }

    @GetMapping(PathName.PATH_ID)
    public ResponseEntity<ContactResponse> getContact(@PathVariable Long id) {
        return ResponseEntity.ok(contactService.getContact(id));
    }

    @PostMapping
    public ResponseEntity<ContactResponse> createContact(@Valid @RequestBody ContactRequest contact) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contactService.saveContact(contact));
    }

    @PutMapping(PathName.PATH_ID)
    public ResponseEntity<ContactResponse> updateContact(@PathVariable Long id, @Valid @RequestBody ContactRequest contact) {
        return ResponseEntity.ok(contactService.updateContact(id, contact));
    }

    @DeleteMapping(PathName.PATH_ID)
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
