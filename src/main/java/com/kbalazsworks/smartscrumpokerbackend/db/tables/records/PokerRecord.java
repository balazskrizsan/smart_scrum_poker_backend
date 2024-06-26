/*
 * This file is generated by jOOQ.
 */
package com.kbalazsworks.smartscrumpokerbackend.db.tables.records;


import com.kbalazsworks.smartscrumpokerbackend.db.tables.Poker;

import java.time.LocalDateTime;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PokerRecord extends UpdatableRecordImpl<PokerRecord> implements Record5<Long, UUID, String, LocalDateTime, UUID> {

    private static final long serialVersionUID = -903999809;

    /**
     * Setter for <code>public.poker.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.poker.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.poker.id_secure</code>.
     */
    public void setIdSecure(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.poker.id_secure</code>.
     */
    public UUID getIdSecure() {
        return (UUID) get(1);
    }

    /**
     * Setter for <code>public.poker.name</code>.
     */
    public void setName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.poker.name</code>.
     */
    public String getName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.poker.created_at</code>.
     */
    public void setCreatedAt(LocalDateTime value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.poker.created_at</code>.
     */
    public LocalDateTime getCreatedAt() {
        return (LocalDateTime) get(3);
    }

    /**
     * Setter for <code>public.poker.created_by</code>.
     */
    public void setCreatedBy(UUID value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.poker.created_by</code>.
     */
    public UUID getCreatedBy() {
        return (UUID) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row5<Long, UUID, String, LocalDateTime, UUID> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    public Row5<Long, UUID, String, LocalDateTime, UUID> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return Poker.POKER.ID;
    }

    @Override
    public Field<UUID> field2() {
        return Poker.POKER.ID_SECURE;
    }

    @Override
    public Field<String> field3() {
        return Poker.POKER.NAME;
    }

    @Override
    public Field<LocalDateTime> field4() {
        return Poker.POKER.CREATED_AT;
    }

    @Override
    public Field<UUID> field5() {
        return Poker.POKER.CREATED_BY;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public UUID component2() {
        return getIdSecure();
    }

    @Override
    public String component3() {
        return getName();
    }

    @Override
    public LocalDateTime component4() {
        return getCreatedAt();
    }

    @Override
    public UUID component5() {
        return getCreatedBy();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public UUID value2() {
        return getIdSecure();
    }

    @Override
    public String value3() {
        return getName();
    }

    @Override
    public LocalDateTime value4() {
        return getCreatedAt();
    }

    @Override
    public UUID value5() {
        return getCreatedBy();
    }

    @Override
    public PokerRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public PokerRecord value2(UUID value) {
        setIdSecure(value);
        return this;
    }

    @Override
    public PokerRecord value3(String value) {
        setName(value);
        return this;
    }

    @Override
    public PokerRecord value4(LocalDateTime value) {
        setCreatedAt(value);
        return this;
    }

    @Override
    public PokerRecord value5(UUID value) {
        setCreatedBy(value);
        return this;
    }

    @Override
    public PokerRecord values(Long value1, UUID value2, String value3, LocalDateTime value4, UUID value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PokerRecord
     */
    public PokerRecord() {
        super(Poker.POKER);
    }

    /**
     * Create a detached, initialised PokerRecord
     */
    public PokerRecord(Long id, UUID idSecure, String name, LocalDateTime createdAt, UUID createdBy) {
        super(Poker.POKER);

        set(0, id);
        set(1, idSecure);
        set(2, name);
        set(3, createdAt);
        set(4, createdBy);
    }
}
