package com.bravi.portfolio.service.impl;

import com.bravi.portfolio.dto.ContactRequest;
import com.bravi.portfolio.dto.ContactResponse;
import com.bravi.portfolio.entity.Contact;
import com.bravi.portfolio.mapper.ContactMapperImpl;
import com.bravi.portfolio.repository.ContactRepository;
import com.bravi.portfolio.util.BuilderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactServiceImplTest {

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private ContactMapperImpl contactMapper;

    @InjectMocks
    private ContactServiceImpl contactService;

    Contact contact;

    @BeforeEach
    void setUp() {
        contact = BuilderUtil.buildContact(null);
    }

    @Test
    void getAllContacts() {
        when(contactRepository.findAll()).thenReturn(List.of(contact));
        when(contactMapper.toDtoList(List.of(contact))).thenReturn(List.of(new ContactResponse()));

        contactService.getAllContacts();

        verify(contactRepository, atLeastOnce()).findAll();
        verify(contactMapper, atLeastOnce()).toDtoList(List.of(contact));
    }

    @Test
    void getContact() {
        when(contactRepository.findById(1L)).thenReturn(Optional.ofNullable(contact));
        when(contactMapper.toDto(contact)).thenReturn(new ContactResponse());

        contactService.getContact(1L);

        verify(contactRepository, atLeastOnce()).findById(1L);
        verify(contactMapper, atLeastOnce()).toDto(contact);
    }

    @Test
    void createContact() {
        when(contactRepository.save(contact)).thenReturn(contact);
        when(contactMapper.toEntity(any(ContactRequest.class))).thenReturn(contact);
        when(contactMapper.toDto(contact)).thenReturn(new ContactResponse());

        contactService.saveContact(new ContactRequest());

        verify(contactRepository, atLeastOnce()).save(contact);
        verify(contactMapper, atLeastOnce()).toEntity(new ContactRequest());
        verify(contactMapper, atLeastOnce()).toDto(contact);
    }

    @Test
    void updateContact() {
        when(contactRepository.findById(1L)).thenReturn(Optional.ofNullable(contact));
        when(contactRepository.save(contact)).thenReturn(contact);
        when(contactMapper.toEntity(any(Contact.class), any(ContactRequest.class))).thenReturn(contact);
        when(contactMapper.toDto(contact)).thenReturn(new ContactResponse());

        contactService.updateContact(1L, new ContactRequest());

        verify(contactRepository, atLeastOnce()).findById(1L);
        verify(contactRepository, atLeastOnce()).save(contact);
        verify(contactMapper, atLeastOnce()).toEntity(contact, new ContactRequest());
        verify(contactMapper, atLeastOnce()).toDto(contact);
    }

    @Test
    void deleteContact() {
        when(contactRepository.existsById(1L)).thenReturn(true);

        contactService.deleteContact(1L);

        verify(contactRepository, atLeastOnce()).existsById(1L);
        verify(contactRepository, atLeastOnce()).deleteById(1L);
    }
}