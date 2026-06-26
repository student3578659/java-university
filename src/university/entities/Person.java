package university.entities;

public class Person {
    private final int id;
    private String fullName;
    private String email;

    public Person(int id, String fullName, String email) {
        if (id < 1) {
            throw new IllegalArgumentException("id має бути більше 0");
        }
        this.id = id;
        setFullName(fullName);
        setEmail(email);
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        if (fullName == null || fullName.trim().length() < 2) {
            throw new IllegalArgumentException("ПІБ має містити мінімум 2 символи");
        }
        this.fullName = fullName.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("неправильний формат email");
        }
        this.email = email.trim();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Person person = (Person) object;
        return id == person.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "id=" + id + ", ПІБ='" + fullName + "', email='" + email + "'";
    }
}
