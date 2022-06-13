package io.lonmstalker.jpa.mock;

import io.lonmstalker.jpa.JpaTest;
import io.lonmstalker.model.ExampleObject;
import io.lonmstalker.repo.ExampleRepository;
import io.lonmstalker.service.ExampleService;
import io.lonmstalker.service.impl.ExampleServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ExampleServiceImpl.class)
public class MockJpaTest implements JpaTest {

    @Autowired
    private ExampleService exampleService;

    @MockBean
    private ExampleRepository exampleRepository;

    @BeforeEach
    void reset(){
        Mockito.reset(exampleRepository);
    }

    @Test
    @Override
    public void disable_whenEnabled() {
        ExampleObject exampleObject = this.exampleObject(true);
        InOrder inOrder = inOrder(exampleObject, exampleRepository);
        when(exampleRepository.save(exampleObject))
                .thenReturn(exampleObject);

        Assertions.assertDoesNotThrow(
                () -> this.exampleService.disable(exampleObject)
        );

        verify(exampleRepository, times(1)).save(any());
        verify(exampleObject, times(1)).setEnabled(false);

        // verify order
        inOrder.verify(exampleObject).setEnabled(false);
        inOrder.verify(exampleRepository).save(any());
    }

    @Test
    @Override
    public void disable_whenDisabled() {
        ExampleObject exampleObject = this.exampleObject(false);
        when(exampleRepository.save(exampleObject))
                .thenReturn(exampleObject);

        Assertions.assertThrows(
                RuntimeException.class,
                () -> this.exampleService.disable(exampleObject),
                "must be enabled"
        );

        verify(exampleRepository, never()).save(any());
        verify(exampleObject, never()).setEnabled(false);
    }

    @Test
    @Override
    public void enable_whenDisabled() {
        ExampleObject exampleObject = this.exampleObject(false);
        InOrder inOrder = inOrder(exampleObject, exampleRepository);
        when(exampleRepository.save(exampleObject))
                .thenReturn(exampleObject);

        Assertions.assertDoesNotThrow(
                () -> this.exampleService.enable(exampleObject)
        );

        verify(exampleRepository, times(1)).save(any());
        verify(exampleObject, times(1)).setEnabled(true);

        // verify order
        inOrder.verify(exampleObject).setEnabled(true);
        inOrder.verify(exampleRepository).save(any());
    }

    @Test
    @Override
    public void enable_whenEnabled() {
        ExampleObject exampleObject = this.exampleObject(true);
        when(exampleRepository.save(exampleObject))
                .thenReturn(exampleObject);

        Assertions.assertThrows(
                RuntimeException.class,
                () -> this.exampleService.enable(exampleObject),
                "must be disabled"
        );

        verify(exampleRepository, never()).save(any());
        verify(exampleObject, never()).setEnabled(false);
    }

    private ExampleObject exampleObject(boolean enabled) {
        ExampleObject exampleObject = new ExampleObject();
        exampleObject.setName("any");
        exampleObject.setEnabled(enabled);
        return spy(exampleObject);
    }
}
