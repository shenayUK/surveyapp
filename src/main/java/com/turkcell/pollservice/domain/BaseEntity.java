package com.turkcell.pollservice.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
@EqualsAndHashCode
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQ")
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false, precision = 15, scale = 0, columnDefinition = "NUMERIC")
    protected Long id;

    @UpdateTimestamp
    @Column(name = "update_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime updateTime;

    @CreationTimestamp
    @Column(name = "insert_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime insertTime;


    @Override
    public String toString() {
        return String.format("%s[id=%d]", getClass().getSimpleName(), getId());
    }
}