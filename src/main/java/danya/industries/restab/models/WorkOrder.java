package danya.industries.restab.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Float percentProgress;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Prediction prediction;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "workOrder"
    )
    private List<WorkDay> workDays;

    public void addWorkDay(WorkDay workDay) {
        if (workDay == null) return;
        this.workDays.add(workDay);
        workDay.setWorkOrder(this);
    }

    public void removeWorkDay(WorkDay workDay) {
        if (workDay == null) return;
        this.workDays.remove(workDay);
        workDay.setWorkOrder(null);

    }
}
