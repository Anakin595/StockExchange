package com.example.demo.controllers

import com.example.demo.models.TestResponse
import groovy.util.logging.Slf4j
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
class TestRestController {

    @GetMapping('/testing')
    TestResponse testRestMapping() {
        log.info('Test message working!!!')
        new TestResponse(id: 1, name: 'trying to test it')
    }

}
