package HospitalManagementSystem;

import java.util.Scanner;
import  java.sql.*;

public class Patients
{
    private Connection connection;
    private Scanner sc;

    Patients(Connection connection, Scanner sc)
    {
        this.connection=connection;
        this.sc=sc;
    }
    //add patients
    public void addPatients()
    {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter Patients Name: ");
        String name=sc.nextLine();
        System.out.println("Enter Patients Age: ");
        int age=sc.nextInt();
        System.out.println("Enter Patients Gender: ");
        String gender=sc.next();

        // add to the database
        try{
            String query="insert into patients(name,age,gender) values (?,?,?)";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);

            int AffectedRows=preparedStatement.executeUpdate();
            if(AffectedRows >0){
                System.out.println("Patients added successfully.....");
            }else {
                System.out.println("Failed to added");
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    //View Patients
    public void  viewPatients()
    {
      try{

          String query="select * from patients";
          PreparedStatement preparedStatement=connection.prepareStatement(query);
          ResultSet resultSet=preparedStatement.executeQuery();
          while (resultSet.next()){
              int id=resultSet.getInt("id");
              System.out.println("Patient id is: "+id);
              String name=resultSet.getString("name");
              System.out.println("Patient Name is: "+name);
              int age=resultSet.getInt("age");
              System.out.println("patient Age: "+age);
              String  gender=resultSet.getString("gender");
              System.out.println("Patient Gender: "+gender);
              System.out.println();// for space
          }

      }catch (SQLException e)
      {
          e.printStackTrace();
      }

    }
    // check patient in the database
    public boolean getPatientById(int id)
    {
        String query= "select * from patients where id=?";
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
