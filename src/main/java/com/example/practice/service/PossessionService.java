package com.example.practice.service;

import com.example.practice.dto.PossessionDto;
import com.example.practice.dto.UsersItemIdsDto;
import com.example.practice.entity.ArchivePossession;
import com.example.practice.entity.Item;
import com.example.practice.entity.User;
import com.example.practice.entity.Possession;
import com.example.practice.exceptions.ItemNotFoundException;
import com.example.practice.exceptions.UserNotFoundException;
import com.example.practice.exceptions.UsersItemNotFoundException;
import com.example.practice.mapper.PossessionMapper;
import com.example.practice.repository.ArchivePossessionRepository;
import com.example.practice.repository.ItemRepository;
import com.example.practice.repository.PossessionRepository;
import com.example.practice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PossessionService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final PossessionRepository possessionRepository;
    private final PossessionMapper possessionMapper;
    private final ArchivePossessionRepository archivePossessionRepository;

    public void addItemToUser(UsersItemIdsDto usersItemIdsDto) {
        Optional<User> user = userRepository.findById(usersItemIdsDto.getUserId());
        Optional<Item> item = itemRepository.findById(usersItemIdsDto.getItemId());
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        if (item.isEmpty()) {
            throw new ItemNotFoundException();
        }
        Possession possession = new Possession();
        possession.setUser(user.get());
        possession.setItem(item.get());
        possession.setWithDate(Instant.now());
        possessionRepository.save(possession);

    }

    public List<PossessionDto> readAll() {
        return possessionMapper.toDto(possessionRepository.findAll());
    }

    public void removeItemFromUser(UsersItemIdsDto uikd) {
        Possession possession = possessionRepository.findByUserIdAndItemId(uikd.getUserId(), uikd.getItemId());
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
