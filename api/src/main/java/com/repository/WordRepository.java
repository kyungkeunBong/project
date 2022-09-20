package com.repository;

import com.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Keyword, Long>{
	@Query(value = 
			"select keyword, count(keyword) as count" 
			+ " from word" 
			+ " group by keyword" 
			+ " order by count desc limit 10"
			, nativeQuery = true
			)
	List<KeywordTopten> findToptenKeyword();
	
	@Transactional
	@Modifying
	@Query(value = 
			"delete" 
			+ " from word" 
			+ " where partitioncode = :partitioncode" 			          
			, nativeQuery = true
			)
	void deleteByPartitionCode(@Param("partitioncode")String partitioncode);
}
