package com.oovetest.webDemo.tracking.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;
import jakarta.persistence.EnumType;

import com.oovetest.webDemo.series.entity.Series;
import com.oovetest.webDemo.tracking.entity.TrackingStatus;

@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"series_id"})
    }
)
@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true) 
@NoArgsConstructor
public class SeriesTracking {
    //追蹤項目
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private Series series;

    @Enumerated(EnumType.STRING)
    private TrackingStatus status;

    private LocalDateTime createdAt;
}
