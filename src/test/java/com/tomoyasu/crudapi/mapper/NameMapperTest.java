package com.tomoyasu.crudapi.mapper;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.tomoyasu.crudapi.entity.Name;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DBRider
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NameMapperTest {
    @Autowired
    NameMapper nameMapper;

    @Test
    @DataSet(value = "datasets/names.yml")
    @Transactional
    void 全てのIDを取得できること() {
        List<Name> names = nameMapper.findAll();
        assertThat(names)
                .hasSize(3)
                .contains(
                        new Name(1, "tomoyasu", YearMonth.of(2023, 1)),
                        new Name(2, "tanaka", YearMonth.of(2023, 2)),
                        new Name(3, "yamada", YearMonth.of(2023, 3))
                );
    }

    @Test
    @DataSet(value = "datasets/names.yml")
    @Transactional
    void 指定したIDが取得できること() {
        assertThat(nameMapper.findById(1))
                .contains(new Name(1, "tomoyasu", YearMonth.of(2023, 1)));
    }

    @Test
    @DataSet(value = "datasets/empty.yml")
    @Transactional
    void DBが空の時に空のリストが返されること() {
        assertThat(nameMapper.findAll().isEmpty());
    }

    @Test
    @DataSet(value = "datasets/names.yml")
    @ExpectedDataSet(value = "datasets/insert_names.yml", ignoreCols = "id")
    @Transactional
    void データ登録ができ既存のIDより大きい数字のIDが採番されること() {
        Name name = new Name("higashi", YearMonth.of(2023, 1));
        nameMapper.createName(name);
        assertThat(name.getId()).isGreaterThan(3);
    }

    @Test
    @DataSet(value = "datasets/names.yml")
    @ExpectedDataSet(value = "datasets/update_names.yml")
    @Transactional
    void 指定したIDのデータを入力データで更新すること() {
        Name updatedName = new Name(3, "suzuki", YearMonth.of(2023, 10));
        nameMapper.updateName(updatedName);
    }

    @Test
    @DataSet(value = "datasets/names.yml")
    @ExpectedDataSet(value = "datasets/delete_names.yml")
    @Transactional
    void 指定したIDのメンバーが削除できること() {
        nameMapper.deleteById(3);
    }
}
