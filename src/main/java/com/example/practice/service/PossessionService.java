package com.example.practice.service;

import com.example.practice.dto.ItemDto;
import com.example.practice.dto.PossessionDto;
import com.example.practice.dto.UserDto;
import com.example.practice.dto.UsersItemIdsDto;
import com.example.practice.entity.ArchivePossession;
import com.example.practice.entity.Possession;
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
    private final PossessionMapper possessionMapper;
    private final ArchivePossessionRepository archivePossessionRepository;
    private final ItemMapper itemMapper;

    public void addPossession(UserDto user, ItemDto item) {
        Possession poss = possessionRepository.findByItemId(item.getId());
        if (poss != null) {
            throw new UsersItemExistsException(poss);
        } else {
            PossessionDto possession = new PossessionDto();
            possession.setUser(user);
            possession.setItem(item);
            possession.setWithDate(Instant.now());
            possessionRepository.save(possessionMapper.fromDto(possession));
        }
    }

    public List<PossessionDto> readAll() {
        return possessionMapper.toDto(possessionRepository.findAll());
    }

    public List<ItemDto> getUserItems(Long userId) {
        return itemMapper.toDto(possessionRepository.findByUserId(userId)
                .stream()
                .map(Possession::getItem)
                .collect(Collectors.toList()));
    }

    public void removeItemFromUser(UsersItemIdsDto ids) {
        Possession possession = possessionRepository.findByUserIdAndItemId(ids.getUserId(), ids.getItemId());
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
}
