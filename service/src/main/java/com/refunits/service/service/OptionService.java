package com.refunits.service.service;

import com.refunits.database.model.Option;
import com.refunits.database.repository.OptionRepository;
import com.refunits.service.dto.CreateOption;
import com.refunits.service.dto.ViewOptionInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@CacheConfig(cacheNames = "options")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OptionService {

    private final OptionRepository optionRepository;

    public List<ViewOptionInfo> getAll() {

        return optionRepository.findAll().stream().map(opt -> new ViewOptionInfo(opt.getId(),
                opt.getName(), opt.getPrice(), opt.getDescription())).collect(Collectors.toList());
    }

    @Cacheable
    public Optional<ViewOptionInfo> getById(Integer id) {
        ViewOptionInfo option = optionRepository.findById(id)
                .map(opt -> ViewOptionInfo.builder()
                        .id(opt.getId())
                        .name(opt.getName())
                        .price(opt.getPrice())
                        .build())
                .orElseThrow(RuntimeException::new);

        return Optional.of(option);
    }

    public Integer save(CreateOption createOption) {

        return optionRepository.save(new Option(createOption.getName(), createOption.getDescription(),
                createOption.getPrice())).getId();
    }
}