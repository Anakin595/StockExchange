package com.example.demo.controllers

import com.example.demo.models.Account
import com.example.demo.repository.AccountsRepository
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
@RequestMapping('/account')
class AccountsController {

    AccountsRepository accountsRepository

    @Autowired
    AccountsController(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository
    }

    @PostMapping('account')
    Long createAccount(@RequestParam('account') Account account) {
        accountsRepository.createNewAccount(account)
    }

    @GetMapping('/{id}')
    Account getAccountById(@PathVariable('id') Long id) {
        log.info("get account by id")
        accountsRepository.findById(id)
    }


}
