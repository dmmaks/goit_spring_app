package com.edu.goit.mapper;

import com.edu.goit.dto.AccountDTO;
import com.edu.goit.model.Credentials;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring")
public interface CredentialsMapper {
    @Mappings({
            @Mapping(target="email", source="dto.email"),
            @Mapping(target="password", source="dto.password")
    })
    Credentials accountDTOtoCredentials(AccountDTO dto);
}
