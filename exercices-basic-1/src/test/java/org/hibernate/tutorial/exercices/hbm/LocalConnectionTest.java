package org.hibernate.tutorial.exercices.hbm;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.tutorial.exercices.util.HibernateUtil;

import junit.framework.TestCase;

public class LocalConnectionTest extends TestCase {
	
	private SessionFactory sessionFactory;
	
	@Override
	protected void setUp() throws Exception {
		// A SessionFactory is set up once for an application
        sessionFactory = HibernateUtil.getSession();
	}

	@Override
	protected void tearDown() throws Exception {
		if ( sessionFactory != null ) {
			sessionFactory.close();
		}
	}

	public void testBasicUsage() {
		// create a couple of events...
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		OperationModel om = new OperationModel( new Date() );
		om.setOperationTypeModel(new OperationTypeModel("LEASING"));
		session.save( om );
		session.getTransaction().commit();
		session.close();

		// now lets pull events from the database and list them
		session = sessionFactory.openSession();
        session.beginTransaction();
        List result = session.createQuery( "from OperationModel" ).list();
		for ( OperationModel omtemp : (List<OperationModel>) result ) {
			System.out.println( "OperationModel (" + omtemp.getDate() + ") : " + omtemp.getId() + 
					"\t OperationType Id:" + omtemp.getOperationTypeModel().getIdOperationType() +
					" OperationType description:" +omtemp.getOperationTypeModel().getDescription() );
		}
        session.getTransaction().commit();
        session.close();
	}

}
