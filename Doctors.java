package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctors {
    private Connection connection;
    Doctors(Connection connection)
    {
        this.connection=connection;
    }

    //View Doctors
    public void  viewDoctors()
    {
        try{

            String query="select * from doctors";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                int id=resultSet.getInt("id");
                System.out.println("Doctor id is: "+id);
                String name=resultSet.getString("name");
                System.out.println("Doctor Name is: "+name);
                String  speciality=resultSet.getString("specialiazation");
                System.out.println("specialization is: "+speciality);
                System.out.println();// for space
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    // check doctor data in the database
    public boolean getDoctorsById(int id)
    {
        String query= "select * from doctors where id=?";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                return  true;
            }
            else {
                return  false;
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return  false;
    }

}
