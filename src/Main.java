import java.lang.annotation.*;
import java.lang.reflect.Field;

public class Main {

    public static void main(String[] args) throws IllegalAccessException {
       UseCollaborator useCollaborator = new UseCollaborator();
        System.out.println(useCollaborator);

        Contener.wstrzyknij(useCollaborator, "wstrzykniety collaborator" );
        System.out.println(useCollaborator);
    }
}

//TODO wlasna adnotacja
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface Wstrzyknij {

}

//TODO klasa do wstrzykiwania
class Collaborator {
    private String name;

    public Collaborator(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Collaborator{" +
                "name='" + name + '\'' +
                '}';
    }
}

class UseCollaborator {

    private Collaborator collaborator;
    @Wstrzyknij
    private Collaborator collaboratorAnnotation;

    @Override
    public String toString() {
        return "UseCollaborator{" +
                "collaborator=" + collaborator +
                ", collaboratorAnnotation=" + collaboratorAnnotation +
                '}';
    }
}

class Contener {
    static void wstrzyknij (Object target, String name) throws IllegalAccessException {
        //TODO pobierz pola obiektu i przeiteruj
        Field table[] = target.getClass().getDeclaredFields();
        for (Field field: table) {

            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation: annotations) {
                if("Wstrzyknij".equals(annotation.annotationType().getSimpleName())) {
                    field.setAccessible(true);
                    field.set(target, new Collaborator(name));
                }
            }
        }

    }
}
