//package org.ss.vendorapi.service;
//
//
//
//import java.lang.System.Logger.Level;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.NoResultException;
//import jakarta.persistence.Query;
//import jakarta.transaction.Transactional;
//
//
//@Service
//public class CommonAppServiceImpl {
//	
//	@Autowired 
//	private EntityManager em;  
//	
//	@Autowired
//    private EntityManagerFactory entityManagerFactory;
//	
////	@Transactional(readOnly = true)
//    public List<Object> findByNativeQuery(String nativeQuery) {
//
//        jakarta.persistence.Query query = null;
//
//        List<Object> dataObject = null;
//
//        EntityManager session = entityManagerFactory.createEntityManager();
//        try {
//            if (nativeQuery != null) {
//                query = session.createNativeQuery(nativeQuery);
//            }
//            dataObject = query.getResultList();
////            log.log(Level.INFO, "The JPA-QUERY IMPLEMENTATION ::::: " + dataObject);
//            return dataObject;
//        } catch (NoResultException ex) {
//            ex.printStackTrace();
//        } catch (Exception ex) {
//
//            ex.printStackTrace();
//
//        } finally {
//
//            if (session.isOpen()) session.close();
//        }
//
//        return null;
//
//    }
//    
//    public List<Object[]> executeNativeQuery(String lookupSQL) {
//        List<Object[]> resultList = null;
//
//        try {
//            resultList = em.createNativeQuery(lookupSQL).getResultList();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return resultList;
//    }
//    
//    
//    public int updateQuery(String queryStr) {
////        log.log(Level.INFO, "@@ UPDATE Query :: " + queryStr);
//
//        int x = 0;
//        try {
//            Query query = em.createNativeQuery(queryStr);
//            x = query.executeUpdate();
////            log.log(Level.INFO, "@@ Update Query Completed @@ :: " + x);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return x;
//    }
//    
//    private int parseInteger(String value) {
//        if (value != null && value.matches("-?\\d+")) {  // Check if the value is a valid integer
//            return Integer.parseInt(value);
//        } else {
//            return 0;  // Default value if the value is not a valid number
//        }
//    }    
//}
//
//
//	
//
