package guru.springframework.sdjpaintro;

import guru.springframework.sdjpaintro.domain.AuthorUuid;
import guru.springframework.sdjpaintro.domain.BookNatural;
import guru.springframework.sdjpaintro.domain.BookUuid;
import guru.springframework.sdjpaintro.domain.composite.AuthorComposite;
import guru.springframework.sdjpaintro.domain.composite.NameId;
import guru.springframework.sdjpaintro.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"guru.springframework.sdjpaintro.bootstrap"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MySQLIntegrationTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorUuidRepository authorUuidRepository;

    @Autowired
    BookUuidRepository bookUuidRepository;

    @Autowired
    BookNaturalRepository bookNaturalRepository;

    @Autowired
    AuthorCompositeRepository authorCompositeRepository;

    BookUuid savedBookUuid;
    AuthorUuid savedAuthorUuid;

    @BeforeEach
    void setUp() {
        savedBookUuid = bookUuidRepository.save(new BookUuid("test", "test1", "test2"));
        savedAuthorUuid = authorUuidRepository.save(new AuthorUuid("test", "test1"));
    }

    @Test
    void authorCompositeTest() {
        NameId nameId = new NameId("Anna", "Malecka");
        AuthorComposite authorComposite = new AuthorComposite();
        authorComposite.setFirstName(nameId.getFirstName());
        authorComposite.setLastName(nameId.getLastName());
        authorComposite.setCountry("PL");

        AuthorComposite saved = authorCompositeRepository.save(authorComposite);
        assertThat(saved).isNotNull();

        AuthorComposite retrieved = authorCompositeRepository.getById(nameId);
        assertThat(retrieved).isNotNull();
    }

    @Test
    void bookNaturalTest() {
        BookNatural bookNatural = new BookNatural();
        bookNatural.setTitle("My Book");
        BookNatural saved = bookNaturalRepository.save(bookNatural);

        BookNatural retrieved = bookNaturalRepository.getById(saved.getTitle());
        assertThat(retrieved).isNotNull();
    }

    @Test
    void testMySQL() {
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(2);
    }

    @Test
    void shouldSaveNewAuthorUuid() {
        long countBefore = authorUuidRepository.count();

        authorUuidRepository.save(new AuthorUuid("test", "test"));

        long countAfter = authorUuidRepository.count();

        assertThat(countAfter).isEqualTo(countBefore + 1);
    }

    @Test
    void shouldSaveNewBookUuid() {
        long countBefore = bookUuidRepository.count();

        bookUuidRepository.save(new BookUuid("test", "test", "test"));

        long countAfter = bookUuidRepository.count();

        assertThat(countAfter).isEqualTo(countBefore + 1);
    }

    @Test
    void shouldGetBookUuidById() {
        BookUuid retrieved = bookUuidRepository.getById(savedBookUuid.getId());

        assertThat(retrieved).isEqualTo(savedBookUuid);
    }

    @Test
    void shouldGetAuthorUuidById() {
        AuthorUuid retrieved = authorUuidRepository.getById(savedAuthorUuid.getId());

        assertThat(retrieved).isEqualTo(savedAuthorUuid);
    }
}
