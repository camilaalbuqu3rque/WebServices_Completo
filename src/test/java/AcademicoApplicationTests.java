import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.ifgoias.academico.AcademicoApplication;

@SpringBootTest(classes = AcademicoApplication.class) // Adicionando referência à classe principal
class AcademicoApplicationTests {

    @Test
    void contextLoads() {
        // Apenas verifica se o contexto do Spring Boot inicializa corretamente
    }
}
