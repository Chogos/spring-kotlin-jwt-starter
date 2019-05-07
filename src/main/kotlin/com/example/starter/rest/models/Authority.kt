package com.example.starter.rest.models

import org.springframework.security.core.GrantedAuthority
import javax.persistence.*

@Entity @Table(name="authorities")
data class Authority(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,
        @Column(name = "name") @Enumerated(EnumType.STRING) val role: UserRole
) : GrantedAuthority {
    override fun getAuthority(): String = role.name
}
