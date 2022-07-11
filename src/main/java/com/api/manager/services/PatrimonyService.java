package com.api.manager.services;

import com.api.manager.repositories.PatrimonyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatrimonyService {

    @Autowired
    PatrimonyRepository patrimonyRepository;
}
