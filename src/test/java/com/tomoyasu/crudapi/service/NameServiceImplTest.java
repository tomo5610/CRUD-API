package com.tomoyasu.crudapi.service;

import com.tomoyasu.crudapi.entity.Name;
import com.tomoyasu.crudapi.exception.ResourceNotFoundException;
import com.tomoyasu.crudapi.mapper.NameMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NameServiceImplTest {

    @InjectMocks
    NameServiceImpl nameServiceImpl;

    @Mock
    NameMapper nameMapper;

    @Test
    public void IDに紐づくユーザーが1件取得できること() throws Exception {
        doReturn(Optional.of(new Name(1, "tomoyasu", "202301"))).when(nameMapper).findById(1);

        Name actual = nameServiceImpl.findById(1);
        assertThat(actual).isEqualTo(new Name(1, "tomoyasu", "202301"));
        verify(nameMapper, times(1)).findById(1);
    }

    @Test
    public void 存在するユーザー情報を全て返すこと() {
        List<Name> names = List.of(
                new Name(1, "tomoyasu", "202301"),
                new Name(2, "tanaka", "202302"),
                new Name(3, "yamada", "202303"));

        doReturn(names).when(nameMapper).findAll();

        List<Name> actual = nameServiceImpl.findAll();
        assertThat(actual).isEqualTo(names);
        verify(nameMapper, times(1)).findAll();
    }

    @Test
    public void 存在しないユーザーのIDを指定したときに例外が返されること() throws Exception {
        doReturn(Optional.empty()).when(nameMapper).findById(2);

        assertThatThrownBy(
                () -> nameServiceImpl.findById(2)
        ).isInstanceOfSatisfying(
                ResourceNotFoundException.class, e -> assertThat(e.getMessage()).isEqualTo("resource not found")
        );
    }
}
