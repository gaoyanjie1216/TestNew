package com.gyj.Solr.Utils;

/**
 * Created by Gao on 2018/1/3.
 */

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.FacetFieldEntry;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolrHelper {

    public static final String HIGHLIGHT_TAG = "red";

    /**
     * 新建 查询条件
     *
     * @param fieldName 字段名
     * @return
     */
    public static Criteria criteria(String fieldName) {
        return criteria(fieldName, Float.NaN);
    }

    /**
     * 新建 查询条件
     *
     * @param fieldName 字段名
     * @param boost     权重
     * @return
     */
    public static Criteria criteria(String fieldName, float boost) {
        return new Criteria(fieldName).boost(boost);
    }

    /**
     * 新建 查询条件
     *
     * @param searchWords 检索词
     * @param fields      检索字段及权重，分隔符"^"
     * @param values      检索词及权重，分隔符"^"
     */
    public static Criteria criteria(String searchWords, List<String> fields, List<String> values) {
        Criteria criteria = null;
        for (String field : fields) {
            String[] fieldArr = field.split("\\^");
            // 字段名
            String fieldName = fieldArr[0];
            // 字段权重
            Float boost = fieldArr.length == 2 ? NumberUtils.toFloat(fieldArr[1], Float.NaN) : Float.NaN;

            if (criteria == null) {
                criteria = new Criteria(fieldName).boost(boost).is(searchWords);
            } else {
                criteria = criteria.or(fieldName).boost(boost).is(searchWords);
            }
            if (values != null) {
                for (String value : values) {
                    String[] valueArr = value.split("\\^");
                    String val = valueArr[0];
                    Float valBoost = valueArr.length == 2 ? NumberUtils.toFloat(valueArr[1], Float.NaN) : Float.NaN;
                    criteria = criteria.or(fieldName).boost((boost.isNaN() ? 1 : boost) * valBoost).is(val);
                }
            }
        }
        return criteria;
    }

    /**
     * FilterQuery 对象
     *
     * @param criteria
     * @return
     */
    public static FilterQuery filterQuery(Criteria criteria) {
        return new SimpleFilterQuery(criteria);
    }

    /**
     * FilterQuery 对象
     *
     * @param filterString
     * @return
     */
    public static FilterQuery filterQuery(String filterString) {
        return new SimpleFilterQuery(new SimpleStringCriteria(filterString));
    }

    /**
     * FacetQuery 对象
     *
     * @param query
     * @return
     */
    public static FacetQuery facetQuery(Query query) {
        return SimpleQuery.fromQuery(query, new SimpleFacetQuery());
    }

    /**
     * HighlightQuery 对象
     *
     * @param query
     * @return
     */
    public static HighlightQuery highlightQuery(Query query) {
        return SimpleQuery.fromQuery(query, new SimpleHighlightQuery());
    }

    /**
     * 新建 Facet查询设置
     *
     * @return
     */
    public static FacetOptions facetOptions() {
        FacetOptions options = new FacetOptions();
        options.setPageable(new PageRequest(0, 10000000));
        return options;
    }

    /**
     * FacetPage 转 聚类统计Map
     *
     * @param facetPage
     * @param facetQuery
     * @return
     */
    public static Map<String, Map<Object, Long>> toCounts(FacetPage<?> facetPage, FacetQuery facetQuery) {
        Map<String, Map<Object, Long>> counts = new HashMap<>();
        // Field
        for (Field field : facetQuery.getFacetOptions().getFacetOnFields()) {
            Map<Object, Long> count = new HashMap<>();
            for (FacetFieldEntry entry : facetPage.getFacetResultPage(field)) {
                count.put(entry.getValue(), entry.getValueCount());
            }
            counts.put(field.getName(), count);
        }
        // Pivot
        for (PivotField field : facetQuery.getFacetOptions().getFacetOnPivots()) {
            Map<Object, Long> count = new HashMap<>();
            for (FacetFieldEntry entry : facetPage.getPivot(field)) {
                count.put(entry.getValue(), entry.getValueCount());
            }
            counts.put(field.getName(), count);
        }
        // Range
        for (Field field : facetQuery.getFacetOptions().getFieldsWithRangeParameters()) {
            Map<Object, Long> count = new HashMap<>();
            for (FacetFieldEntry entry : facetPage.getRangeFacetResultPage(field)) {
                count.put(entry.getValue(), entry.getValueCount());
            }
            counts.put(field.getName(), count);
        }
        return counts;
    }

    /**
     * 新建 高亮查询设置
     *
     * @return
     */
    public static HighlightOptions highlightOptions() {
        String prefix = "<" + HIGHLIGHT_TAG + " style=\"font-weight: bolder; color: #e3242e;\">";
        String postfix = "</" + HIGHLIGHT_TAG + ">";
        HighlightOptions options = new HighlightOptions();
        options.setSimplePrefix(prefix).setSimplePostfix(postfix);
        return options;
    }

    /**
     * HighlightPage 转 Page
     *
     * @param highlightPage
     * @param pageable
     * @param <T>
     * @return
     */
    @Deprecated
    private static <T> Page<T> toPage(HighlightPage<T> highlightPage, Pageable pageable) {

        for (T t : highlightPage.getContent()) {
            for (HighlightEntry.Highlight highlight : highlightPage.getHighlights(t)) {
                String field = highlight.getField().getName();
                if (!"DOC_ALLTEXT".equals(field)) {
                    field = field.substring(0, field.lastIndexOf("_"));
                }

                String value = highlight.getSnipplets().get(0);
                try {
                    FieldUtils.writeField(t, field, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return new PageImpl(highlightPage.getContent(), pageable, highlightPage.getTotalElements());
    }

    /**
     * HighlightPage 转 Page
     *
     * @param highlightPage
     * @param pageable
     * @param <T>
     * @return
     */
    public static <T> Page<T> toPageBySetMethod(HighlightPage<T> highlightPage, Pageable pageable) {
        for (T t : highlightPage.getContent()) {
            for (HighlightEntry.Highlight highlight : highlightPage.getHighlights(t)) {
                String field = highlight.getField().getName();
                if (!"DOC_ALLTEXT".equals(field)) {
                    field = field.substring(0, field.lastIndexOf("_"));
                }
                String setMethodName = "set" + field;

                Object value = highlight.getSnipplets().get(0);
                try {
                    MethodUtils.invokeMethod(t, setMethodName, value);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
        return new PageImpl(highlightPage.getContent(), pageable, highlightPage.getTotalElements());
    }

}
