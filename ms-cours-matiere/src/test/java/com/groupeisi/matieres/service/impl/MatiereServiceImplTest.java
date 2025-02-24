package com.groupeisi.matieres.service.impl;

import com.groupeisi.exception.EntityExistsException;
import com.groupeisi.exception.EntityNotFoundException;
import com.groupeisi.matieres.dto.requests.MatiereDtoRequest;
import com.groupeisi.matieres.dto.responses.MatiereDtoResponse;
import com.groupeisi.matieres.entities.Matiere;
import com.groupeisi.matieres.mapper.MatiereMapper;
import com.groupeisi.matieres.repository.MatiereRepository;
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
class MatiereServiceImplTest {

    @InjectMocks
    private MatiereServiceImpl matiereService;

    @Mock
    private MatiereRepository matiereRepository;

    @Mock
    private MatiereMapper matiereMapper;

    @Mock
    private MessageSource messageSource;

    private MatiereDtoRequest matiereDtoRequest;
    private MatiereDtoResponse matiereDtoResponse;
    private Matiere matiere;

    @BeforeEach
    void setUp() {
        matiereDtoRequest = new MatiereDtoRequest();
        matiereDtoRequest.setId(1L);
        matiereDtoRequest.setNom("Physique");
        matiereDtoRequest.setDescription("Cours de physique");
        matiereDtoRequest.setArchive(false);

        matiereDtoResponse = new MatiereDtoResponse();
        matiereDtoResponse.setId(1L);
        matiereDtoResponse.setNom("Physique");
        matiereDtoResponse.setDescription("Cours de physique");
        matiereDtoResponse.setArchive(false);

        matiere = new Matiere();
        matiere.setId(1L);
        matiere.setNom("Physique");
        matiere.setDescription("Cours de physique");
        matiere.setArchive(false);
    }

    @Test
    void saveMatiereOK() {
        when(matiereRepository.findByNom(matiereDtoRequest.getNom())).thenReturn(Optional.empty());
        when(matiereMapper.toEntity(matiereDtoRequest)).thenReturn(matiere);
        when(matiereRepository.save(matiere)).thenReturn(matiere);
        when(matiereMapper.toDto(matiere)).thenReturn(matiereDtoResponse);

        Optional<MatiereDtoResponse> savedMatiere = matiereService.saveMatiere(matiereDtoRequest);

        assertTrue(savedMatiere.isPresent());
        assertEquals("Physique", savedMatiere.get().getNom());
        verify(matiereRepository, times(1)).save(matiere);
    }

    @Test
    void saveMatiereKO() {
        when(matiereRepository.findByNom(matiereDtoRequest.getNom())).thenReturn(Optional.of(matiere));
        when(messageSource.getMessage(anyString(), any(), any(Locale.class))).thenReturn("The matiere with name = {0} is already created.");

        assertThrows(EntityExistsException.class, () -> matiereService.saveMatiere(matiereDtoRequest));
        verify(matiereRepository, never()).save(any());
    }

    @Test
    void getAllMatieres() {
        when(matiereRepository.findAll()).thenReturn(List.of(matiere));
        when(matiereMapper.toDtoList(List.of(matiere))).thenReturn(List.of(matiereDtoResponse));

        Optional<List<MatiereDtoResponse>> matieresList = matiereService.getAllMatieres();

        assertTrue(matieresList.isPresent());
        assertEquals(1, matieresList.get().size());
        assertEquals("Physique", matieresList.get().get(0).getNom());
    }

    @Test
    void getMatiereByIdOK() {
        when(matiereRepository.findById(1L)).thenReturn(Optional.of(matiere));
        when(matiereMapper.toDto(matiere)).thenReturn(matiereDtoResponse);

        Optional<MatiereDtoResponse> result = matiereService.getMatiereById(1L);

        assertTrue(result.isPresent());
        assertEquals("Physique", result.get().getNom());
    }

/*    @Test
    void getMatiereByIdKO() {
        when(matiereRepository.findById(1L)).thenReturn(Optional.empty());
        when(messageSource.getMessage(anyString(), any(), any(Locale.class))).thenReturn("Matiere non trouvée");

        assertThrows(EntityNotFoundException.class, () -> matiereService.getMatiereById(1L));
    }*/

    @Test
    void updateMatiereOK() {
        when(matiereRepository.findById(1L)).thenReturn(Optional.of(matiere));
        when(matiereRepository.save(any(Matiere.class))).thenReturn(matiere);
        when(matiereMapper.toDto(matiere)).thenReturn(matiereDtoResponse);

        Optional<MatiereDtoResponse> updatedMatiere = matiereService.updateMatiere(matiereDtoRequest);

        assertTrue(updatedMatiere.isPresent());
        assertEquals("Physique", updatedMatiere.get().getNom());
    }

    @Test
    void updateMatiereKO() {
        when(matiereRepository.findById(1L)).thenReturn(Optional.empty());
        when(messageSource.getMessage(anyString(), any(), any(Locale.class))).thenReturn("Matiere non trouvée");

        assertThrows(EntityNotFoundException.class, () -> matiereService.updateMatiere(matiereDtoRequest));
    }

    @Test
    void deleteMatiereOK() {
        when(matiereRepository.findById(1L)).thenReturn(Optional.of(matiere));
        doNothing().when(matiereRepository).deleteById(1L);

        boolean deleted = matiereService.deleteMatiere(1L);

        assertTrue(deleted);
        verify(matiereRepository, times(1)).deleteById(1L);
    }
}