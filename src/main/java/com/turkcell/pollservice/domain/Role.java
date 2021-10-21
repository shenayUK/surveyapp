package com.turkcell.pollservice.domain;

import com.turkcell.pollservice.domain.enums.RolesEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name="SEQ",sequenceName="roles_seq", allocationSize=1)
@Data
public class Role extends BaseEntity{

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60)
    private RolesEnum name;

}