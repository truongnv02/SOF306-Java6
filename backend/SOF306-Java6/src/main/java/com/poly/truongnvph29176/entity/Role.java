package com.poly.truongnvph29176.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "Roles")
public class Role implements Serializable {
    @Id
    @Column(name = "Id", nullable = false)
    private String id;

    @Column(name = "Name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "role",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Authority> authoritys;

    public static String DIRE = "DIRE";
    public static String STAFF = "STAFF";
    public static String CUST = "CUST";
}
