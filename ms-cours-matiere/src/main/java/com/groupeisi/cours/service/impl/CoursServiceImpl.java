package com.groupeisi.cours.service.impl;

import com.groupeisi.cours.dto.requests.CoursDtoRequest;
import com.groupeisi.cours.dto.responses.CoursDtoResponse;
import com.groupeisi.cours.entities.Cours;
import com.groupeisi.cours.mapper.CoursMapper;
import com.groupeisi.cours.repository.CoursRepository;
import com.groupeisi.cours.service.CoursService;
import com.groupeisi.exception.EntityExistsException;
import com.groupeisi.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class CoursServiceImpl implements CoursService {
    private final CoursRepository coursRepository;
    private final CoursMapper coursMapper;
    private final MessageSource messageSource;

        @Override
        @Transactional
        public Optional<CoursDtoResponse> saveCours(CoursDtoRequest coursDtoRequest) {

            if (coursRepository.findByNom(coursDtoRequest.getNom()).isPresent()) {
                throw new EntityExistsException(messageSource.getMessage("cours.exists", new Object[]{coursDtoRequest.getNom()}, Locale.getDefault()));
            }

            var cours = coursRepository.save(coursMapper.toEntity(coursDtoRequest));
            return Optional.of(coursMapper.toDto(cours));
        }
    @Override
    public Optional<List<CoursDtoResponse>> getAllCours() {
        List<Cours> coursList = coursRepository.findAll();
        return Optional.of(coursMapper.toDtoList(coursList));
    }

    @Override
    public Optional<CoursDtoResponse> getCoursById(Long id) {
        return coursRepository.findById(id)
                .map(cours -> Optional.of(coursMapper.toDto(cours)))
                .orElseThrow(() ->new EntityExistsException(messageSource.getMessage("cours.notfound", new Object[]{id}, Locale.getDefault())));

                //.orElseThrow(() -> new NoSuchElementException("Aucun cours trouvé avec cet ID : " + id));

    }
    @Override
    public Optional<CoursDtoResponse> updateCours(CoursDtoRequest coursDtoRequest) {
        return coursRepository.findById(coursDtoRequest.getId())
                .map(cours -> {
                    cours.setId(coursDtoRequest.getId());
                    cours.setNom(coursDtoRequest.getNom());
                    cours.setDescription(coursDtoRequest.getDescription());
                    cours.setArchive(coursDtoRequest.isArchive());
                    var updateCours = coursRepository.save(cours);
                    return Optional.of(coursMapper.toDto(cours));
                })
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("cours.notfound", new Object[]{coursDtoRequest.getId()}, Locale.getDefault())));
    }

    @Override
    public boolean deleteCours(Long id) {
        if (coursRepository.findById(id).isEmpty()){
            throw new EntityExistsException("");
        }
        coursRepository.deleteById(id);
        return true;
    }
}
