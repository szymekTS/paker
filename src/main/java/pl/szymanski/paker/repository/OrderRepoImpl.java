package pl.szymanski.paker.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import pl.szymanski.paker.models.Order;

import java.util.List;

public class OrderRepoImpl  {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Order> findByStatusAndLocalization(String status, String localization){
        Query query = new Query();
        query.addCriteria(
                Criteria.where("lastStatus").is(status).
                        andOperator(
                                Criteria.where("localization").is(localization)
                        )
        );
        return mongoTemplate.find(query, Order.class);
    }
}
