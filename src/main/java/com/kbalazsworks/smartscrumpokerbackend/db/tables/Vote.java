/*
 * This file is generated by jOOQ.
 */
package com.kbalazsworks.smartscrumpokerbackend.db.tables;


import com.kbalazsworks.smartscrumpokerbackend.db.Keys;
import com.kbalazsworks.smartscrumpokerbackend.db.Public;
import com.kbalazsworks.smartscrumpokerbackend.db.tables.records.VoteRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row7;
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
public class Vote extends TableImpl<VoteRecord> {

    private static final long serialVersionUID = 1618338359;

    /**
     * The reference instance of <code>public.vote</code>
     */
    public static final Vote VOTE = new Vote();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<VoteRecord> getRecordType() {
        return VoteRecord.class;
    }

    /**
     * The column <code>public.vote.id</code>.
     */
    public final TableField<VoteRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.vote.uncertainty</code>.
     */
    public final TableField<VoteRecord, Short> UNCERTAINTY = createField(DSL.name("uncertainty"), org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>public.vote.complexity</code>.
     */
    public final TableField<VoteRecord, Short> COMPLEXITY = createField(DSL.name("complexity"), org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>public.vote.effort</code>.
     */
    public final TableField<VoteRecord, Short> EFFORT = createField(DSL.name("effort"), org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>public.vote.calculated_point</code>.
     */
    public final TableField<VoteRecord, Short> CALCULATED_POINT = createField(DSL.name("calculated_point"), org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>public.vote.created_at</code>.
     */
    public final TableField<VoteRecord, LocalDateTime> CREATED_AT = createField(DSL.name("created_at"), org.jooq.impl.SQLDataType.LOCALDATETIME.nullable(false), this, "");

    /**
     * The column <code>public.vote.created_by</code>.
     */
    public final TableField<VoteRecord, String> CREATED_BY = createField(DSL.name("created_by"), org.jooq.impl.SQLDataType.VARCHAR, this, "");

    /**
     * Create a <code>public.vote</code> table reference
     */
    public Vote() {
        this(DSL.name("vote"), null);
    }

    /**
     * Create an aliased <code>public.vote</code> table reference
     */
    public Vote(String alias) {
        this(DSL.name(alias), VOTE);
    }

    /**
     * Create an aliased <code>public.vote</code> table reference
     */
    public Vote(Name alias) {
        this(alias, VOTE);
    }

    private Vote(Name alias, Table<VoteRecord> aliased) {
        this(alias, aliased, null);
    }

    private Vote(Name alias, Table<VoteRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> Vote(Table<O> child, ForeignKey<O, VoteRecord> key) {
        super(child, key, VOTE);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public Identity<VoteRecord, Long> getIdentity() {
        return Keys.IDENTITY_VOTE;
    }

    @Override
    public UniqueKey<VoteRecord> getPrimaryKey() {
        return Keys.VOTE_PK;
    }

    @Override
    public List<UniqueKey<VoteRecord>> getKeys() {
        return Arrays.<UniqueKey<VoteRecord>>asList(Keys.VOTE_PK);
    }

    @Override
    public Vote as(String alias) {
        return new Vote(DSL.name(alias), this);
    }

    @Override
    public Vote as(Name alias) {
        return new Vote(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Vote rename(String name) {
        return new Vote(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Vote rename(Name name) {
        return new Vote(name, null);
    }

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row7<Long, Short, Short, Short, Short, LocalDateTime, String> fieldsRow() {
        return (Row7) super.fieldsRow();
    }
}
