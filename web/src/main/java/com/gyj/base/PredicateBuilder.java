package com.gyj.base;

/**
 * Created by Gao on 2017/12/19.
 */

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EntityPathBase;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PredicateBuilder<Q extends EntityPathBase> {

    public interface IPredicateValue<T> {
        boolean isBlank(T val);
    }

    public interface IPredicateExpression<Q extends EntityPathBase, T> {
        BooleanExpression expression(Q path, T val) throws Exception;
    }

    public interface IDateExpression<Q extends EntityPathBase, D extends Date> {
        SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
        Long ONE_DAY = 1 * 24 * 60 * 60 * 1000L;
        BooleanExpression expression(Q path, D date1, D date2) throws Exception;
    }

    private boolean isBlank(String val) {
        return StringUtils.isBlank(val) || "null".equals(val);
    }

    private boolean isBlank(Number number) {
        return null == number;
    }

    private Q path;

    private Predicate predicate = null;

    public PredicateBuilder(Q path) {
        this.path = path;
    }

    public PredicateBuilder<Q> add(String value, IPredicateExpression<Q, String> predicateExpression) {
        return this.add(value, val -> this.isBlank(val), predicateExpression);
    }

    public PredicateBuilder<Q> addDate(String value1, String value2, IDateExpression<Q, Timestamp> dateExpression,
                                       IPredicateExpression<Q, Timestamp> predicateExpression1,
                                       IPredicateExpression<Q, Timestamp> predicateExpression2) {
        if (!this.isBlank(value1)) {
            if (!this.isBlank(value2)) {
                return this.addDate(value1, value2, dateExpression);
            }
            return this.add(value1, (path1, val) -> {
                Timestamp date = new Timestamp(IDateExpression.FORMATTER.parse(val).getTime());
                return predicateExpression1.expression(path1, date);
            });
        }
        return this.add(value2, (path1, val) -> {
            Timestamp date = new Timestamp(IDateExpression.FORMATTER.parse(val).getTime());
            return predicateExpression2.expression(path1, date);
        });
    }

    public PredicateBuilder<Q> addDate(String value1, String value2, IDateExpression<Q, Timestamp> dateExpression) {
        try {
            if (!this.isBlank(value1) && !this.isBlank(value2)) {
                Timestamp date1 = new Timestamp(IDateExpression.FORMATTER.parse(value1).getTime());
                Timestamp date2 = new Timestamp(IDateExpression.FORMATTER.parse(value2).getTime());
                predicate = dateExpression.expression(this.path, date1, date2).and(predicate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return this;
        }
    }

    public <T> PredicateBuilder<Q> addObj(T value, IPredicateExpression<Q, T> predicateExpression) {
        return this.add(value, val -> {
            if (val instanceof String) {
                return this.isBlank((String) val);
            } else if (val instanceof Number) {
                return this.isBlank((Number) val);
            } else {
                return val == null;
            }
        }, predicateExpression);
    }

    public <N extends Number> PredicateBuilder<Q> add(N value, IPredicateExpression<Q, N> predicateExpression) {
        return this.add(value, val -> this.isBlank(val), predicateExpression);
    }

    private  <T> PredicateBuilder<Q> add(T value, IPredicateValue<T> predicateValue, IPredicateExpression<Q, T> predicateExpression) {
        if (!predicateValue.isBlank(value)) {
            if (value instanceof String) {
                value = (T)((String) value).trim();
            }
            try {
                predicate = predicateExpression.expression(this.path, value).and(predicate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    public Predicate build() {
        return predicate;
    }
}
