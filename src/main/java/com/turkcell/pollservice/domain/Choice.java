package com.turkcell.pollservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Table(name = "choices")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name="SEQ",sequenceName="choice_seq", allocationSize=1)
@Getter
@Setter
@Builder
public class Choice extends BaseEntity{


    @NotBlank
    @Size(max = 60)
    private String option;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "poll_id", nullable = false)
    private Poll poll;
}