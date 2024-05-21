package gr.aueb.cf.schoolappspringbootmvc.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * AbstractEntity is a base class for all entities that provides
 * auditing fields and active status. It includes fields for
 * creation date, last modified date, and active status.
 *
 * <p>
 * This class is annotated with {@code @MappedSuperclass} to indicate
 * that its properties should be included in its subclasses.
 * It also includes JPA and Hibernate annotations for auditing
 * and dynamic SQL generation.
 * </p>
 *
 * <p>
 * The class is annotated with:
 * <ul>
 *     <li>{@code @Getter} and {@code @Setter} to generate getter and setter methods for all fields.</li>
 *     <li>{@code @NoArgsConstructor} to generate a no-argument constructor.</li>
 *     <li>{@code @AllArgsConstructor} to generate a constructor with all fields as parameters.</li>
 *     <li>{@code @DynamicInsert} to indicate that SQL INSERT statements should be generated dynamically.</li>
 *     <li>{@code @DynamicUpdate} to indicate that SQL UPDATE statements should be generated dynamically.</li>
 *     <li>{@code @EntityListeners} to specify callback listeners for entity lifecycle events.</li>
 * </ul>
 * </p>
 */
@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity {

    /**
     * The date and time when the entity was created.
     * It is set automatically when the entity is persisted.
     * This field cannot be updated.
     */
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * The date and time when the entity was last modified.
     * It is set automatically when the entity is updated.
     */
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Indicates whether the entity is active.
     * The default value is true.
     */
    @ColumnDefault("true")
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
}
