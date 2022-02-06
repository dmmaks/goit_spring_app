package com.edu.goit.repository.interfaces;

import com.edu.goit.model.Credentials;

public interface CredentialsRepository extends BaseCrudRepository<Credentials, Long> {
    Credentials findByEmail(String email);
    Integer getCountEmailUsages (String email);
}
