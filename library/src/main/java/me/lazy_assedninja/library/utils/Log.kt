package me.lazy_assedninja.library.utils

interface Log {
    fun v(tag: String?, msg: String)

    fun d(tag: String?, msg: String)

    fun i(tag: String?, msg: String)

    fun w(tag: String?, msg: String)

    fun e(tag: String?, msg: String)
}