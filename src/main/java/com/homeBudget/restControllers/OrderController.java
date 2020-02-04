package com.homeBudget.restControllers;

import com.homeBudget.dao.CleaningFeeDAO;
import com.homeBudget.dao.OrderDAO;
import com.homeBudget.dao.OrdersProductDAO;
import com.homeBudget.exception.OrderConstraintViolationException;
import com.homeBudget.exception.OrderNotFoundException;
import com.homeBudget.model.CleaningFee;
import com.homeBudget.model.Order;
import com.homeBudget.model.OrdersProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@RestController
public class OrderController {

	@Autowired
	private OrderDAO orderDao;
	@Autowired
	private OrdersProductDAO orderProductDao;
	@Autowired
	private CleaningFeeDAO cleaningFeeDAO;


	@RequestMapping(value = "/Order/{id}", method = RequestMethod.GET)
	public  ResponseEntity<Order>  getById(@PathVariable("id") Integer id) throws OrderNotFoundException {
		try {
			Order category = orderDao.findById(id).get();
			if (category == null) {
				System.out.println("Unable to delete. User with id " + id + " not found");
				return new ResponseEntity<Order>(HttpStatus.NOT_FOUND) ;
			}
			return new ResponseEntity<Order>(category,HttpStatus.OK) ;

		} catch (Exception ex) {
			throw new  OrderNotFoundException(ex.getMessage());

		}
	}
	@RequestMapping(value = "/Order/", method = RequestMethod.GET)
	public  ResponseEntity<List<Order>>  getAll() throws OrderNotFoundException {
		try {
			Iterator<Order> category = orderDao.findAll().iterator();
			List<Order>productList=new ArrayList<>();
			if (category == null) {

				return new ResponseEntity(HttpStatus.NOT_FOUND) ;
			}
		     while(category.hasNext())
			 {
				 productList.add(category.next());
			 }
			return new ResponseEntity<List<Order>>(productList,HttpStatus.OK) ;

		} catch (Exception ex) {
			throw new  OrderNotFoundException(ex.getMessage());

		}
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	@RequestMapping(value = "/Order/", method = RequestMethod.POST)
	public ResponseEntity<Order> create(@RequestBody Order order) throws OrderNotFoundException{

		if (order != null&&order.getOrdersProducts()!=null&&order.getUser()!=null) {
			order.setTotal(order.getTotal()+30);
			order.setCreationDate(new Date());
			order.setPhoneNumber("02"+order.getUser().getMobileNumber());
			order.setUpdateDate(new Date());
			Order order1 = orderDao.save(order);
            List<OrdersProduct>ordersProducts=order.getOrdersProducts();

			 for(int i=0;i<ordersProducts.size();i++) {
				 OrdersProduct  ordersProduct=ordersProducts.get(i);
				 ordersProduct.setCreationDate(new Date());
				 ordersProduct.setOrder(order1);
				 if( ordersProduct.getCleaningFeeId()!=null) {
					 try {
						 CleaningFee cleaningFee = cleaningFeeDAO.findById(ordersProduct.getCleaningFeeId()).get();
						 ordersProduct.setCleaningFee(cleaningFee);
					 }catch (Exception e)
					 {
						// e.printStackTrace();
					 }
				 }

				 orderProductDao.save(ordersProducts.get(i));


			 }
//			URI locationU = ServletUriComponentsBuilder.fromCurrentRequest().path(
//					"/{id}").buildAndExpand(location1.getId()).toUri();
		//	return ResponseEntity.created(locationU).build();

			return new ResponseEntity<Order>(order1,HttpStatus.OK) ;
		}else
		{
			 throw new OrderNotFoundException("please privide Order In Request Body");

		}


	}

	@RequestMapping(value = "/Order/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Order> update(@PathVariable("id") int id, @RequestBody Order location) {

		Order currentOrder1= orderDao.findById(id).get();

		if (currentOrder1 == null) {

			return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
		}


		currentOrder1=location;
		currentOrder1.setUpdateDate(new Date());
	    Order product=orderDao.save(currentOrder1);


		///update location
		return new ResponseEntity<Order>(product, HttpStatus.OK);
	}

	@RequestMapping(value = "/Order/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Order> delete(@PathVariable("id") int id)throws OrderNotFoundException,OrderConstraintViolationException {
		System.out.println("Fetching & Deleting User with id " + id);

		Order user = orderDao.findById(id).get();
		if (user == null) {
		 throw new OrderNotFoundException("Unable to delete. Order with id \" + id + \" not found");
		}
			try {
				orderDao.deleteById(id);
				return new ResponseEntity<Order>(HttpStatus.OK);
			}catch (Exception ex)
			{
				throw new OrderConstraintViolationException(ex.getMessage());
			}
	}



	



}
