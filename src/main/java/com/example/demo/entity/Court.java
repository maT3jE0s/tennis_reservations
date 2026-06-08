package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing a tennis court in the system.
 * Each court has a unique number and is assigned a surface type.
 */
@Entity
@Table(name = "courts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Court extends BaseEntity {

    @Column(unique = true)
    private Integer courtNumber;

    @ManyToOne
    private SurfaceType surfaceType;
}
