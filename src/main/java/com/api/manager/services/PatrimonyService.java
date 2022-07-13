package com.api.manager.services;

import com.api.manager.models.PatrimonyModel;
import com.api.manager.repositories.PatrimonyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PatrimonyService {

    @Autowired
    PatrimonyRepository patrimonyRepository;


    public PatrimonyModel save(PatrimonyModel patrimonyModel) {
        return patrimonyRepository.save(patrimonyModel);
    }

    public Iterable<PatrimonyModel> findAll() {
        return patrimonyRepository.findAll();
    }
}
