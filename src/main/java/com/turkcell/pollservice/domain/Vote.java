package com.turkcell.pollservice.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "votes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "poll_id",
                "user_id"
        })
})
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name="SEQ",sequenceName="votes_seq", allocationSize=1)
@Data
@Builder
public class Vote extends BaseEntity {


    @ManyToOne
    @JoinColumn(name = "poll_id", nullable = false)
    private Poll poll;

    @ManyToOne
    @JoinColumn
    private Choice choice;

    @Column(name = "user_id")
    private Long userId;


}