package com.af.arabimotors.entities;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "conditions")
public class ConditionsEntity implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3877068715530326366L;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private String id;

    @Column(name = "condition_name", nullable = false)
    private String conditionName;

    @Column(name = "enabled", nullable = false, columnDefinition = "boolean default true")
    private boolean enabled;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }

    @Override
    public String toString() {
        return "ConditionsEntity{" +
                "id=" + id +
                ", conditionName='" + conditionName + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
