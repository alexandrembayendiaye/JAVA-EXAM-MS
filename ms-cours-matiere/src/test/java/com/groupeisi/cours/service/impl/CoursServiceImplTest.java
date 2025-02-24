package com.groupeisi.cours.service.impl;

import com.groupeisi.cours.dto.requests.CoursDtoRequest;
import com.groupeisi.cours.dto.responses.CoursDtoResponse;
import com.groupeisi.cours.entities.Cours;
import com.groupeisi.cours.mapper.CoursMapper;
import com.groupeisi.cours.repository.CoursRepository;
import com.groupeisi.exception.EntityExistsException;
import com.groupeisi.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CoursServiceImplTest {

    @InjectMocks
    private CoursServiceImpl coursService;

    @Mock
    private CoursRepository coursRepository;

    @Mock
    private CoursMapper coursMapper;

    @Mock
    private MessageSource messageSource;

    private CoursDtoRequest coursDtoRequest;
    private CoursDtoResponse coursDtoResponse;
    private Cours cours;

    @BeforeEach
    void setUp() {
        coursDtoRequest = new CoursDtoRequest();
        coursDtoRequest.setId(1L);
        coursDtoRequest.setNom("Mathématiques");
        coursDtoRequest.setDescription("Cours de math");
        coursDtoRequest.setArchive(false);

        coursDtoResponse = new CoursDtoResponse();
        coursDtoResponse.setId(1L);
        coursDtoResponse.setNom("Mathématiques");
        coursDtoResponse.setDescription("Cours de math");
        coursDtoResponse.setArchive(false);

        cours = new Cours();
        cours.setId(1L);
        cours.setNom("Mathématiques");
        cours.setDescription("Cours de math");
        cours.setArchive(false);
    }

    @Test
    void saveCoursOK() {
        when(coursRepository.findByNom(coursDtoRequest.getNom())).thenReturn(Optional.empty());
        when(coursMapper.toEntity(coursDtoRequest)).thenReturn(cours);
        when(coursRepository.save(cours)).thenReturn(cours);
        when(coursMapper.toDto(cours)).thenReturn(coursDtoResponse);

        Optional<CoursDtoResponse> savedCours = coursService.saveCours(coursDtoRequest);

        assertTrue(savedCours.isPresent());
        assertEquals("Mathématiques", savedCours.get().getNom());
        verify(coursRepository, times(1)).save(cours);
    }

    @Test
    void saveCoursKO() {
        when(coursRepository.findByNom(coursDtoRequest.getNom())).thenReturn(Optional.of(cours));
        when(messageSource.getMessage(anyString(), any(), any(Locale.class))).thenReturn("the cours with name = {0} is already created.");

        assertThrows(EntityExistsException.class, () -> coursService.saveCours(coursDtoRequest));
        verify(coursRepository, never()).save(any());
    }

    @Test
    void getAllCours() {
        when(coursRepository.findAll()).thenReturn(List.of(cours));
        when(coursMapper.toDtoList(List.of(cours))).thenReturn(List.of(coursDtoResponse));

        Optional<List<CoursDtoResponse>> coursList = coursService.getAllCours();

        assertTrue(coursList.isPresent());
        assertEquals(1, coursList.get().size());
        assertEquals("Mathématiques", coursList.get().get(0).getNom());
    }

    @Test
    void getCoursByIdOK() {
        when(coursRepository.findById(1L)).thenReturn(Optional.of(cours));
        when(coursMapper.toDto(cours)).thenReturn(coursDtoResponse);

        Optional<CoursDtoResponse> result = coursService.getCoursById(1L);

        assertTrue(result.isPresent());
        assertEquals("Mathématiques", result.get().getNom());
    }

    @Test
    void getCoursByIdKO() {
        when(coursRepository.findById(1L)).thenReturn(Optional.empty());
        when(messageSource.getMessage(anyString(), any(), any(Locale.class))).thenReturn("Cours non trouvé");

        assertThrows(EntityExistsException.class, () -> coursService.getCoursById(1L));
    }

    @Test
    void updateCoursOK() {
        when(coursRepository.findById(1L)).thenReturn(Optional.of(cours));
        when(coursRepository.save(any(Cours.class))).thenReturn(cours);
        when(coursMapper.toDto(cours)).thenReturn(coursDtoResponse);

        Optional<CoursDtoResponse> updatedCours = coursService.updateCours(coursDtoRequest);

        assertTrue(updatedCours.isPresent());
        assertEquals("Mathématiques", updatedCours.get().getNom());
    }

    @Test
    void updateCoursKO() {
        when(coursRepository.findById(1L)).thenReturn(Optional.empty());
        when(messageSource.getMessage(anyString(), any(), any(Locale.class))).thenReturn("Cours non trouvé");

        assertThrows(EntityNotFoundException.class, () -> coursService.updateCours(coursDtoRequest));
    }

    @Test
    void deleteCoursOK() {
        when(coursRepository.findById(1L)).thenReturn(Optional.of(cours));
        doNothing().when(coursRepository).deleteById(1L);

        boolean deleted = coursService.deleteCours(1L);

        assertTrue(deleted);
        verify(coursRepository, times(1)).deleteById(1L);
    }

//    @Test
//    void deleteCoursKO() {
//        when(coursRepository.findById(1L)).thenReturn(Optional.empty());
//        when(messageSource.getMessage(anyString(), any(), any(Locale.class))).thenReturn("");
//
//        assertThrows(EntityExistsException.class, () -> coursService.deleteCours(1L));
//        verify(coursRepository, never()).deleteById(anyLong());
//    }
}