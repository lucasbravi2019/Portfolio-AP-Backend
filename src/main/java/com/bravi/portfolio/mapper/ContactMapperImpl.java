package com.bravi.portfolio.mapper;

import com.bravi.portfolio.dto.*;
import com.bravi.portfolio.entity.Contact;
import com.bravi.portfolio.repository.PersonaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.bravi.portfolio.util.MapperUtil.*;

@AllArgsConstructor
@Component
public class ContactMapperImpl implements IMapper<Contact, ContactRequest, ContactResponse> {

    private final PersonaRepository personaRepository;

    @Override
    public Contact toEntity(ContactRequest request) {
        return Contact.builder()
                .description(request.getDescription())
                .contact(request.getContact())
                .persona(
                        mapNullableObjectWithFilter(
                                request.getPersonaId(),
                                personaRepository::existsById,
                                personaRepository::getReferenceById
                        )
                )
                .build();
    }

    @Override
    public Contact toEntity(Contact entity, ContactRequest request) {
        entity.setContact(request.getContact());
        entity.setDescription(request.getDescription());
        entity.setPersona(
                mapNullableObjectWithFilter(
                        request.getPersonaId(),
                        personaRepository::existsById,
                        personaRepository::getReferenceById
                )
        );
        return entity;
    }

    @Override
    public ContactResponse toDto(Contact entity) {
        return RelationshipMapper.mapContactResponse(entity, null);
    }

    @Override
    public List<ContactResponse> toDtoList(List<Contact> list) {
        return streamNullableList(list, this::toDto);
    }

}
