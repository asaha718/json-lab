import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //Create the list
        List<Person> peopleList = parseCsv("people.data");
        //Ask the user if they want to restore the list of people from file.
        System.out.println("Would you like to restore the List of People? (y)");
        System.out.println("If not we will create a new list (n)");

        try (Scanner scanner = new Scanner(System.in)) {

            String userInput = scanner.nextLine();
            //If yes,
            //restore from the file you might have saved from a previous run of your program.
            if (userInput.equals("y")) {
                readFromFile("people.data", true);
                //user options
                System.out.println("Would you like to add to List of People? (a) ");
                System.out.println("Print the list (p)");
                System.out.println("Exit (e)");
                String option = scanner.nextLine();

                if (option.equals("a")) {
                    createPerson(scanner);
                } else if (option.equals("p")) {
                    readFromFile("people.data", true);
                } else {
                    return;
                }

            } else if (userInput.equals("n")) {
                //If no, start a brand-new list.
                //brand new list
                System.out.println("Creating new List of People");
                List<Person> people = new ArrayList<>();

                Boolean addPerson = true;

                while (addPerson) {
                    Person createdPerson = createPerson(scanner);
                    people.add(createdPerson);

                    System.out.println("Want to add another person(y) or exit(n)?");
                    String keepAddingPeople = scanner.nextLine();

                    if (keepAddingPeople.equals("y")) {
                        addPerson = true;
                    } else {
                        addPerson = false;
                    }
                }

                //take names and add them to csv file
                ArrayList<String> peopleNames = new ArrayList<>();

                for (Person i : people
                ) {
                    peopleNames.add(i.formatAsCSV());
                }

                System.out.println("Print the list (p)");
                System.out.println("Exit (e)");

                String option = scanner.nextLine();

                if (option.equals("p")) {
                    writeToFile("people.data", peopleNames);
                } else if (option.equals("e")) {
                    //add JSON file after last exit
                    writeJson(people);
                    return;
                }
            }

        }
    }

    public static Person createPerson(Scanner scanner) {
        Person newPerson;
        System.out.println("Lets create a person");
        System.out.println("Enter a First and Last Name: ");

        String nameInput = scanner.nextLine();
        String[] name = nameInput.split(" ");
        String firstName = name[0];
        String lastName = name[1];

        newPerson = new Person(firstName, lastName);
        return newPerson;

    }


    public static List<Person> parseCsv(String fileName) {
        //Let's create a list of students to save our data to.
        List<Person> people = new ArrayList<>();

        //We are going to open the file using a Scanner.
        File csvFile = new File(fileName);
        Scanner scanner;
        try {
            scanner = new Scanner(csvFile);
        } catch (Exception e) {
            System.out.println("File does not exist");
            return people;
        }
        System.out.println("File does exist!");

        //First let's read off the header
        String header = scanner.nextLine();

        //While there is a next line in the file
        while (scanner.hasNextLine()) {
            //Read off the next line
            String thisLine = scanner.nextLine();

            //Split on commas
            String[] personStrings = thisLine.split(",");

            //Make a student object
            String firstName = personStrings[0];
            String lastName = personStrings[1];

            Person person = new Person(firstName, lastName);

            //Add the student to the list.
            people.add(person);
        }

        //Return the finished list
        return people;
    }

    static void writeToFile(String fileName, ArrayList<String> list) throws IOException {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileName);
            for (String person : list
            ) {
                fileWriter.write(person + "\n");
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            if (fileWriter != null)
                fileWriter.close();
        }
    }

    static String readFromFile(String fileName, boolean addNewLine) throws IOException {
        String returnString = new String();
        Scanner fileReader = null;
        try {
            File myFile = new File(fileName);
            fileReader = new Scanner(myFile);
            while (fileReader.hasNextLine()) {
                returnString += fileReader.nextLine();
                if (addNewLine)
                    returnString += "\n";
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            if (fileReader != null)
                fileReader.close();
        }

        return returnString;
    }

    public static void writeJson(List<Person> people) throws JsonProcessingException {
        String json = new ObjectMapper().writeValueAsString(people);
        System.out.println(json);
        //Write JSON to file.
        try {
            writeToJSONFile("people.json", json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void writeToJSONFile(String fileName, String text) throws IOException {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileName);
            fileWriter.write(text);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            if (fileWriter != null)
                fileWriter.close();
        }
    }
}
