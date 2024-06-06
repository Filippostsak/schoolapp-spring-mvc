package gr.aueb.cf.schoolappspringbootmvc.model;

import io.swagger.v3.oas.annotations.media.Schema;
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
 * This is an abstract class that represents an entity in the database.
 * It contains fields that are common to all entities, such as the creation and update timestamps.
 */

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@Schema(description = "An abstract entity with common fields for all entities.")
public abstract class AbstractEntity {


    /**
     * This field represents the creation date and time of the entity.
     * It is annotated with @CreatedDate from Spring Data to automatically set the creation date and time when the entity is persisted.
     * It is also annotated with @Column from the Jakarta Persistence API to specify details about the database column that will be used to persist this field.
     * The name of the column is "created_at", it is not nullable, and it is not updatable after the entity is created.
     */
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @Schema(description = "The date and time when the entity was created.")
    private LocalDateTime createdAt;

    /**
     * This field represents the last update date and time of the entity.
     * It is annotated with @LastModifiedDate from Spring Data to automatically set the update date and time whenever the entity is updated.
     * It is also annotated with @Column from the Jakarta Persistence API to specify details about the database column that will be used to persist this field.
     * The name of the column is "updated_at", and it is not nullable.
     */
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    @Schema(description = "The date and time when the entity was last updated.")
    private LocalDateTime updatedAt;

    /**
     * This field represents the active status of the entity.
     * It is annotated with @ColumnDefault from Hibernate to specify the default value of the column in the database, which is "true".
     * It is also annotated with @Column from the Jakarta Persistence API to specify details about the database column that will be used to persist this field.
     * The name of the column is "is_active", and it is not nullable.
     */
    @ColumnDefault("true")
    @Column(name = "is_active", nullable = false)
    @Schema(description = "The active status of the entity.")
    private Boolean isActive;
}
