package com.odysay.nokserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NokServerApplication

fun main(args: Array<String>) {
    runApplication<NokServerApplication>(*args)
}
