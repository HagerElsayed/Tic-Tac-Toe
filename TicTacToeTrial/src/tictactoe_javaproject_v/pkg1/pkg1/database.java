/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe_javaproject_v.pkg1.pkg1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author somaya
 */
public class database {
    
    
    public int login(String username,String passwd)
	{
            int result=0;
	    try{
//		
                    Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/tic_toc","root","admin");
                    Statement stmt = connect.createStatement() ;
                    String queryString = new String("SELECT id  FROM player WHERE username='"+ username + "' and password='"+ passwd + "'");
                    ResultSet resultSet = stmt.executeQuery(queryString);

                     if (!resultSet.next()) {
                         result=0;
                           
                      }
                     else{
                         result=resultSet.getInt("id");
                        
                     }
                            stmt.close();
                            connect.close();
                }
                    catch(SQLException ex)
                    {
                    ex.printStackTrace();
                    }
            return result;
        }
     public int register(String username,String passwd,String name)//,String type)
	{
            int result=0;
	     try{
                Class.forName("com.mysql.jdbc.Driver"); 
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tic_toc" , "root","admin");
                Statement stmt = con.createStatement();
                String queryString = new String("insert into player set name='"+name+"' , password='"+passwd+"' ,username='"+username+"'");
                result=stmt.executeUpdate(queryString);
                 if(result==1){
                     result=1;
                 }
                 else{
                     result=0;
                 }
                
                
                stmt.close();
                con.close();
            }
            catch(Exception ex){ex.printStackTrace();}
             return result;
		
                    
        }
     public int insert_game(int first_player,int second_player)
	{
            int lastid=0;
	     try{
                Class.forName("com.mysql.jdbc.Driver"); 
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tic_toc" , "root","admin");
                 PreparedStatement ps = con.prepareStatement("insert into game (first_player,second_player) values(?,?) ", Statement.RETURN_GENERATED_KEYS);
                  ps.setInt(1,first_player );
                 ps.setInt(2,second_player );
                 
                 

                 ps.executeUpdate();
                 ResultSet rs = ps.getGeneratedKeys();

                 if (rs.next()) {
                     lastid = rs.getInt(1);
                 }
                
                
                
               
                con.close();
            }
            catch(Exception ex){ex.printStackTrace();}
             return lastid;
		
                    
        }
     public void local_game_save(List<Integer>x_row,List<Integer>x_col,List<Integer>o_row,List<Integer>o_col )
	{
           
            if(x_row.size()> o_row.size()){
                o_row.add(-1);
                o_col.add(-1);
               
               
            }
            
            if(x_row.size()< o_row.size()){
             
                x_row.add(-1);
                x_col.add(-1); 
            }
           
	     try{
                Class.forName("com.mysql.jdbc.Driver"); 
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tic_toc" , "root","admin");
                Statement stmt = con.createStatement();
                for(int i=0;i< x_row.size();i++){
                    int xrow=x_row.get(i);
                    int xcol=x_row.get(i);
                    int orow=x_row.get(i);
                    int ocol=x_row.get(i);
                    
                
                String queryString = new String("insert into local_game set x_row='"+xrow+"' , x_col='"+xcol+"' ,o_row='"+orow+"' ,o_col='"+ocol+"'");
                stmt.executeUpdate(queryString);
                }
                
                
                
                stmt.close();
                con.close();
            }
            catch(Exception ex){ex.printStackTrace();}
           
		
                    
        }
     
    
    
}
