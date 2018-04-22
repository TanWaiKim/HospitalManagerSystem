package cn.edu.dgut.pojo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbBackExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TbBackExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andBackTypeIsNull() {
            addCriterion("back_type is null");
            return (Criteria) this;
        }

        public Criteria andBackTypeIsNotNull() {
            addCriterion("back_type is not null");
            return (Criteria) this;
        }

        public Criteria andBackTypeEqualTo(String value) {
            addCriterion("back_type =", value, "backType");
            return (Criteria) this;
        }

        public Criteria andBackTypeNotEqualTo(String value) {
            addCriterion("back_type <>", value, "backType");
            return (Criteria) this;
        }

        public Criteria andBackTypeGreaterThan(String value) {
            addCriterion("back_type >", value, "backType");
            return (Criteria) this;
        }

        public Criteria andBackTypeGreaterThanOrEqualTo(String value) {
            addCriterion("back_type >=", value, "backType");
            return (Criteria) this;
        }

        public Criteria andBackTypeLessThan(String value) {
            addCriterion("back_type <", value, "backType");
            return (Criteria) this;
        }

        public Criteria andBackTypeLessThanOrEqualTo(String value) {
            addCriterion("back_type <=", value, "backType");
            return (Criteria) this;
        }

        public Criteria andBackTypeLike(String value) {
            addCriterion("back_type like", value, "backType");
            return (Criteria) this;
        }

        public Criteria andBackTypeNotLike(String value) {
            addCriterion("back_type not like", value, "backType");
            return (Criteria) this;
        }

        public Criteria andBackTypeIn(List<String> values) {
            addCriterion("back_type in", values, "backType");
            return (Criteria) this;
        }

        public Criteria andBackTypeNotIn(List<String> values) {
            addCriterion("back_type not in", values, "backType");
            return (Criteria) this;
        }

        public Criteria andBackTypeBetween(String value1, String value2) {
            addCriterion("back_type between", value1, value2, "backType");
            return (Criteria) this;
        }

        public Criteria andBackTypeNotBetween(String value1, String value2) {
            addCriterion("back_type not between", value1, value2, "backType");
            return (Criteria) this;
        }

        public Criteria andBackObjectIsNull() {
            addCriterion("back_object is null");
            return (Criteria) this;
        }

        public Criteria andBackObjectIsNotNull() {
            addCriterion("back_object is not null");
            return (Criteria) this;
        }

        public Criteria andBackObjectEqualTo(String value) {
            addCriterion("back_object =", value, "backObject");
            return (Criteria) this;
        }

        public Criteria andBackObjectNotEqualTo(String value) {
            addCriterion("back_object <>", value, "backObject");
            return (Criteria) this;
        }

        public Criteria andBackObjectGreaterThan(String value) {
            addCriterion("back_object >", value, "backObject");
            return (Criteria) this;
        }

        public Criteria andBackObjectGreaterThanOrEqualTo(String value) {
            addCriterion("back_object >=", value, "backObject");
            return (Criteria) this;
        }

        public Criteria andBackObjectLessThan(String value) {
            addCriterion("back_object <", value, "backObject");
            return (Criteria) this;
        }

        public Criteria andBackObjectLessThanOrEqualTo(String value) {
            addCriterion("back_object <=", value, "backObject");
            return (Criteria) this;
        }

        public Criteria andBackObjectLike(String value) {
            addCriterion("back_object like", value, "backObject");
            return (Criteria) this;
        }

        public Criteria andBackObjectNotLike(String value) {
            addCriterion("back_object not like", value, "backObject");
            return (Criteria) this;
        }

        public Criteria andBackObjectIn(List<String> values) {
            addCriterion("back_object in", values, "backObject");
            return (Criteria) this;
        }

        public Criteria andBackObjectNotIn(List<String> values) {
            addCriterion("back_object not in", values, "backObject");
            return (Criteria) this;
        }

        public Criteria andBackObjectBetween(String value1, String value2) {
            addCriterion("back_object between", value1, value2, "backObject");
            return (Criteria) this;
        }

        public Criteria andBackObjectNotBetween(String value1, String value2) {
            addCriterion("back_object not between", value1, value2, "backObject");
            return (Criteria) this;
        }

        public Criteria andDrugIdIsNull() {
            addCriterion("drug_id is null");
            return (Criteria) this;
        }

        public Criteria andDrugIdIsNotNull() {
            addCriterion("drug_id is not null");
            return (Criteria) this;
        }

        public Criteria andDrugIdEqualTo(Integer value) {
            addCriterion("drug_id =", value, "drugId");
            return (Criteria) this;
        }

        public Criteria andDrugIdNotEqualTo(Integer value) {
            addCriterion("drug_id <>", value, "drugId");
            return (Criteria) this;
        }

        public Criteria andDrugIdGreaterThan(Integer value) {
            addCriterion("drug_id >", value, "drugId");
            return (Criteria) this;
        }

        public Criteria andDrugIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("drug_id >=", value, "drugId");
            return (Criteria) this;
        }

        public Criteria andDrugIdLessThan(Integer value) {
            addCriterion("drug_id <", value, "drugId");
            return (Criteria) this;
        }

        public Criteria andDrugIdLessThanOrEqualTo(Integer value) {
            addCriterion("drug_id <=", value, "drugId");
            return (Criteria) this;
        }

        public Criteria andDrugIdIn(List<Integer> values) {
            addCriterion("drug_id in", values, "drugId");
            return (Criteria) this;
        }

        public Criteria andDrugIdNotIn(List<Integer> values) {
            addCriterion("drug_id not in", values, "drugId");
            return (Criteria) this;
        }

        public Criteria andDrugIdBetween(Integer value1, Integer value2) {
            addCriterion("drug_id between", value1, value2, "drugId");
            return (Criteria) this;
        }

        public Criteria andDrugIdNotBetween(Integer value1, Integer value2) {
            addCriterion("drug_id not between", value1, value2, "drugId");
            return (Criteria) this;
        }

        public Criteria andBatchNoIsNull() {
            addCriterion("batch_no is null");
            return (Criteria) this;
        }

        public Criteria andBatchNoIsNotNull() {
            addCriterion("batch_no is not null");
            return (Criteria) this;
        }

        public Criteria andBatchNoEqualTo(String value) {
            addCriterion("batch_no =", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotEqualTo(String value) {
            addCriterion("batch_no <>", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoGreaterThan(String value) {
            addCriterion("batch_no >", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoGreaterThanOrEqualTo(String value) {
            addCriterion("batch_no >=", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLessThan(String value) {
            addCriterion("batch_no <", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLessThanOrEqualTo(String value) {
            addCriterion("batch_no <=", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLike(String value) {
            addCriterion("batch_no like", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotLike(String value) {
            addCriterion("batch_no not like", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoIn(List<String> values) {
            addCriterion("batch_no in", values, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotIn(List<String> values) {
            addCriterion("batch_no not in", values, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoBetween(String value1, String value2) {
            addCriterion("batch_no between", value1, value2, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotBetween(String value1, String value2) {
            addCriterion("batch_no not between", value1, value2, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBackSumIsNull() {
            addCriterion("back_sum is null");
            return (Criteria) this;
        }

        public Criteria andBackSumIsNotNull() {
            addCriterion("back_sum is not null");
            return (Criteria) this;
        }

        public Criteria andBackSumEqualTo(Integer value) {
            addCriterion("back_sum =", value, "backSum");
            return (Criteria) this;
        }

        public Criteria andBackSumNotEqualTo(Integer value) {
            addCriterion("back_sum <>", value, "backSum");
            return (Criteria) this;
        }

        public Criteria andBackSumGreaterThan(Integer value) {
            addCriterion("back_sum >", value, "backSum");
            return (Criteria) this;
        }

        public Criteria andBackSumGreaterThanOrEqualTo(Integer value) {
            addCriterion("back_sum >=", value, "backSum");
            return (Criteria) this;
        }

        public Criteria andBackSumLessThan(Integer value) {
            addCriterion("back_sum <", value, "backSum");
            return (Criteria) this;
        }

        public Criteria andBackSumLessThanOrEqualTo(Integer value) {
            addCriterion("back_sum <=", value, "backSum");
            return (Criteria) this;
        }

        public Criteria andBackSumIn(List<Integer> values) {
            addCriterion("back_sum in", values, "backSum");
            return (Criteria) this;
        }

        public Criteria andBackSumNotIn(List<Integer> values) {
            addCriterion("back_sum not in", values, "backSum");
            return (Criteria) this;
        }

        public Criteria andBackSumBetween(Integer value1, Integer value2) {
            addCriterion("back_sum between", value1, value2, "backSum");
            return (Criteria) this;
        }

        public Criteria andBackSumNotBetween(Integer value1, Integer value2) {
            addCriterion("back_sum not between", value1, value2, "backSum");
            return (Criteria) this;
        }

        public Criteria andBackTotalPriceIsNull() {
            addCriterion("back_total_price is null");
            return (Criteria) this;
        }

        public Criteria andBackTotalPriceIsNotNull() {
            addCriterion("back_total_price is not null");
            return (Criteria) this;
        }

        public Criteria andBackTotalPriceEqualTo(BigDecimal value) {
            addCriterion("back_total_price =", value, "backTotalPrice");
            return (Criteria) this;
        }

        public Criteria andBackTotalPriceNotEqualTo(BigDecimal value) {
            addCriterion("back_total_price <>", value, "backTotalPrice");
            return (Criteria) this;
        }

        public Criteria andBackTotalPriceGreaterThan(BigDecimal value) {
            addCriterion("back_total_price >", value, "backTotalPrice");
            return (Criteria) this;
        }

        public Criteria andBackTotalPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("back_total_price >=", value, "backTotalPrice");
            return (Criteria) this;
        }

        public Criteria andBackTotalPriceLessThan(BigDecimal value) {
            addCriterion("back_total_price <", value, "backTotalPrice");
            return (Criteria) this;
        }

        public Criteria andBackTotalPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("back_total_price <=", value, "backTotalPrice");
            return (Criteria) this;
        }

        public Criteria andBackTotalPriceIn(List<BigDecimal> values) {
            addCriterion("back_total_price in", values, "backTotalPrice");
            return (Criteria) this;
        }

        public Criteria andBackTotalPriceNotIn(List<BigDecimal> values) {
            addCriterion("back_total_price not in", values, "backTotalPrice");
            return (Criteria) this;
        }

        public Criteria andBackTotalPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("back_total_price between", value1, value2, "backTotalPrice");
            return (Criteria) this;
        }

        public Criteria andBackTotalPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("back_total_price not between", value1, value2, "backTotalPrice");
            return (Criteria) this;
        }

        public Criteria andBackReasonIsNull() {
            addCriterion("back_reason is null");
            return (Criteria) this;
        }

        public Criteria andBackReasonIsNotNull() {
            addCriterion("back_reason is not null");
            return (Criteria) this;
        }

        public Criteria andBackReasonEqualTo(String value) {
            addCriterion("back_reason =", value, "backReason");
            return (Criteria) this;
        }

        public Criteria andBackReasonNotEqualTo(String value) {
            addCriterion("back_reason <>", value, "backReason");
            return (Criteria) this;
        }

        public Criteria andBackReasonGreaterThan(String value) {
            addCriterion("back_reason >", value, "backReason");
            return (Criteria) this;
        }

        public Criteria andBackReasonGreaterThanOrEqualTo(String value) {
            addCriterion("back_reason >=", value, "backReason");
            return (Criteria) this;
        }

        public Criteria andBackReasonLessThan(String value) {
            addCriterion("back_reason <", value, "backReason");
            return (Criteria) this;
        }

        public Criteria andBackReasonLessThanOrEqualTo(String value) {
            addCriterion("back_reason <=", value, "backReason");
            return (Criteria) this;
        }

        public Criteria andBackReasonLike(String value) {
            addCriterion("back_reason like", value, "backReason");
            return (Criteria) this;
        }

        public Criteria andBackReasonNotLike(String value) {
            addCriterion("back_reason not like", value, "backReason");
            return (Criteria) this;
        }

        public Criteria andBackReasonIn(List<String> values) {
            addCriterion("back_reason in", values, "backReason");
            return (Criteria) this;
        }

        public Criteria andBackReasonNotIn(List<String> values) {
            addCriterion("back_reason not in", values, "backReason");
            return (Criteria) this;
        }

        public Criteria andBackReasonBetween(String value1, String value2) {
            addCriterion("back_reason between", value1, value2, "backReason");
            return (Criteria) this;
        }

        public Criteria andBackReasonNotBetween(String value1, String value2) {
            addCriterion("back_reason not between", value1, value2, "backReason");
            return (Criteria) this;
        }

        public Criteria andOperatorIsNull() {
            addCriterion("operator is null");
            return (Criteria) this;
        }

        public Criteria andOperatorIsNotNull() {
            addCriterion("operator is not null");
            return (Criteria) this;
        }

        public Criteria andOperatorEqualTo(String value) {
            addCriterion("operator =", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotEqualTo(String value) {
            addCriterion("operator <>", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThan(String value) {
            addCriterion("operator >", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThanOrEqualTo(String value) {
            addCriterion("operator >=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThan(String value) {
            addCriterion("operator <", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThanOrEqualTo(String value) {
            addCriterion("operator <=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLike(String value) {
            addCriterion("operator like", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotLike(String value) {
            addCriterion("operator not like", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorIn(List<String> values) {
            addCriterion("operator in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotIn(List<String> values) {
            addCriterion("operator not in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorBetween(String value1, String value2) {
            addCriterion("operator between", value1, value2, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotBetween(String value1, String value2) {
            addCriterion("operator not between", value1, value2, "operator");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}