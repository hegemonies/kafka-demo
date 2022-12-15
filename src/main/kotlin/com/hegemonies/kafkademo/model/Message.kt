package com.hegemonies.kafkademo.model

import jakarta.persistence.Id
import org.hibernate.Hibernate
import org.springframework.data.relational.core.mapping.Table

@Table(name = "messages")
data class Message(
    @Id
    val id: Long? = null,

    val message: String,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Message

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    override fun toString(): String =
        this::class.simpleName + "(id = $id , message = $message)"
}
