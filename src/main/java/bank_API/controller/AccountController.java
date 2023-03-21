package bank_API.controller;

import bank_API.model.Account;
import bank_API.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }


    @PostMapping("/createaccount")
    public String createAccount(@RequestBody Account account) {
        accountService.saveAccount(account);
        return "account created";
    }

    @GetMapping("/balance:BE{id}")
    public String getBalanceById(@PathVariable Long id) {
        Optional<Account> account = accountService.getAccountById(id);
         if (account.isPresent()) {
            double balance = account.get().getBalance();
            return "Balance of account " + id + " is " + balance+"€";
        } else {
            return "Account not found";
        }
    }


    @PostMapping("/BE{id}/withdraw")
    public String withdrawFromAccount(@PathVariable Long id, @RequestParam double amount) {
        Optional<Account> account = accountService.getAccountById(id);

        if (account.isPresent()) {
            Account updatedAccount = account.get();

            if (updatedAccount.getBalance() >= amount) {
                updatedAccount.setBalance(updatedAccount.getBalance() - amount);
                accountService.saveAccount(updatedAccount);
                return "Withdrew " + amount + "€ from account BE" + id + ". New balance is " + updatedAccount.getBalance()+"€";
            } else {
                return "Insufficient funds";
            }
        } else {
            return "Account not found";
        }
    }


    @PostMapping("/BE{id}/deposit")
    public String depositToAccount(@PathVariable Long id, @RequestParam double amount) {
        Optional<Account> account = accountService.getAccountById(id);

        if (account.isPresent()) {
            Account updatedAccount = account.get();
            updatedAccount.setBalance(updatedAccount.getBalance() + amount);
            accountService.saveAccount(updatedAccount);
            return "Deposited " + amount +"€ to account " + id + ". New balance is " + updatedAccount.getBalance()+"€";
        } else {
            return "Account not found";
        }
    }


    @DeleteMapping("/BE{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        Optional<Account> account = accountService.getAccountById(id);
        if (account.isPresent()) {
            String AccountName = account.get().getName();
            accountService.deleteAccount(id);
            return ResponseEntity.ok("The bank account with number BE" + id + " and name " + AccountName + " has been deleted.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/jointaccounts")
    public List<Account> getJointAccounts(@RequestParam String name1, @RequestParam String name2) {
        List<Account> allAccounts = accountService.getAllAccounts();
        List<Account> jointAccounts = new ArrayList<>();

        for (Account account : allAccounts) {
            if (account.getName().equals(name1) || account.getName().equals(name2)) {
                jointAccounts.add(account);
            }
        }

        return jointAccounts;
    }

    @PostMapping("/createjointaccount")
    public String createJointAccount(@RequestBody List<Account> accounts) {
        if (accounts.size() != 2) {
            return "Joint account requires exactly two account holders";
        }

        Account account1 = accounts.get(0);
        Account account2 = accounts.get(1);

        if (account1.getBalance() < 0 || account2.getBalance() < 0) {
            return "Balance cannot be negative";
        }

        account1.setBalance(account1.getBalance() + account2.getBalance());
        account2.setBalance(account1.getBalance());

        accountService.saveAccount(account1);
        accountService.saveAccount(account2);

        return "Joint account created";
    }

}
