package com.tomoyasu.crudapi.service;

import com.tomoyasu.crudapi.entity.Name;
import com.tomoyasu.crudapi.exception.ResourceNotFoundException;
import com.tomoyasu.crudapi.mapper.NameMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
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
        doReturn(Optional.of(new Name(1, "tomoyasu", YearMonth.of(2023, 1)))).when(nameMapper).findById(1);

        Name actual = nameServiceImpl.findById(1);
        assertThat(actual).isEqualTo(new Name(1, "tomoyasu", YearMonth.of(2023, 1)));
        verify(nameMapper, times(1)).findById(1);
    }

    @Test
    public void 存在するユーザー情報を全て返すこと() {
        List<Name> names = List.of(
                new Name(1, "tomoyasu", YearMonth.of(2023, 1)),
                new Name(2, "tanaka", YearMonth.of(2023, 2)),
                new Name(3, "yamada", YearMonth.of(2023, 3)));

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

    @Test
    public void ユーザーが１件登録されること() throws Exception {
        Name name = new Name("tomoyasu", YearMonth.of(2023, 1));
        doNothing().when(nameMapper).createName(name);

        nameServiceImpl.createName("tomoyasu", YearMonth.of(2023, 1));
        verify(nameMapper, times(1)).createName(name);
    }

    @Test
    public void 存在するユーザーのIDを指定したときにユーザーを更新できること() throws Exception {
        Name updateName = new Name(1, "tomoyasu", YearMonth.of(2023, 1));
        doReturn(Optional.of(updateName)).when(nameMapper).findById(1);

        nameServiceImpl.updateName(1, "tomoyasu", YearMonth.of(2023, 2));
        verify(nameMapper, times(1)).updateName(updateName);
    }

    @Test
    public void 更新指定したIDが存在しないとき例外を返すこと() {
        doReturn(Optional.empty()).when(nameMapper).findById(99);
        assertThrows(ResourceNotFoundException.class, () -> nameServiceImpl.findById(99));
        verify(nameMapper, times(1)).findById(99);
    }

    @Test
    public void 存在するユーザーのIDを指定したときにユーザーを削除できること() throws Exception {
        doReturn(Optional.of(new Name(1, "tomoyasu", YearMonth.of(2023, 1)))).when(nameMapper).findById(1);

        doNothing().when(nameMapper).deleteById(1);

        assertDoesNotThrow(() -> nameServiceImpl.deleteById(1));
        
        verify(nameMapper, times(1)).findById(1);
        verify(nameMapper, times(1)).deleteById(1);
    }
}
