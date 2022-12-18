package com.hegemonies.kafkademo.model

import com.hegemonies.kafkademo.consts.TableNames
import jakarta.persistence.Column
import jakarta.persistence.Id
import org.hibernate.Hibernate
import org.springframework.data.relational.core.mapping.Table

@Table(name = TableNames.TOKENS)
data class Token(
    @Id
    val id: Long? = null,

    @Column(name = "user_id")
    val userId: Long,

    @Column(name = "created_at")
    val createdAt: Long,

    val token: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Token

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , userId = $userId , token = $token )"
    }
}