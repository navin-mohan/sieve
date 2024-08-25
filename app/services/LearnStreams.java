package services;

import services.utils.TopKFrequentItems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LearnStreams {
    static class Person implements Comparable<Person> {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        @Override
        public int hashCode() {
            final String s = name + age;
            return s.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (obj.getClass() != getClass()) {
                return false;
            }
            Person p = (Person) obj;
            return p.name.equals(name) && p.age == age;
        }

        @Override
        public int compareTo(Person o) {
            if (age != o.age) {
                return Integer.compare(age, o.age);
            }
            return name.compareTo(o.name);
        }

        public static List<Person> filterPerson(List<Person> persons, PersonFilter filter) {
            List<Person> result = new ArrayList<>();
            for (Person p : persons) {
                if (filter.test(p)) {
                    result.add(p);
                }
            }
            return result;
        }

        public static boolean isAdult(Person p) {
            return p.getAge() > 18;
        }
    }

    interface PersonFilter {
        boolean test(Person p);
    }

    public static void main(String[] args) {
        List<Person> l = new ArrayList<>();
        l.add(new Person("Alice", 23));
        l.add(new Person("Bob", 45));
        l.add(new Person("Poulet Sivaji", 15));

        List<Person> above30 = Person.filterPerson(l, (Person p) -> p.getAge() > 30);
        System.out.println("Above 30:");
        for (Person p : above30) {
            System.out.println(p.getName());
        }

        List<Person> nameStartsWithA = Person.filterPerson(l, (Person p) -> p.getName().startsWith("A"));
        System.out.println("Name starts with A:");
        for (Person p : nameStartsWithA) {
            System.out.println(p.getName());
        }

        List<Person> isAdult = Person.filterPerson(l, Person::isAdult);
        System.out.println("Is Adult:");
        for (Person p : isAdult) {
            System.out.println(p.getName());
        }

        TopKFrequentItems<Person> t = new TopKFrequentItems<>(3);
        List<Person> persons = Arrays.asList(
                new Person("Alice", 23),
                new Person("Alice", 23),
                new Person("Bob", 45),
                new Person("Poulet Sivaji", 15),
                new Person("Alice", 23),
                new Person("Bob", 45),
                new Person("Poulet Sivaji", 15),
                new Person("Poulet Sivaji", 15),
                new Person("Poulet Sivaji", 15),
                new Person("Poulet Sivaji", 15),
                new Person("Poulet Sivaji", 15),
                new Person("Poulet Sivaji", 15),
                new Person("Poulet Sivaji", 15)
        );

        for (Person p : persons) {
            t.add(p);
            System.out.println("Top 3:");
            for (Person q : t.getTopK()) {
                System.out.println(q.getName());
            }
        }


    }
}
