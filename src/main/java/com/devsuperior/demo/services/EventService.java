package com.devsuperior.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.demo.dto.EventDTO;
import com.devsuperior.demo.entities.Event;
import com.devsuperior.demo.repositories.CityRepository;
import com.devsuperior.demo.repositories.EventRepository;

@Service
public class EventService {

    @Autowired
    private EventRepository repository;

    @Autowired
    private CityRepository cityRepository;

    @Transactional(readOnly = true)
    public Page<EventDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(EventDTO::new);
    }

    @Transactional
    public EventDTO insert (EventDTO dto) {
        Event entity = new Event(null, dto.getName(), dto.getDate(), dto.getUrl(), cityRepository.getReferenceById(dto.getCityId()));
        return new EventDTO(entity);
    }


}
