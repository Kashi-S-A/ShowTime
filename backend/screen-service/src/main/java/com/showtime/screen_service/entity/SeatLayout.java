package com.showtime.screen_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"screen_id", "row_label"}
    )
)
@Data
public class SeatLayout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer layoutId;

    @Column(name = "screen_id", nullable = false)
    private Integer screenId;

    @Column(name = "row_label", nullable = false)
    private String rowLabel;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String layout;
    // example: "1,2,0,0,3,4,5,0,6"
}

