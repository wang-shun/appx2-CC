package com.dreawer.customer.persistence;


import com.dreawer.customer.domain.Hierarchy;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HierarchyMapper {

    List<Hierarchy> findByStoreId(String id);

    int insert(Hierarchy hierarchy);

    Hierarchy findById(String id);

    int update(Hierarchy hierarchy);

    List<String> findAllStores();
}
