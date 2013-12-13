package test.immutable;

final public class MyImmutable {

    private final TypeA objA;
    private final String text;

    public MyImmutable(TypeA objA, String text) {
        this.objA = objA;
        this.text = text;
    }

    public TypeA getObjA() {
        return objA;
    }

    public String getText() {
        return text;
    }

    public static void main(String[] args) {
        TypeA objA = new TypeA(10);
        MyImmutable immutable = new MyImmutable(objA, "Immutable message");
        System.out.println(immutable.getObjA().getCount());
        objA.setCount(20);

        System.out.println(immutable.getObjA().getCount());
        System.out.println(immutable.getText());

    }
}
