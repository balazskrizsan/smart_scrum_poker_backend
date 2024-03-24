/*
 * This file is generated by jOOQ.
 */
package com.kbalazsworks.smartscrumpokerbackend.db.tables;


import com.kbalazsworks.smartscrumpokerbackend.db.Keys;
import com.kbalazsworks.smartscrumpokerbackend.db.Public;
import com.kbalazsworks.smartscrumpokerbackend.db.tables.records.PokerRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row5;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Poker extends TableImpl<PokerRecord> {

    private static final long serialVersionUID = -1618246336;

    /**
     * The reference instance of <code>public.poker</code>
     */
    public static final Poker POKER = new Poker();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PokerRecord> getRecordType() {
        return PokerRecord.class;
    }

    /**
     * The column <code>public.poker.id</code>.
     */
    public final TableField<PokerRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.poker.id_secure</code>.
     */
    public final TableField<PokerRecord, UUID> ID_SECURE = createField(DSL.name("id_secure"), org.jooq.impl.SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.poker.name</code>.
     */
    public final TableField<PokerRecord, String> NAME = createField(DSL.name("name"), org.jooq.impl.SQLDataType.VARCHAR.nullable(false), this, "");

    /**
     * The column <code>public.poker.created_at</code>.
     */
    public final TableField<PokerRecord, LocalDateTime> CREATED_AT = createField(DSL.name("created_at"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false), this, "");

    /**
     * The column <code>public.poker.created_by</code>.
     */
    public final TableField<PokerRecord, String> CREATED_BY = createField(DSL.name("created_by"), org.jooq.impl.SQLDataType.VARCHAR, this, "");

    /**
     * Create a <code>public.poker</code> table reference
     */
    public Poker() {
        this(DSL.name("poker"), null);
    }

    /**
     * Create an aliased <code>public.poker</code> table reference
     */
    public Poker(String alias) {
        this(DSL.name(alias), POKER);
    }

    /**
     * Create an aliased <code>public.poker</code> table reference
     */
    public Poker(Name alias) {
        this(alias, POKER);
    }

    private Poker(Name alias, Table<PokerRecord> aliased) {
        this(alias, aliased, null);
    }

    private Poker(Name alias, Table<PokerRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> Poker(Table<O> child, ForeignKey<O, PokerRecord> key) {
        super(child, key, POKER);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public Identity<PokerRecord, Long> getIdentity() {
        return Keys.IDENTITY_POKER;
    }

    @Override
    public UniqueKey<PokerRecord> getPrimaryKey() {
        return Keys.POKER_PK;
    }

    @Override
    public List<UniqueKey<PokerRecord>> getKeys() {
        return Arrays.<UniqueKey<PokerRecord>>asList(Keys.POKER_PK, Keys.ID_SECURE_UNIQUE);
    }

    @Override
    public Poker as(String alias) {
        return new Poker(DSL.name(alias), this);
    }

    @Override
    public Poker as(Name alias) {
        return new Poker(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Poker rename(String name) {
        return new Poker(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Poker rename(Name name) {
        return new Poker(name, null);
    }

    // -------------------------------------------------------------------------
    // Row5 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row5<Long, UUID, String, LocalDateTime, String> fieldsRow() {
        return (Row5) super.fieldsRow();
    }
}