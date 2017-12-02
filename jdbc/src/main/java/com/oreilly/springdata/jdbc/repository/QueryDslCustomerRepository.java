package com.oreilly.springdata.jdbc.repository;

import com.oreilly.springdata.jdbc.domain.*;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.sql.SQLQueryFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 */
@Repository
@Transactional
public class QueryDslCustomerRepository implements CustomerRepository, ApplicationContextAware {

	private final QCustomer qCustomer = QCustomer.customer;

	private final QAddress qAddress = QAddress.address;


	private Path[] customerAddressProjection = new Path[] { qCustomer.id, qCustomer.firstName, qCustomer.lastName,
            qCustomer.emailAddress, qAddress.id, qAddress.customerId, qAddress.street, qAddress.city, qAddress.country };
	@Resource
	private SQLQueryFactory queryFactory;
    private ApplicationContext applicationContext;


    @Override
	@Transactional(readOnly = true)
	public Customer findById(Long id) {
		if (id == null) {
			return null;
		}
		return findOne(qCustomer.id.eq(id));
	}

	@Override

	public List<Customer> findAll() {
        applicationContext.getBeanDefinitionNames();
        List<Tuple> tupleList= queryFactory.select(customerAddressProjection).from(qCustomer)
				.leftJoin(qCustomer._addressCustomerRef, qAddress).orderBy(new OrderSpecifier<Long>(Order.DESC, qCustomer.id)).fetch();
        List<Customer> customerList = new ArrayList<Customer>();
        if (CollectionUtils.isEmpty(tupleList)) {
            return customerList;
        }
        // 第一遍生成所有顾客与地址集合
        for (Tuple tuple : tupleList){
            Customer customer = tupleToCustomer(tuple);
            if (!customerList.contains(customer)){
                for (Tuple addressTuple : tupleList){
                    if (tuple.get(qCustomer.id).equals(addressTuple.get(qCustomer.id))){
                        Address address = tupleToAddress(addressTuple);
                        customer.addAddress(address);
                    }else {
                        break;
                    }
                }
                customerList.add(customer);
            }
        }
		return customerList;
	}


	/**
     * convert tuple data to Address
     * */
    private Address tupleToAddress(Tuple addressTuple) {
        Address address = new Address();
        address.setCity(addressTuple.get(qAddress.city));
        address.setId(addressTuple.get(qAddress.id));
        address.setCountry(addressTuple.get(qAddress.country));
        address.setStreet(addressTuple.get(qAddress.street));
        return address;
    }


    /**
     * 从tuple中取出customer对象
     * */
    private Customer tupleToCustomer(Tuple tuple) {
        Customer customer = new Customer();
        customer.setId(tuple.get(qCustomer.id));
        customer.setFirstName(tuple.get(qCustomer.firstName));
        customer.setLastName(tuple.get(qCustomer.lastName));
        return customer;
    }

    @Override
	@Transactional(readOnly = true)
	public Customer findByEmailAddress(EmailAddress emailAddress) {
		if (emailAddress == null) {
			return null;
		}
		return findOne(qCustomer.emailAddress.eq(emailAddress.toString()));
	}

    private Customer findOne(Predicate predicate) {
        List<Tuple> tupleList = queryFactory.query().select(customerAddressProjection).from(qCustomer)
                .leftJoin(qCustomer._addressCustomerRef, qAddress).where(predicate).fetch();
        if (CollectionUtils.isEmpty(tupleList)) {
            return null;
        }
        Customer customer = tupleToCustomer(tupleList.get(0));
        for (Tuple addressTuple : tupleList) {
            if (customer.getId().equals(addressTuple.get(qCustomer.id))) {
                Address address = tupleToAddress(addressTuple);
                customer.addAddress(address);
            } else {
                break;
            }

        }
        return customer;
    }

	@Override
	public void save(final Customer customer) {
		if (customer.getId() == null) {
            Long generatedKey = queryFactory.insert(qCustomer).columns(qCustomer.firstName, qCustomer.lastName, qCustomer.emailAddress).values(customer.getFirstName(), customer.getLastName(), customer.getEmailAddress()).execute();
            customer.setId(generatedKey);
        }else {
            queryFactory.update(qCustomer).populate(customer).execute();
        }
	}

	@Override
	public void delete(final Customer customer) {
		queryFactory.delete(qCustomer).where(qCustomer.id.eq(customer.getId()));
	}

	private static String columnLabel(Path<?> path) {
		return path.toString();
	}

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
