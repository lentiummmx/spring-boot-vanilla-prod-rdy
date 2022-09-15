package mx.nuniez.xoco.spring.boot.vanilla.api.config;

import org.hibernate.dialect.PostgreSQL10Dialect;
import org.hibernate.engine.spi.QueryParameters;

import java.util.List;

public class YugabyteDialect extends PostgreSQL10Dialect {
    @Override
    public String addSqlHintOrComment(
            String sql,
            QueryParameters parameters,
            boolean commentsEnabled) {
        return super.addSqlHintOrComment(sql, parameters, commentsEnabled);
    }

    @Override
    public String getQueryHintString(String query, List<String> hintList) {
        return super.getQueryHintString(query, hintList);
    }
}

/**
 * org.hibernate.loader.Loader#preprocessSQL(java.lang.String, org.hibernate.engine.spi.QueryParameters, org.hibernate.engine.spi.SessionFactoryImplementor, java.util.List)
 * org.hibernate.dialect.Dialect#addSqlHintOrComment(java.lang.String, org.hibernate.engine.spi.QueryParameters, boolean)
 * org.hibernate.engine.jdbc.spi.SqlStatementLogger#logStatement(java.lang.String, org.hibernate.engine.jdbc.internal.Formatter)
 */
