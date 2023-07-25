package com.rc.cloud.common.core.domain;

import com.rc.cloud.common.core.util.AssertUtils;

import java.io.Serializable;


public abstract class AbstractId implements Identity, Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    protected AbstractId() {
        super();
    }

    @Override
    public String id() {
        return this.id;
    }

    public String getId() {
        return this.id;
    }

    public boolean sameValueAs(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            AbstractId typedObject = (AbstractId) anObject;
            equalObjects = this.id().equals(typedObject.id());
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        return this.id().hashCode();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " [id=" + id + "]";
    }

    protected AbstractId(String anId) {
        this();

        this.setId(anId);
    }



    protected void validateId(String anId) {
        // implemented by subclasses for validation.
        // throws a runtime exception if invalid.
    }

    private void setId(String anId) {
        AssertUtils.assertArgumentNotEmpty(anId, "The basic identity is required.");
        AssertUtils.assertArgumentLength(anId, 32, "The basic identity must be 32 characters.");

        this.validateId(anId);

        this.id = anId;
    }

    @Override
    public boolean equals(Object anObject) {
        if (anObject != null && this.getClass() == anObject.getClass()) {
            AbstractId typedObject = (AbstractId) anObject;
            return this.sameValueAs((AbstractId)anObject);
        }
        return false;
    }
}
