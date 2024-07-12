package HospitalManagementSystem;

import javax.print.Doc;
import java.sql.*;
import java.util.Scanner;

public class HospitalManagement {
    private static final String url="jdbc:mysql://localhost:3306/hospital";
    private static final String username="root";
    private static final String password="Krishna@7065";

    public static void main(String[] args)
    {
        // load necessary drivers
        try
        {
          Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
           e.printStackTrace();
        }
    // connect with database
        Scanner sc=new Scanner(System.in);
        try {
            Connection connection= DriverManager.getConnection(url,username,password);
            Patients patients=new Patients(connection,sc);
            Doctors doctors=new Doctors(connection);

            while (true){
                System.out.println("--- HOSPITAL MANAGEMENT SYSTEM ---");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patient");
                System.out.println("3. View Doctor");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println("Enter your choice: ");
                int choice= sc.nextInt();
                switch (choice){
                    case 1: patients.addPatients();
                        System.out.println();
                    break;
                    case 2: patients.viewPatients();
                        System.out.println();
                    break;
                    case 3: doctors.viewDoctors();
                        System.out.println();
                    break;
                    case 4: BookAppoint(patients,doctors,connection,sc);
                        System.out.println();
                    break;
                    case 5:
                        System.out.println("Thank you for using HOSPITAL MANAGEMENT SYSTEM");
                        return;
                    default:
                        System.out.println("Enter Valid Choice(1,2,3,4,5)");
                }
            }

        }catch (SQLException e){
        e.printStackTrace();
        }
    }
    public static void BookAppoint(Patients patients , Doctors doctors, Connection connection ,Scanner sc){
        System.out.println("Enter Patients Id: ");
        int pid= sc.nextInt();
        System.out.println("Enter Doctors Id: ");
        int did= sc.nextInt();
        System.out.println("Enter Appointment Date (YYYY-MM-DD): ");
        String appointdate= sc.next();
        if(patients.getPatientById(pid) && doctors.getDoctorsById(did))
        {
            if (Checkdoctoravailable(appointdate,did,connection)){
              String appointmentquery="insert into appointments(PID,DID,appointment_date) values(?,?,?)";

                try {
                    PreparedStatement preparedStatement=connection.prepareStatement(appointmentquery);
                    preparedStatement.setInt(1,pid);
                    preparedStatement.setInt(2,did);
                    preparedStatement.setString(3,appointdate);

                    int Affectedrows=preparedStatement.executeUpdate();
                    if(Affectedrows >0){
                        System.out.println("Appointment Booked");
                    }else {
                        System.out.println("Failed to Book appointment");
                    }
                } catch (SQLException e){
                    e.printStackTrace();
                }


            }else {
                System.out.println("Doctor not available on this date....");
            }

        }else {
            System.out.println("Either Doctor or patients doesn't exist");

        }

    }
    public static boolean Checkdoctoravailable(String appointdate, int did, Connection connection)
    {
        String query="select count(*) from appointments where DID=? AND appointment_date=?";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,did);
            preparedStatement.setString(2,appointdate);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                int cout=resultSet.getInt(1);
                if (cout==0){
                    return  true;
                }else {
                    return false;
                }
            }


           } catch (SQLException e)
        {
           e.printStackTrace();
        }
        return false;
    }


}

