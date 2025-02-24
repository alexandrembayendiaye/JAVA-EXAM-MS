package com.groupeisi.enseignants.service.impl;

import com.groupeisi.enseignants.dto.requests.EnseignantDtoRequest;
import com.groupeisi.enseignants.dto.responses.EnseignantDtoResponse;
import com.groupeisi.enseignants.entities.Enseignant;
import com.groupeisi.enseignants.mapper.EnseignantMapper;
import com.groupeisi.enseignants.repository.EnseignantRepository;
import com.groupeisi.enseignants.service.EnseignantService;
import com.groupeisi.exception.EntityExistsException;
import com.groupeisi.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class EnseignantServiceImpl implements EnseignantService {
    private final EnseignantRepository enseignantRepository;
    private final EnseignantMapper enseignantMapper;
    private final MessageSource messageSource;
    @Override
    @Transactional
    public Optional<EnseignantDtoResponse> saveEnseignant(EnseignantDtoRequest enseignantDtoRequest) {
        if (enseignantRepository.findByEmailPro(enseignantDtoRequest.getEmailPro()).isPresent()) {
            throw new EntityExistsException(messageSource.getMessage("enseignant.exists", new Object[]{enseignantDtoRequest.getEmailPro()}, Locale.getDefault()));
        }
        var enseignant = enseignantRepository.save(enseignantMapper.toEntity(enseignantDtoRequest));
        return Optional.of(enseignantMapper.toDto(enseignant));
    }

    @Override
    public Optional<List<EnseignantDtoResponse>> getAllEnseignants() {
        List<Enseignant> enseignantList = enseignantRepository.findAll();
        return Optional.of(enseignantMapper.toDtoList(enseignantList));
    }

    @Override
    public Optional<EnseignantDtoResponse> getEnseignantById(Long id) {
        return enseignantRepository.findById(id)
                .map(enseignant -> Optional.of(enseignantMapper.toDto(enseignant)))
                .orElseThrow(() -> new EntityExistsException(messageSource.getMessage("enseignant.notfound", new Object[]{id}, Locale.getDefault())));
    }

    @Override
    public Optional<EnseignantDtoResponse> updateEnseignant(EnseignantDtoRequest enseignantDtoRequest) {
        return enseignantRepository.findById(enseignantDtoRequest.getId())
                .map(enseignant -> {
                    enseignant.setNom(enseignantDtoRequest.getNom());
                    enseignant.setPrenom(enseignantDtoRequest.getPrenom());
                    enseignant.setEmailPro(enseignantDtoRequest.getEmailPro());
                    enseignant.setNumeroTelephone(enseignantDtoRequest.getNumeroTelephone());
                    var updatedEnseignant = enseignantRepository.save(enseignant);
                    return Optional.of(enseignantMapper.toDto(updatedEnseignant));
                })
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("enseignant.notfound", new Object[]{enseignantDtoRequest.getId()}, Locale.getDefault())));
    }

    @Override
    public boolean deleteEnseignant(Long id) {
        if (enseignantRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException(messageSource.getMessage("enseignant.notfound", new Object[]{id}, Locale.getDefault()));
        }
        enseignantRepository.deleteById(id);
        return true;
    }
}
