package persistence.sql.dml.query.builder;

import domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dml.conditional.Criteria;
import persistence.sql.dml.conditional.Criterion;
import persistence.sql.dml.query.clause.ColumnClause;
import persistence.sql.dml.query.clause.WhereClause;
import persistence.sql.entity.EntityMappingTable;
import persistence.sql.entity.model.DomainType;
import persistence.sql.entity.model.TableName;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class SelectQueryBuilderTest {

    private EntityMappingTable entityMappingTable;
    private SelectQueryBuilder selectQueryBuilder;

    @BeforeEach
    void setUp() {
        this.entityMappingTable = EntityMappingTable.from(Person.class);
        this.selectQueryBuilder = SelectQueryBuilder.getInstance();
    }

    @DisplayName("조건문 없는 SELECT문을 반환한다.")
    @Test
    void sqlTest() {
        TableName tableName = entityMappingTable.getTableName();
        ColumnClause columnClause = new ColumnClause(entityMappingTable.getDomainTypes().getColumnName());
        WhereClause whereClause = new WhereClause(Criteria.emptyInstance());

        assertThat(selectQueryBuilder.toSql(tableName, columnClause, whereClause)).isEqualTo("SELECT id,\n" +
                "nick_name,\n" +
                "old,\n" +
                "email \n" +
                " FROM Person \n" +
                " ");
    }

    @DisplayName("조건문 있는 SELECT문을 반환한다.")
    @Test
    void whereSqlTest() {
        TableName tableName = entityMappingTable.getTableName();
        ColumnClause columnClause = new ColumnClause(entityMappingTable.getDomainTypes().getColumnName());
        DomainType domainType = entityMappingTable.getPkDomainTypes();
        Criteria criteria = Criteria.ofCriteria(Collections.singletonList(Criterion.of(domainType.getColumnName(), "1")));
        WhereClause whereClause = new WhereClause(criteria);

        assertThat(selectQueryBuilder.toSql(tableName, columnClause, whereClause)).isEqualTo("SELECT id,\n" +
                "nick_name,\n" +
                "old,\n" +
                "email \n" +
                " FROM Person \n" +
                " WHERE id='1'");
    }

}
