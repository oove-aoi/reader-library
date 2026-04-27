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

    // 已購買的卷數(最大連續集數) 目前不適配非連續集數的情況，後續可能會改成set或list的紀錄
    //private Integer ownedVolume; 目前決定使用時在service撈就好，不在entity紀錄，避免資料不一致的問題

    @Enumerated(EnumType.STRING)
    private TrackingStatus status;

    private LocalDateTime createdAt;

}
