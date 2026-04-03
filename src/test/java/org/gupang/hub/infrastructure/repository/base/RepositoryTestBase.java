package org.gupang.hub.infrastructure.repository.base;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class RepositoryTestBase {

    @Autowired
    protected EntityManager em;

    protected void flushAndClear() {
        em.flush();
        em.clear();
    }

}
