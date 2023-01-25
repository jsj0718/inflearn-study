package hellojpa;

public class ValueMain {

    public static void main(String[] args) {
        Address address1 = new Address("city", "street", "12345");
        Address address2 = new Address("city", "street", "12345");

        System.out.println("address1.equals(address2) = " + address1.equals(address2));
    }
}
