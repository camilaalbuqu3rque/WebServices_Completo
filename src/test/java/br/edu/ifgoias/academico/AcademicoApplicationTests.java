package br.edu.ifgoias.academico;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = AcademicoApplication.class) // Adicionando referência à classe principal
class AcademicoApplicationTests {

    @Test
    void contextLoads() {
        // Apenas verifica se o contexto do Spring Boot inicializa corretamente
    }
}
