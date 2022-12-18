package com.hegemonies.kafkademo.dto

enum class TicketType(val value: Int) {
    MORTGAGE(1),
    SALARY(2),
    ACCOUNT(3),
    PAY_BILL(4),
    MAIL_SERVICE(5);

    fun getOffset(): Long =
        when (this) {
            MORTGAGE -> 0
            SALARY -> 1_000
            ACCOUNT -> 10_000
            PAY_BILL -> 100_000
            MAIL_SERVICE -> 1_000_000
        }

    val shortValue: Short
        get() = this.value.toShort()
}
