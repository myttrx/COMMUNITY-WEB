package com.jos.community.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.data.jpa.domain.Specification;

public class DynamicSpecifications {

	public static <T> Specification<T> bySearchFilter(final Collection<SearchFilter> filters,final Class<T> entityClazz){
		return new Specification<T>() {

			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
					CriteriaBuilder builder) {
				if (filters!=null && filters.size()>0) {
					List<Predicate> predicates = new ArrayList<Predicate>();
					for(SearchFilter filter : filters){
						if (filter.value!=null) {
							String[] names = StrUtils.split(filter.fieldName, ".");
							Path expression = root.get(names[0]);
							for(int i=1;i<names.length;i++ ){
								expression = expression.get(names[i]);
							}
							
							switch (filter.operator) {
							case EQ:
								predicates.add(builder.equal(expression, filter.value));
								break;
							case NOTEQ:
								predicates.add(builder.notEqual(expression, filter.value));
								break;	
							case LIKE:
								predicates.add(builder.like(builder.lower(expression), "%" + filter.value.toString().toLowerCase() + "%"));
								break;
							case GT:
								predicates.add(builder.greaterThan(expression, (Comparable) filter.value));
								break;
							case LT:
								predicates.add(builder.lessThan(expression, (Comparable) filter.value));
								break;
							case GTE:
								predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) filter.value));
								break;
							case LTE:
								predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) filter.value));
								break;
							case ISNULL:
								predicates.add(builder.isNull(expression));
								break;
							case ISNOTNULL:
								predicates.add(builder.isNotNull(expression));
								break;	
							case NOTIN:
								In in1=builder.in(builder.lower(expression));
								Object [] value1=null;
								if(filter.value.getClass().equals(String[].class)){
									 String[] ss=(String[])filter.value;
									 for(int i = 0;i<ss.length; i++){
										 String s = ss[i];
										 ss[i] = s.toLowerCase();
									 }
									 value1 = ss;
								}else if (filter.value.getClass().equals(Long[].class)) {
									value1=(Long[])filter.value;
								}
								
								for (int i = 0; i < value1.length; i++) {
									in1.value(value1[i]);
								}
								predicates.add(builder.not(in1));
								break;
							case IN:
								In in=builder.in(builder.lower(expression));
								Object [] value=null;
								if(filter.value.getClass().equals(String[].class)){
									 String[] ss=(String[])filter.value;
									 for(int i = 0;i<ss.length; i++){
										 String s = ss[i];
										 ss[i] = s.toLowerCase();
									 }
									 value = ss;
								}else if (filter.value.getClass().equals(Long[].class)) {
									value=(Long[])filter.value;
								}
								
								for (int i = 0; i < value.length; i++) {
									in.value(value[i]);
								}
								predicates.add(in);
								break;
							}
							
						}
					}
					if (!predicates.isEmpty()) {
						return builder.and(predicates.toArray(new Predicate[predicates.size()]));
					}
				}
				return builder.conjunction();
			}
		};
	}
}
