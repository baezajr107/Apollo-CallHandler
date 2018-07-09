package com.Apollo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Apollo.Data.TradedCoin;

@Repository
public interface TradedCoinsRepository extends JpaRepository<TradedCoin,String>  {

}
