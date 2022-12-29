package com.example.practice.service;

import com.example.practice.dto.ItemDto;
import com.example.practice.dto.PossessionDto;
import com.example.practice.entity.ArchivePossession;
import com.example.practice.entity.Item;
import com.example.practice.entity.Possession;
import com.example.practice.entity.User;
import com.example.practice.exceptions.UsersItemExistsException;
import com.example.practice.exceptions.UsersItemNotFoundException;
import com.example.practice.mapper.ItemMapper;
import com.example.practice.mapper.PossessionMapper;
import com.example.practice.repository.ArchivePossessionRepository;
import com.example.practice.repository.PossessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PossessionService {

    private final PossessionRepository possessionRepository;
    private final ArchivePossessionRepository archivePossessionRepository;
    private final PossessionMapper possessionMapper;


    public void addPossession(User user, Item item) {
        Possession poss = possessionRepository.findByItemId(item.getId());
        if (poss != null) {
            throw new UsersItemExistsException(poss);
        } else {
            Possession possession = new Possession();
            possession.setUser(user);
            possession.setItem(item);
            possession.setWithDate(Instant.now());
            possessionRepository.save(possession);
        }
    }

    public PossessionDto getPossessionsByItemId(Long itemId) {
        Possession p = possessionRepository.findByItemId(itemId);
        if (p == null) {
            throw new UsersItemNotFoundException();
        }
        return possessionMapper.toDto(p);
    }

    public List<PossessionDto> getPossessionsByUserId(Long userId) {
        List<Possession> p = possessionRepository.findByUserId(userId);
        if (p == null) {
            throw new UsersItemNotFoundException();
        }
        return possessionMapper.toDto(p);
    }

    public void deletePossession(Long userId, Long itemId) {
        Possession possession = possessionRepository.findByUserIdAndItemId(userId, itemId);
        if (possession == null) {
            throw new UsersItemNotFoundException();
        }
        ArchivePossession archivePossession = new ArchivePossession();
        archivePossession.setUser(possession.getUser());
        archivePossession.setItem(possession.getItem());
        archivePossession.setWithDate(possession.getWithDate());
        archivePossession.setToDate(Instant.now());
        archivePossessionRepository.save(archivePossession);
        possessionRepository.delete(possession);
    }

//    public List<PossessionDto> getAllPossessions() {
//        return possessionRepository.findAll();
//    }
}
