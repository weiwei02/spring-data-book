package com.oreilly.springdata.jdbc.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QCustomer is a Querydsl query type for QCustomer
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QCustomer extends com.querydsl.sql.RelationalPathBase<QCustomer> {

    private static final long serialVersionUID = -325328342;

    public static final QCustomer customer = new QCustomer("CUSTOMER");

    public final StringPath emailAddress = createString("emailAddress");

    public final StringPath firstName = createString("firstName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastName = createString("lastName");

    public final com.querydsl.sql.PrimaryKey<QCustomer> sysPk10086 = createPrimaryKey(id);

    public final com.querydsl.sql.ForeignKey<QAddress> _addressCustomerRef = createInvForeignKey(id, "CUSTOMER_ID");

    public QCustomer(String variable) {
        super(QCustomer.class, forVariable(variable), "PUBLIC", "CUSTOMER");
        addMetadata();
    }

    public QCustomer(String variable, String schema, String table) {
        super(QCustomer.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QCustomer(String variable, String schema) {
        super(QCustomer.class, forVariable(variable), schema, "CUSTOMER");
        addMetadata();
    }

    public QCustomer(Path<? extends QCustomer> path) {
        super(path.getType(), path.getMetadata(), "PUBLIC", "CUSTOMER");
        addMetadata();
    }

    public QCustomer(PathMetadata metadata) {
        super(QCustomer.class, metadata, "PUBLIC", "CUSTOMER");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(emailAddress, ColumnMetadata.named("EMAIL_ADDRESS").withIndex(4).ofType(Types.VARCHAR).withSize(255));
        addMetadata(firstName, ColumnMetadata.named("FIRST_NAME").withIndex(2).ofType(Types.VARCHAR).withSize(255));
        addMetadata(id, ColumnMetadata.named("ID").withIndex(1).ofType(Types.BIGINT).withSize(64).notNull());
        addMetadata(lastName, ColumnMetadata.named("LAST_NAME").withIndex(3).ofType(Types.VARCHAR).withSize(255));
    }

}

