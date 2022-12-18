package com.hegemonies.kafkademo.model

import com.hegemonies.kafkademo.consts.TableNames
import jakarta.persistence.Id
import org.springframework.data.relational.core.mapping.Table
import org.hibernate.Hibernate

@Table(name = TableNames.USERS)
data class User(
    @Id
    val id: Long? = null,

    val username: String,

    val password: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as User

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , username = $username )"
    }
}
