package com.edu.goit.mapper;


import com.edu.goit.model.Account;
import com.edu.goit.dto.NewModeratorDTO;
import com.edu.goit.model.UnconfirmedModerator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring")
public interface ModeratorMapper {
    @Mappings({
            @Mapping(target = "email", source = "moderatorDTO.email"),
            @Mapping(target="firstName", source="moderatorDTO.firstName"),
            @Mapping(target="lastName", source="moderatorDTO.lastName"),
            @Mapping(target = "birthDate", source = "moderatorDTO.birthDate"),
            @Mapping(target = "gender", source = "moderatorDTO.gender")
    })
    UnconfirmedModerator newModerDTOtoUnconfirmedModer(NewModeratorDTO moderatorDTO);

    @Mappings({
            @Mapping(target="firstName", source="moder.firstName"),
            @Mapping(target="lastName", source="moder.lastName"),
            @Mapping(target = "birthDate", source = "moder.birthDate"),
            @Mapping(target = "gender", source = "moder.gender")
    })
    Account unconfirmedModerToAccount (UnconfirmedModerator moder);
}