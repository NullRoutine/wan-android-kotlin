package com.nullroutine.wan.http

class ApiException(var code: Int, override var message: String) : RuntimeException()