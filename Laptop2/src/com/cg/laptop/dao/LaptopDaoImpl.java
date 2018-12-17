package com.cg.laptop.dao;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cg.laptop.bean.CustomerBean;
import com.cg.laptop.bean.LaptopBean;
import com.cg.laptop.exception.LaptopException;
import com.cg.laptop.util.DBConnection;


public class LaptopDaoImpl implements ILaptopDao {
	
		@Override
		public String addCustomer(CustomerBean customer) throws LaptopException {
			String customerId=null;
			try
			{
				
				Connection connection=DBConnection.getConnection();
				PreparedStatement preparedStatement=null;
				ResultSet rs=null;
				Statement st=null;
				
				
				try {
					
					preparedStatement=connection.prepareStatement("INSERT INTO Customer_Details values(customerId_sequence.nextval,?,?,?,SYSDATE,?,?)");
					
					preparedStatement.setString(1, customer.getCustomerName());
					preparedStatement.setString(2, customer.getAddress());
					preparedStatement.setString(3, customer.getPhoneNumber());
					preparedStatement.setDouble(4, customer.getLaptopAmount());
					//preparedStatement.setString(5, customer.getLaptopId());
					preparedStatement.setString(6, customer.getMailId());
					//preparedStatement.setString(7, customer.getLaptopModelName());
					preparedStatement.executeUpdate();
					st=connection.createStatement();
					rs=st.executeQuery("select max(customer_id) from Customer_Details");
						
						while(rs.next()) {
							customerId=rs.getString(1);
							//System.out.println(rs.getString(2));
							//System.out.println(rs.getString(3));
							//System.out.println(rs.getString(4));
							//System.out.println(rs.getString(5));
							
							//System.out.println(rs.getDouble(6));
							
							
						}
					
					
					
					
				}
				catch(SQLException se) {
					
					System.out.println(se);
				}
	             			
				
				
				
			} 
			catch (Exception e)
			{
				System.out.println(e);
			}
			return customerId;
		}
		
		
		

		@Override
		public CustomerBean viewCustomerDetails(String customerId) throws LaptopException, ClassNotFoundException, IOException, SQLException {
		
			
			Connection connection=DBConnection.getConnection();
			Statement st=connection.createStatement();
			String a=customerId;
			CustomerBean bean = new CustomerBean();
			
	            try {
	            	
	  	         
	  	         
	  	         
				ResultSet rs=st.executeQuery("select * from Customer_Details where customer_id='"+a+"'");
				
				while(rs.next())
				{
					bean.setCustomerId(rs.getString(1));
					bean.setCustomerName(rs.getString(2));
					bean.setAddress(rs.getString(3));
					bean.setPhoneNumber(rs.getString(4));
					bean.setBookingDate(rs.getString(5));
					bean.setLaptopAmount(rs.getDouble(6));
					//bean.setLaptopId(rs.getString(7));
					bean.setMailId(rs.getString(8));

					
				}
			          
			          //st=connection.createStatement();
			         // st.executeQuery("SELECT * FROM Donor_Details WHERE Donor_Id=donorId");
			          
		
	                }
	               catch(Exception e) 
	               {
		                  
	            	   System.out.println(e);
	                }
				return bean;
		}
		
		
		
		

		@Override
		public List viewLaptopDetails(LaptopBean laptop) throws ClassNotFoundException, IOException, SQLException {
			Connection connection=DBConnection.getConnection();

            try {
            	
  	          
  	         
  	        List<LaptopBean> li=new ArrayList<LaptopBean>();
  	          
  	       Statement st=connection.createStatement();
  	        
  	       // PreparedStatement preparedStatement=connection.prepareStatement("select * from laptop");
			
			ResultSet rs=st.executeQuery("select * from laptop");
			
			while(rs.next())
			{
			
				LaptopBean laptopBean = new LaptopBean();
				laptopBean.setLaptopModelNo(rs.getString(1));
				laptopBean.setLaptopModelName(rs.getString(2));
				laptopBean.setRAM(rs.getString(3));
				laptopBean.setOS(rs.getString(4));
				laptopBean.setScreen(rs.getString(5));
				laptopBean.setHardDisk(rs.getString(6));
				laptopBean.setCPU(rs.getString(7));
				laptopBean.setLaptopPrice(rs.getInt(8));
				laptopBean.setQuantity(rs.getInt(9));
				
				li.add(laptopBean);	
			}
		          
		          //st=connection.createStatement();
		         // st.executeQuery("SELECT * FROM Donor_Details WHERE Donor_Id=donorId");
		          
			return li;
                }
               catch(Exception e) 
               {
	                  
            	   System.out.println(e);
                }
			return null;
			
			}
		
		
		
		

		@Override
		public void bookLaptop(LaptopBean laptop, CustomerBean customer) throws LaptopException {
			
			String modelno=laptop.getLaptopModelNo();
			//String laptopname=laptop.getLaptopModelName();
			System.out.println(modelno);
			System.out.println(customer.getCustomerName());
			Connection connection=null;
			try
			{
				connection=DBConnection.getConnection();
				
				PreparedStatement ps=connection.prepareStatement("INSERT INTO Customer_Details values(customerId_sequence.nextval,?,?,?,SYSDATE,?,?,?)");
				
				ps.setString(1, customer.getCustomerName());
				ps.setString(2, customer.getAddress());
				ps.setString(3, customer.getPhoneNumber());
				ps.setDouble(4, customer.getLaptopAmount());
				ps.setString(5, modelno);
				ps.setString(6, customer.getMailId());
				//ps.setString(7,laptopname);
				ps.executeUpdate();
				
				Statement s=connection.createStatement();
				s.executeUpdate("update laptop set quantity=quantity-1 where laptopModelNo='"+modelno+"'");
				connection.close();
				}
				
				catch(Exception e)
				{
					System.out.println(e);
				}
			
			
			
			
			
		}

		

	}

	
	
	


