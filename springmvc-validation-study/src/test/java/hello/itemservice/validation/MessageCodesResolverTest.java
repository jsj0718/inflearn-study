package hello.itemservice.validation;

import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageCodesResolverTest {

    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    @Test
    void messageCodesResolverObject() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");

        assertThat(messageCodes).containsExactly("required.item", "required");

/*      # messageCodesResolver 동작 방법
        for (String messageCode : messageCodes) {
            System.out.println("messageCode = " + messageCode);
        }

        new ObjectError("item", new String[]{"required.item", "required"});
*/
    }

    @Test
    void messageCodesResolverObjectField() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item", "itemName", String.class);
        assertThat(messageCodes).containsExactly(
                "required.item.itemName",
                "required.itemName",
                "required.java.lang.String",
                "required");


/*      # bindingResult.rejectValue("itemName", "required") 동작 방법
        for (String messageCode : messageCodes) {
            System.out.println("messageCode = " + messageCode);
        }

        # messageCodes = new String[]{"required.item.itemName", "required.itemName", "required.java.lang.String", "required"}
        new FieldError("item", "itemName", null, false, messageCodes, null, null);
*/
    }
}
