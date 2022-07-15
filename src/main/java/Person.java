public class Person {
    private String firstName;
    private String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String toString() {
        StringBuffer personString = new StringBuffer();
        personString.append("firstName = " + firstName);
        personString.append("\n");
        personString.append("lastName = " + lastName);
        personString.append("\n");


        return personString.toString();
    }

    public String formatAsCSV() {
        StringBuffer personString = new StringBuffer();
        personString.append(firstName);
        personString.append(",");
        personString.append(lastName);

        return personString.toString();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
