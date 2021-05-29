package jdbc.dao.entity;

import java.util.Objects;

public class DeveloperDao {
    private int idDeveloper;
    private String name;
    private int age;
    private String sex;
    private int idCompany;
    private double salary;

    public int getIdDeveloper() {
        return idDeveloper;
    }

    public void setIdDeveloper(int idDeveloper) {
        this.idDeveloper = idDeveloper;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id_developer=" + idDeveloper +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", id_company=" + idCompany +
                ", salary=" + salary +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeveloperDao that = (DeveloperDao) o;
        return age == that.age && idCompany == that.idCompany && Double.compare(that.salary, salary) == 0 && Objects.equals(name, that.name) && Objects.equals(sex, that.sex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, sex, idCompany, salary);
    }
}
