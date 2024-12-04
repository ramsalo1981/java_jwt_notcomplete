package com.ramsa.company.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_roles")

public class UserRole {
    @EmbeddedId
    private UserRoleId id;

    @ManyToOne
    @MapsId("userId")
    @NotNull(message = "User  is required")
    private User user;

    @ManyToOne
    @MapsId("roleId")
    @NotNull(message = "Role is required")
    private Role role;
}
