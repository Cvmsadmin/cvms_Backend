package org.ss.vendorapi.service;



import java.lang.System.Logger.Level;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;

@Service
public class CommonAppServiceImpl {
	
	@Autowired
    private EntityManagerFactory entityManagerFactory;
	
//	@Transactional(readOnly = true)
    public List<Object> findByNativeQuery(String nativeQuery) {

        jakarta.persistence.Query query = null;

        List<Object> dataObject = null;

        EntityManager session = entityManagerFactory.createEntityManager();
        try {
            if (nativeQuery != null) {
                query = session.createNativeQuery(nativeQuery);
            }
            dataObject = query.getResultList();
//            log.log(Level.INFO, "The JPA-QUERY IMPLEMENTATION ::::: " + dataObject);
            return dataObject;
        } catch (NoResultException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {

            if (session.isOpen()) session.close();
        }

        return null;

    }

	public List<Object> executeNativeQuery(String strQuery) {
		
	
		return null;
	}

}
