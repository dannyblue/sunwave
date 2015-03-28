package com.sunwave.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sunwave.app.model.SlArea;
import com.sunwave.app.model.SysUser;

public interface LoginDao extends CrudRepository<SysUser, Long>,
		JpaSpecificationExecutor<SysUser> {
	
	@Query("select o from SlArea o where o.slArea.areaId=? or o.areaId=? and o.areaGrade<3 order by o.areaGrade,o.areaOrder asc")
	public List<SlArea> getAreasByParentId(Integer parentId,Integer areaId);
	
}
