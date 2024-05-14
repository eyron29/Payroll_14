package Project1;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EmployeeList {
    static Scanner inp = new Scanner(System.in);

    static ArrayList<Employee> emp = new ArrayList<>();

    static ArrayList<String> fileList = new ArrayList<String>();

    static ArrayList<String> fileList2 = new ArrayList<String>();
    static String posCode, depCode, empName, empPos;
    static double hoursWorked, regPay, otPay, netPay;

    static double hourlyRate, taxRate;
    static double regHours = 176.02;
    static char subCode;
    static String ans ="";

    static String fileName = "", fileName1 = "";

    static int choice, option;

    static String remName;

    static int x;

    static boolean nameIsFound = false;

    //index to remove
    static int i;

    static boolean isValidInput1 = false;

    static boolean isValidInput2 = false;

    static boolean isValidInput3 = false;

    static boolean isValidName = false;

    static String statCode;

    static double presentNum;

    static double otHours;

    static boolean fileIsDeleted = false;

    static String integers = "0123456789";

    static boolean containsInt = false;

    static String day, dateCreated;

    static DecimalFormat twodec = new DecimalFormat("0.00");

    public static void main(String[] args) throws InvalidInputException{

        do{
            menu();
            System.out.print("Back to Main Menu?   (YES/NO) : ");
            ans = inp.nextLine();
        }while(ans.equalsIgnoreCase("YES"));
        //members();

    }//main
    public static void menu(){
        System.out.println("\n------------------------------------------------------------------");
        System.out.println("•••••••••••••••••••EMPLOYEE PAYROLL SYSTEM ••••••••••••••••••••••••");
        System.out.println("------------------------------------------------------------------");
        System.out.println("******************************************************************");
        System.out.println("               Select from the Following Options                   ");
        System.out.println("******************************************************************");
        System.out.println("------------------------------------------------------------------");
        System.out.println("\t [1] - Add Employee Record");
        System.out.println("\t [2] - Display Employee Record");
        System.out.println("\t [3] - Remove Employee Record");
        System.out.println("\t [4] - File Options");
        System.out.println("\t [5] - Exit Program");
        System.out.println("------------------------------------------------------------------");

        try {
            System.out.print("Enter Choice: ");
            choice = inp.nextInt();
            if(choice <= 0  || choice > 5){
                throw new InvalidInputException("Invalid option");
            }
        } catch(InvalidInputException e) {
            System.out.println("NOTE: Enter a valid option");
            System.out.println("------------------------------------------------------------------");
        } catch(InputMismatchException e) {
            System.out.println("Input Mismatch Exception: Enter a valid number");
            System.out.println("------------------------------------------------------------------");
        } finally {
            inp.nextLine();
        }

        switch(choice){
            case 1:{
                do{
                    System.out.println("------------------------------------------------------------------");
                    addRecord();
                    inp.nextLine();
                    System.out.print("Add Another Employee?   (YES/NO) : ");
                    ans = inp.nextLine();
                }while(ans.equalsIgnoreCase("YES"));
                break;
            }
            case 2:{
                printRecord();
                break;
            }
            case 3:{
                removeRecord();
                break;
            }
            case 4:{
                fileOption();
                break;
            }
            case 5:{
                System.exit(0);
                break;
            }
            default:{
                break;
            }
        }

    }//menu

    public static void addRecord(){

        while(!isValidInput1){
            System.out.print("Employee Code (000X) : ");
            posCode = inp.nextLine();

            if(posCode.equalsIgnoreCase("011A") || posCode.equalsIgnoreCase("011B")
                    || posCode.equalsIgnoreCase("011C") || posCode.equalsIgnoreCase("011D")){
                isValidInput1 = true;
            } else {
                System.out.println("INVALID...");
                System.out.println();
            }
        }

        subCode = posCode.charAt(3);

        while(!isValidInput2){
            System.out.print("Status (SWD/SOD) : ");
            statCode = inp.nextLine();

            if(statCode.equalsIgnoreCase("SWD") || statCode.equalsIgnoreCase("SOD")){
                isValidInput2 = true;
            } else {
                System.out.println("INVALID...");
                System.out.println();
            }
        }

        while(!isValidName){
            System.out.print("Employee Name    : ");
            empName = inp.nextLine();

//            if(!(empName.isEmpty())){
//                isValidName = true;
//            } else {
//                System.out.println("NAME CANNOT BE EMPTY...");
//                System.out.println();
//            }

            for(x = 0; x<empName.length(); x++){
                char eN = empName.charAt(x);
                if(integers.indexOf(eN) >= 0){
                    containsInt = true;
                } else {
                    continue;
                }//else
            }//for

            if(containsInt == true){
                System.out.println("NAME CANNOT CONTAIN A NUMBER...");
                System.out.println();
            } else {
                isValidName = true;
            }

            containsInt = false;
        }//isValidName

        //System.out.println();

        while(!isValidInput3){
            try {
                System.out.print("Hours Worked : ");
                hoursWorked = inp.nextDouble();
                if(!(hoursWorked > 0.0)){
                    throw new InvalidInputException("Number is less than 0.00");
                } else {
                    isValidInput3 = true;
                }
            } catch(InvalidInputException e) {
                System.out.println("NOTE: Hours cannot be equal to 0.00");
                System.out.println();
            } catch(InputMismatchException e) {
                System.out.println("Input Mismatch Exception: Enter a number");
                System.out.println();
            } finally {
                inp.nextLine();
            }
        }

        presentNum = hoursWorked / 8.00;

        switch(subCode){
            case 'a':
            case 'A':{
                hourlyRate = 290.00;
                empPos = "Senior Programmer";
                break;
            }
            case 'b':
            case 'B': {
                hourlyRate = 148.00;
                empPos = "Junior Programmer";
                break;
            }
            case 'c':
            case 'C': {
                hourlyRate = 159.00;
                empPos = "System Analyst";
                break;
            }
            case 'd':
            case 'D': {
                hourlyRate = 165.00;
                empPos = "Data Analyst";
                break;
            }
            default:{
                break;
            }
        }
        depCode = "Management Information Systems";
        computeSalary(hoursWorked, hourlyRate);

        emp.add(new Employee(empName,posCode,empPos, depCode,statCode,hourlyRate,hoursWorked, presentNum, regPay,otPay,netPay));
        System.out.print("------------------------------------------------------------------");

        isValidInput1 = false;
        isValidInput2 = false;
        isValidInput3 = false;
        isValidName = false;

    }//addRecord

    public static double computeSalary(double hoursWorked, double hourlyRate){

        if(hoursWorked > regHours){
            //with Overtime Pay (SWD/SOD)
            if(statCode.equals("SWD")){ //SWD: Single With Dependent
                regPay= hoursWorked * hourlyRate;
                otHours = hoursWorked - regHours;
                otPay =  otHours * hourlyRate * 1.5;
                taxRate = regPay * 0.05;
                netPay = (regPay + otPay) - taxRate;
            }
            else {// SOD: Single W/O Dependent
                regPay = hoursWorked * hourlyRate;
                otHours = hoursWorked - regHours;
                otPay =  otHours * hourlyRate * 1.5;
                taxRate = regPay * 0.10;
                netPay = (regPay + otPay) - taxRate;
            }
        } else {

            if(statCode.equals("SWD")){ //SWD: Single With Dependent
                regPay = hoursWorked * hourlyRate;
                otPay =  0;
                taxRate = regPay * 0.05;
                netPay = regPay - taxRate;
            } else {// SOD: Single W/O Dependent
                regPay = hoursWorked * hourlyRate;
                otHours = hoursWorked - regHours;
                otPay =  0;
                taxRate = regPay * 0.10;
                netPay = regPay - taxRate;
            }
        }
        return netPay;
    }//computeSalary

    public static void printRecord(){
        if(emp.isEmpty()){
            System.out.println("NO EMPLOYEE RECORD FOUND...");
            System.out.println("------------------------------------------------------------------");
        }
        else{
            System.out.println("\n------------------------------------------------------------------");
            System.out.println("                   EMPLOYEE PAYROLL RECORD                        ");
            System.out.println("------------------------------------------------------------------");
            for(x =0; x<emp.size(); x++){
                System.out.println("EMPLOYEE NAME         : " + emp.get(x).empName);
                System.out.println("POSITION CODE         : " + emp.get(x).posCode);
                System.out.println("POSITION              : " + emp.get(x).empPos);
                System.out.println("DEPARTMENT            : " + emp.get(x).depCode);
                System.out.println("STATUS                : " + emp.get(x).statCode);
                System.out.println("PAY PER HOUR          : " + emp.get(x).hourlyRate);
                System.out.println("HOURS WORKED          : " + twodec.format(emp.get(x).hoursWorked));
                System.out.println("PRESENT DAYS          : " + twodec.format(emp.get(x).presentNum));
                System.out.println("REGULAR PAY           : " + twodec.format(emp.get(x).regPay));
                System.out.println("OVERTIME PAY          : " + twodec.format(emp.get(x).otPay));
                System.out.println("TAX/DEDUCTIONS        :");
                System.out.println("NET PAY               : " + twodec.format(emp.get(x).netPay));
                System.out.println("------------------------------------------------------------------");
            }
            //System.out.println("------------------------------------------------------------------");
        }
    }//printRecord

    public static void removeRecord(){
        //Status: Not yet finish

        if(emp.isEmpty()){
            System.out.println("NO EMPLOYEE RECORD FOUND...");
            System.out.println("------------------------------------------------------------------");
        } else {
            do{
                if(emp.isEmpty()){
                    System.out.println("------------------------------------------------------------------");
                    System.out.println("NO EMPLOYEE RECORD FOUND...");
                    System.out.println("NOTE: ADD EMPLOYEE RECORD FIRST");
                    System.out.println("------------------------------------------------------------------");
                    break;
                } else {
                    System.out.println("\n------------------------------------------------------------------");
                    System.out.println("                LIST OF EXISTING EMPLOYEES                        ");
                    System.out.println("------------------------------------------------------------------");
                    System.out.println("NAME \t\t\tPOSITION");
                    System.out.println("------------------------------------------------------------------");
                    for(x=0; x<emp.size(); x++){
                        System.out.println((x+1) + "."+ emp.get(x).empName + " - " + "\t\t" + emp.get(x).empPos);
                    }
                    System.out.println("------------------------------------------------------------------");
                    System.out.println("PRESS ANY KEY TO CANCEL...");
                    System.out.println("NOTE: PLEASE ENTER THE FULL NAME...");
                    System.out.println("------------------------------------------------------------------");
                    System.out.print("Name to Remove: ");
                    remName = inp.nextLine();

                    if(remName.isEmpty()){
                        System.out.println("ERROR: CANNOT BE EMPTY..");
                        System.out.println();
                    } else {
                        for(x = 0; x < emp.size(); x++){
                            if(remName.contains(emp.get(x).empName)){
                                i = x;
                                emp.remove(i);
                                nameIsFound = true;
                            } else {
                                continue;
                            }
                        }
                    }

                    if(!nameIsFound){
                        System.out.println("ERROR: NAME NOT FOUND...");
                    }else{
                        System.out.println("SUCCESSFULLY REMOVED...");
                    }
                    inp.nextLine();

                    System.out.print("Try Again?   (YES/NO) : ");
                    ans = inp.nextLine();

                    nameIsFound = false;
                }
            }while(ans.equalsIgnoreCase("YES"));
        }
    }//removeRecord

    public static void fileOption(){
        //NOTE: This includes all option for File Handling para organized

        do{
            System.out.println("\n------------------------------------------------------------------");
            System.out.println("                     FILE OPTIONS MENU                        ");
            System.out.println("------------------------------------------------------------------");
            System.out.println("******************************************************************");
            System.out.println("               Select from the Following Options                   ");
            System.out.println("******************************************************************");
            System.out.println("------------------------------------------------------------------");
            System.out.println("\t [1] - Create New File");
            System.out.println("\t [2] - Display File Content");
            System.out.println("\t [3] - Check for Existing File/s");
            //System.out.println("\t [4] - Delete File");
            System.out.println("------------------------------------------------------------------");

            try {
                System.out.print("Enter Option: ");
                option = inp.nextInt();
                if(option <= 0  || option > 4){
                    throw new InvalidInputException("Invalid option");
                }
            } catch(InvalidInputException e) {
                System.out.println("NOTE: Enter a valid option");
                System.out.println("------------------------------------------------------------------");
            } catch(InputMismatchException e) {
                System.out.println("Input Mismatch Exception: Enter a valid number");
                System.out.println("------------------------------------------------------------------");
            } finally {
                inp.nextLine();
            }

            switch(option){
                case 1:{
                    if(emp.isEmpty()){
                        System.out.println("------------------------------------------------------------------");
                        System.out.println("ERROR: CANNOT GENERATE A FILE");
                        System.out.println("NOTE: NO EMPLOYEE RECORD FOUND...");
                        System.out.println("------------------------------------------------------------------");
                    } else {
                        System.out.println("------------------------------------------------------------------");
                        System.out.print("Enter a File Name : ");
                        fileName = inp.nextLine();

                        for(x = 0; x < fileList.size(); x++) {
                            if (fileName.equals(fileList.get(x))) {
                                System.out.println("------------------------------------------------------------------");
                                System.out.println("CAUTION: FILE ALREADY EXIST");
                                System.out.println("NOTE: FILE'S CONTENT WILL BE OVERRIDE...");
                                fileList.remove(String.valueOf(fileName));
                            } else {
                                continue;
                            }
                        }
                        fileName1 = "C:/Users/Owner/Desktop/Payroll_Records/" + fileName + ".txt";
                        fileList.add(fileName);
                        writeFile(fileName, fileName1);
                        System.out.println("------------------------------------------------------------------");
                        System.out.println("--FILE SUCCESSFULLY CREATED--");
                        System.out.println("File Location: " + fileName1);
                        System.out.println("------------------------------------------------------------------");
                    }//else
                    break;
                }
                case 2:{
                    readFile();
                    break;
                }
                case 3:{
                    checkFile();
                    break;
                }
                case 4:{
                    deleteFile();
                    break;
                }
                default:{
                    break;
                }
            }//switch

            System.out.print("Try Again?   (YES/NO) : ");
            ans = inp.nextLine();

        }while(ans.equalsIgnoreCase("YES"));

    }

    public static String writeFile(String fileName, String fileName1){

        try{
            FileWriter writer = new FileWriter(fileName1);
            writer.write("------------------------------------------------------------------");
            writer.write("\n                   EMPLOYEE PAYROLL RECORD                        ");
            writer.write("\n------------------------------------------------------------------");
            for(x=0; x<emp.size(); x++){
                writer.write("\nEMPLOYEE NAME         : " + emp.get(x).empName);
                writer.write("\nPOSITION CODE         : " + emp.get(x).posCode);
                writer.write("\nPOSITION              : " + emp.get(x).empPos);
                writer.write("\nDEPARTMENT            : " + emp.get(x).depCode);
                writer.write("\nSTATUS                : " + emp.get(x).statCode);
                writer.write("\nPAY PER HOUR          : " + emp.get(x).hourlyRate);
                writer.write("\nHOURS WORKED          : " + emp.get(x).hoursWorked);
                writer.write("\nPRESENT DAYS          : " + emp.get(x).presentNum);
                writer.write("\nREGULAR PAY           : " + emp.get(x).regPay);
                writer.write("\nOVERTIME PAY          : " + emp.get(x).otPay);
                writer.write("\nTAX/DEDUCTIONS        : ");
                writer.write("\nNET PAY               : " + emp.get(x).netPay);
                writer.write("\n------------------------------------------------------------------");
            }
            writer.write("\n                  FILE DOCUMENT PROPERTIES                        ");
            writer.write("\n------------------------------------------------------------------");
            writer.write("\nFile Name    : " + fileName);
            writer.write("\nFile Location: " + fileName1);
            writer.write("\nType of File : Text Document (.txt)");
            writer.write("\n------------------------------------------------------------------");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileName1;
    }//writeFile

    public static void checkFile(){

        if(fileList.isEmpty()){
            System.out.println("------------------------------------------------------------------");
            System.out.println("ERROR: LIST IS EMPTY");
            System.out.println("NOTE: CREATE A NEW FILE...");
        }
        else {
            System.out.println("------------------------------------------------------------------");
            System.out.println("FILE LIST RECORD:");
            for (x = 0; x < fileList.size(); x++) {
                System.out.println("\t" + (x + 1) + ". " + fileList.get(x));
            }
        }
        System.out.println("------------------------------------------------------------------");

        //since the file is already created from createFile() method
        //this does not create a new file, it just checks if the fileName exist already

        File file = new File(fileName1);

        //check if the file exist
        if(file.exists()){
            //return the path of file
            System.out.println("--MOST RECENT FILE--");
            System.out.println("File Location: " + file.getAbsolutePath());
            System.out.println("------------------------------------------------------------------");
        }
        else {
            System.out.println("NO EXISTING FILE...");
            System.out.println("------------------------------------------------------------------");
        }

    }//checkFile

    public static void readFile(){

        //Edit: Yung by scanner yung reader
        //Scanner read = new Scanner(fileHere);

        if(fileList.isEmpty()){
            System.out.println("------------------------------------------------------------------");
            System.out.println("NO EXISTING FILE...");
            System.out.println("------------------------------------------------------------------");
        } else {

            System.out.println();

            try {
                FileReader reader = new FileReader(fileName1);
                int data = reader.read();

                while(data != -1 ){
                    System.out.print((char) data);
                    data = reader.read();
                }
                reader.close();
                System.out.println();

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void deleteFile(){

        if(fileList.isEmpty()){
            System.out.println("NO EXISTING FILE...");
            System.out.println("------------------------------------------------------------------");
        } else {

            File file = new File(fileName1);

            if(file.exists()){
                file.delete();
                fileIsDeleted = true;
                System.out.println("FILE SUCCESSFULLY DELETED...");
                System.out.println("------------------------------------------------------------------");
            }
        }
    }//deleteFile

    //            if(fileIsDeleted = true){
//                break;
//            } else {
//                System.out.print("Try Again?   : ");
//                ans = inp.nextLine();
//            }

}//class
