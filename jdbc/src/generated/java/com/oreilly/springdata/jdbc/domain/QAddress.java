package com.oreilly.springdata.jdbc.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QAddress is a Querydsl query type for QAddress
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QAddress extends com.querydsl.sql.RelationalPathBase<QAddress> {

    private static final long serialVersionUID = 207732776;

    public static final QAddress address = new QAddress("ADDRESS");

    public final StringPath city = createString("city");

    public final StringPath country = createString("country");

    public final NumberPath<Long> customerId = createNumber("customerId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath street = createString("street");

    public final com.querydsl.sql.PrimaryKey<QAddress> sysPk10088 = createPrimaryKey(id);

    public final com.querydsl.sql.ForeignKey<QCustomer> addressCustomerRef = createForeignKey(customerId, "ID");

    public QAddress(String variable) {
        super(QAddress.class, forVariable(variable), "PUBLIC", "ADDRESS");
        addMetadata();
    }

    public QAddress(String variable, String schema, String table) {
        super(QAddress.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QAddress(String variable, String schema) {
        super(QAddress.class, forVariable(variable), schema, "ADDRESS");
        addMetadata();
    }

    public QAddress(Path<? extends QAddress> path) {
        super(path.getType(), path.getMetadata(), "PUBLIC", "ADDRESS");
        addMetadata();
    }

    public QAddress(PathMetadata metadata) {
        super(QAddress.class, metadata, "PUBLIC", "ADDRESS");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(city, ColumnMetadata.named("CITY").withIndex(4).ofType(Types.VARCHAR).withSize(255));
        addMetadata(country, ColumnMetadata.named("COUNTRY").withIndex(5).ofType(Types.VARCHAR).withSize(255));
        addMetadata(customerId, ColumnMetadata.named("CUSTOMER_ID").withIndex(2).ofType(Types.BIGINT).withSize(64));
        addMetadata(id, ColumnMetadata.named("ID").withIndex(1).ofType(Types.BIGINT).withSize(64).notNull());
        addMetadata(street, ColumnMetadata.named("STREET").withIndex(3).ofType(Types.VARCHAR).withSize(255));
    }

}

