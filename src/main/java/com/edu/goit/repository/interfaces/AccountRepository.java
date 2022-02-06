package com.edu.goit.repository.interfaces;



import com.edu.goit.model.Account;
import com.edu.goit.model.AccountRole;

import java.util.Collection;

public interface AccountRepository extends BaseCrudRepository<Account, Long>{
    Account findByEmail(String email);
    int countAccountsBySearch (String search, AccountRole role, String gender, String status);
    Collection<Account> findAccountsBySearch (String search, String gender, AccountRole role, String status, int limit, int offset, boolean order);
    void updateStatus(long id);
}
