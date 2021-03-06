package com.DaoImpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.Dao.ProductDao;
import com.model.Product;
import java.sql.Blob;
import org.hibernate.Hibernate;

@Repository("productDaoImpl")
@Service
public class ProductDaoImpl implements ProductDao {

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    public ProductDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void insertProduct(Product product) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(product);
        session.getTransaction().commit();

    }

    public Blob insertImage(byte[] image) {
        Session session = sessionFactory.openSession();

        Blob blob = Hibernate.getLobCreator(session).createBlob(image);
        return blob;
    }

    @SuppressWarnings("unchecked")
    public List<Product> retrieve() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Product> li = session.createQuery("from Product").list();
        session.getTransaction().commit();;
        return li;
    }

    public Product findByProdId(int pid) {
        Session session = sessionFactory.openSession();
        Product p = null;
        try {
            session.beginTransaction();
            p = (Product) session.get(Product.class, pid);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.getMessage();
        }
        return p;
    }

    public Product findByProdName(String pname) {
        Session session = sessionFactory.openSession();
        Product p = null;
        try {
            session.beginTransaction();
            p = (Product) session.get(Product.class, pname);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.getMessage();
        }
        return p;
    }

    @SuppressWarnings("unchecked")
    public List<Product> getProdByCatId(int cid) {
        Session session = sessionFactory.openSession();
        List<Product> prod = null;
        try {
            session.beginTransaction();
            prod = session.createQuery("from Product where cid=" + cid).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return prod;
    }

    @SuppressWarnings("unchecked")
    public List<Product> getProdBySupId(int sid) {
        Session session = sessionFactory.openSession();
        List<Product> prod = null;
        try {
            session.beginTransaction();
            prod = session.createQuery("from Product where sid=" + sid).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return prod;
    }

    public void update(Product p) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(p);
        session.getTransaction().commit();
    }

    public void deleteProd(int pid) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Product p = (Product) session.get(Product.class, pid);
        session.delete(p);
        session.getTransaction().commit();
    }

    //changes
//	public int findStockByProdId(String cartname){
//		Session session = sessionFactory.openSession();
//		int stk = 0;
//		try{
//			session.beginTransaction();
//			Query query = session.createQuery("select Stock from Product where name="+cartname);
//			session.getTransaction().commit();
//		}
//		 catch (HibernateException e) {
//				e.printStackTrace();
//				session.getTransaction().rollback();
//		}
//		return (String)query.uniqueResult();
//	
//		
//	}
    @SuppressWarnings("unchecked")
    public List<Product> findByProdNameFrSearch(String pname) {
        Session session = sessionFactory.openSession();
        List<Product> p = null;
        try {
            session.beginTransaction();
            p = session.createQuery("from Product where lower(name) LIKE '%" + pname + "%'").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.getMessage();
            session.getTransaction().rollback();
        }
        return p;
    }

    @SuppressWarnings("unchecked")
    public List<Product> findByProdNameFrSearch(String pname, int CatType) {
        Session session = sessionFactory.openSession();
        List<Product> p = null;
        try {
            session.beginTransaction();
            if(CatType==0){
                p = session.createQuery("from Product where lower(name) LIKE '%" + pname + "%'").list();
            }else{
                p = session.createQuery("from Product where lower(name) LIKE '%" + pname + "%' AND cid LIKE '%" + CatType + "%'").list();
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.getMessage();
            session.getTransaction().rollback();
        }
        return p;
    }

    @Override
    public List<Product> findByProdByTopChoice(String tc) {
        Session session = sessionFactory.openSession();
        List<Product> prod = null;
        try {
            session.beginTransaction();
            prod = session.createQuery("from Product where TOP_LISTS ='" + tc + "'").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return prod;
    }

    @Override
    public List<Product> findByProdByCols(String coll) {
        Session session = sessionFactory.openSession();
        List<Product> prod = null;
        try {
            session.beginTransaction();
            prod = session.createQuery("from Product where COLL_TYPE ='" + coll +"'").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return prod;
    }

}
