package sec.kursa;

public class Main {
    public static void main(String[] args) {
        Test ts = new Test(4,"text",  'd');
        try {
            String res = Serializer.serialize(ts);
            System.out.println(ts.toString());
            System.out.println("--------------------");
            ts = DeSerializer.deSerializer(res, Test.class);
            System.out.println(ts.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
