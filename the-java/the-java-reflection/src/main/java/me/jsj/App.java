package me.jsj;

import java.awt.print.Book;
import java.lang.reflect.*;
import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App {
/*
    public static void main( String[] args ) throws ClassNotFoundException {
        //클래스 타입의 인스턴스를 가져오는 방법1. 타입 활용
        Class<BookV1> bookClass = BookV1.class;

        //방법2. 생성된 인스턴스 활용
        BookV1 book = new BookV1();
        Class<? extends BookV1> aClass = book.getClass();

        //방법3. FQCN(Full Qualified Class Name) 활용 -> 문자열을 통해 클래스 타입의 인스턴스를 구할 수 있다.
        Class<?> bClass = Class.forName("me.jsj.BookV1");

        //클래스 인스턴스의 필드 조회 (이름을 통해 원하는 필드만 조회 가능)
        Arrays.stream(bookClass.getFields()).forEach(System.out::println); //public만 조회
        System.out.println();

        Arrays.stream(bookClass.getDeclaredFields()).forEach(System.out::println); //모든 필드 조회
        System.out.println();

        Arrays.stream(bookClass.getDeclaredFields()).forEach(field -> {
            try {
                field.setAccessible(true); //접근 지시자 무시 (전부 접근 가능)
                System.out.printf("%s %s\n", field, field.get(book));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        System.out.println();

        //클래스 인스턴스의 메서드 조회
        Arrays.stream(bookClass.getMethods()).forEach(System.out::println);
        System.out.println();

        //클래스 인스턴스의 생성자 조회
        Arrays.stream(bookClass.getConstructors()).forEach(System.out::println);
        System.out.println();

        //부모 클래스 조회
        System.out.println(MyBook.class.getSuperclass());
        System.out.println();
        
        //인터페이스 조회
        Arrays.stream(MyBook.class.getInterfaces()).forEach(System.out::println);
        System.out.println();

        //접근 지시자, static 여부 확인
        Arrays.stream(bookClass.getDeclaredFields()).forEach(field -> {
            field.setAccessible(true);
            int modifiers = field.getModifiers();
            System.out.println(field);
            System.out.println(Modifier.isPrivate(modifiers));
            System.out.println(Modifier.isStatic(modifiers));
        });
        System.out.println();

        //Annotation 조회
        Arrays.stream(bookClass.getAnnotations()).forEach(System.out::println);
        System.out.println();

        //상속된 클래스의 Annotation 조회 (@Inherited 필요)
        Arrays.stream(MyBook.class.getAnnotations()).forEach(System.out::println);
        System.out.println();

        //자기 자신의 Annotation만 조회
        Arrays.stream(MyBook.class.getDeclaredAnnotations()).forEach(System.out::println);
        System.out.println();

        //필드에 붙은 Annotation 조회
        Arrays.stream(BookV1.class.getFields()).forEach(field -> {
            Arrays.stream(field.getAnnotations()).forEach(annotation -> {
                if (annotation instanceof MyAnnotation) {
                    MyAnnotation myAnnotation = (MyAnnotation) annotation;
                    System.out.println("myAnnotation.name() = " + myAnnotation.name());
                    System.out.println("myAnnotation.value() = " + myAnnotation.value());
                }
            });
        });
    }
*/
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        //직접 참조할 수 없고, 문자열을 통해 참조해야 하는 상황인 경우
        //생성자를 통해 인스턴스 생성
        Class<?> bookClass = Class.forName("me.jsj.BookV2");

        //생성자 파라미터가 없는 경우
//        Constructor<?> constructor = bookClass.getConstructor();
//        BookV2 book = (BookV2) constructor.newInstance();
        
        //생성자 파라미터가 있는 경우
        Constructor<?> constructor = bookClass.getConstructor(String.class);
        BookV2 book = (BookV2) constructor.newInstance("Test");

        System.out.println("book = " + book);

        //필드 참조
        //static
        Field a = BookV2.class.getDeclaredField("A");
        System.out.println(a.get(null)); //특정 인스턴스에 해당하는 필드면 파라미터로 넘겨야 하지만, static이므로 그럴 필요가 없다.
        a.set(null, "AAA"); //값 설정 (위와 같은 이유로 obj는 null)
        System.out.println(a.get(null));

        //인스턴스에 해당하는 필드
        Field b = BookV2.class.getDeclaredField("b");
//        System.out.println("b.get(null) = " + b.get(null)); //NullPointerException 발생
        b.setAccessible(true); //접근 지시자 모두 허용
        System.out.println("b.get(book) = " + b.get(book)); //위에서 선언한 book 객체의 b 필드 값을 가져옴 ("Test")
        b.set(book, "Test2");
        System.out.println("b.get(book) = " + b.get(book));

        //메서드 참조
        Method c = BookV2.class.getDeclaredMethod("c");//모든 접근 지시자의 메서드 참조 가능(단, 이 클래스 내부에서 정의한 메서드여야 한다.)
        c.setAccessible(true); //접근 지시자 모두 허용
        c.invoke(book); //해당 객체의 메서드 호출

        Method sum = BookV2.class.getDeclaredMethod("sum", int.class, int.class); //파라미터가 있는 경우 파라미터 타입 필요
        int invoke = (int) sum.invoke(book, 1, 2);
        System.out.println("invoke = " + invoke);
    }
}
