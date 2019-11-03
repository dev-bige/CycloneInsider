package edu.cyclone.insider.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
public class BaseModel {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;

    /**
     * @return the primary key uuid of the model
     */
    public UUID getUuid() {
        return uuid;
    }

    private void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
