package com.example.starter.rest.models

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import javax.persistence.*

@Entity @Table(name = "users")
data class User(
        @Id @Column(name="id") @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,
        val email: String,
        private val username: String,
        private var password: String,
        @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
        @JoinTable(name = "user_authorities",
                joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "authority_id", referencedColumnName = "id")])
        private val authorities: MutableList<Authority>,
        var lastPasswordChangeDate: Date
) : UserDetails {

    private var enabled: Boolean = true

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = authorities
    override fun isEnabled(): Boolean = enabled
    override fun getUsername(): String = username
    override fun getPassword(): String = password
    override fun isCredentialsNonExpired(): Boolean = enabled
    override fun isAccountNonExpired(): Boolean = enabled
    override fun isAccountNonLocked(): Boolean = enabled

    fun setPassword(password: String) {
        this.password = password
    }
}
