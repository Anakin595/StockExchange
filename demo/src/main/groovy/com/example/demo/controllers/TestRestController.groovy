package com.example.demo.controllers

import com.example.demo.models.TestResponse
import groovy.util.logging.Slf4j
import org.springframework.security.access.annotation.Secured
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
class TestRestController {

    @GetMapping('/testing')
    //@PreAuthorize("hasRole('ADMIN')")
    TestResponse testRestMapping() {
        log.info("Test: logged as: ${SecurityContextHolder.context.authentication.name}")
        new TestResponse(id: 1, name: 'trying to test it')
        
    }

}
