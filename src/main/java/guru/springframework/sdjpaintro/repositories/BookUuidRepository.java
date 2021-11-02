package guru.springframework.sdjpaintro.repositories;

import guru.springframework.sdjpaintro.domain.BookUuid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookUuidRepository extends JpaRepository<BookUuid, Long> {
}
