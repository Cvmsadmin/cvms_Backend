package org.ss.vendorapi.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.SqlExecutorEntity;
import org.ss.vendorapi.repository.SqlExecutorRepository;

@Service
public class SqlExecutorService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private SqlExecutorRepository sqlExecutorRepository;

    public List<Map<String, Object>> executeQueryAndSave(String reportName, String reportQuery) {
        // Optionally validate or restrict query here
        if (!isSelectQuery(reportQuery)) {
            throw new RuntimeException("Only SELECT queries are allowed.");
        }
        
        SqlExecutorEntity entity = new SqlExecutorEntity();
        entity.setReportName(reportName);
        entity.setReportQuery(reportQuery);
        sqlExecutorRepository.save(entity);
        
        return jdbcTemplate.queryForList(reportQuery);
    }

    private boolean isSelectQuery(String reportQuery) {
        return reportQuery != null && reportQuery.trim().toLowerCase().startsWith("select");
    }

    public List<SqlExecutorEntity> getAllReports() {
        return sqlExecutorRepository.findAll();
    }

}
