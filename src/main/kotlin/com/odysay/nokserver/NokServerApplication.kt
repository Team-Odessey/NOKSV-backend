package com.odysay.nokserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class NokServerApplication

fun main(args: Array<String>) {
    runApplication<NokServerApplication>(*args)
}
