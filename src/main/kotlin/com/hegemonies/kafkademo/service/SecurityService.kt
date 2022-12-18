package com.hegemonies.kafkademo.service

import liquibase.util.MD5Util
import org.springframework.stereotype.Service

@Service
class SecurityService {

    fun encodePassword(password: String): String = MD5Util.computeMD5(password)
}
