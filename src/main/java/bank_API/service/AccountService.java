package bank_API.service;

import bank_API.model.Account;
import bank_API.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(Long.valueOf(id));
    }


    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    public void addAccountHolder(Long id, String accountHolder) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            List<String> accountHolders = account.getAccountHolders();
            accountHolders.add(accountHolder);
            accountRepository.save(account);
        }
    }

    public void removeAccountHolder(Long id, String accountHolder) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            List<String> accountHolders = account.getAccountHolders();
            accountHolders.remove(accountHolder);
            accountRepository.save(account);
        }
    }
}
