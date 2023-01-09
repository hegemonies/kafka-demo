package com.hegemonies.kafkademo.model

import com.hegemonies.kafkademo.consts.TableNames
import com.hegemonies.kafkademo.dto.AbstractDto
import com.hegemonies.kafkademo.dto.TicketType
import jakarta.persistence.Column
import jakarta.persistence.Id
import org.springframework.data.relational.core.mapping.Table
import org.hibernate.Hibernate

@Table(name = TableNames.TICKETS)
data class Ticket(
    @Id
    val id: Long? = null,

    @Column(name = "ticket_number")
    val ticketNumber: Long,

    /**
     * @see [TicketType]
     */
    val type: Short,

    @Column(name = "created_at")
    val createdAt: Long,

    @Column(name = "closed_at")
    val closedAt: Long? = null
) : AbstractDto() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Ticket

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName +
                "(id = $id ," +
                " ticketNumber = $ticketNumber ," +
                " type = $type ," +
                " createdAt = $createdAt ," +
                " closedAt = $closedAt" +
                " )"
    }
}